package com.app.seafoodapp.ui


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.app.seafoodapp.Const.ConstApi
import com.app.seafoodapp.CountryAdapter
import com.app.seafoodapp.DelegateAdapter
import com.app.seafoodapp.Interface.CountryInterface
import com.app.seafoodapp.Interface.StateInterface
import com.app.seafoodapp.Interface.delegateInterface
import com.app.seafoodapp.Network.HttpTask
import com.app.seafoodapp.Network.NetworkConnect
import com.app.seafoodapp.R
import com.app.seafoodapp.StateAdapter
import com.app.seafoodapp.lib.activity.WebViewActivity
import com.app.seafoodapp.lib.activity.usdPaymentGatewayActivity
import com.app.seafoodapp.lib.utility.AvenuesParams
import com.app.seafoodapp.lib.utility.Constants
import com.app.seafoodapp.lib.utility.ServiceUtility
import com.app.seafoodapp.model.Adddelegatemodel
import com.app.seafoodapp.model.AllDetails
import com.app.seafoodapp.model.Countrystdcode
import com.app.seafoodapp.model.StateModel
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity(), delegateInterface, CountryInterface,
    StateInterface {
    private var btnmakepayment: Button? = null
    private var tvvalregfee: TextView? = null
    private var tvvalgstfee: TextView? = null
    private var tvvaltotalfee: TextView? = null
    private var tvcurrencyregfee: TextView? = null
    private var tvcurrencyregfeeone: TextView? = null
    private var tvcurrencytotalfee: TextView? = null
    private var fontAwesomewebFont: Typeface? = null
    private var fontAwesomeFont: Typeface? = null
    private val radioIndian: RadioButton? = null
    private val radiousd: RadioButton? = null
    private var cddelegatereg: CardView? = null
    private var tvtotalpaymentamt: TextView? = null
    private var tvdelregdetail: TextView? = null
    private var rllineregdetail: View? = null
    private var jarrdelegate: JSONArray? = null
    private var jarrfinaldelegate: JSONArray? = null
    private var arrdelegatelist: ArrayList<Adddelegatemodel> = ArrayList<Adddelegatemodel>()
    private val jobjectdelegate: JSONObject? = null
    private var jarrdelegateregistration: JSONArray? = null
    private var jobjectdelegateregistration: JSONObject? = null
    private var jresultresponse: JSONObject? = null
    private var strresultrespnse: String? = null
    private val payment_button: Button? = null
    private var total_inr = 0.0
    private var edtdelgatename: EditText? = null
    private var edtdelgatedesignation: EditText? = null
    private var edtdelgatecompany: EditText? = null
    private var edtaddress: EditText? = null
    private var edtcity: EditText? = null
    private var edtzippincode: EditText? = null
    private var edtemailid: EditText? = null
    private var edtmobileno: EditText? = null
    private var edtgstno: EditText? = null
    private var edtothercategory: EditText? = null
    private var edtfax: EditText? = null
    private var edttelephone: EditText? = null
    private var mdeladatper: DelegateAdapter? = null

    var isAcceptedConditions = false


    //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    var emailPattern = "^(.+)@(.+)$"
    private var spnselectcategory: Spinner? = null
    var foreignmoney = 0.0
    var totalamtpasstoccavenue = 0.0
    var totalindianmoney = 12500.00
    var spncategoryitem: MutableList<String> = ArrayList()
    private var mPref: SharedPreferences? = null
    private var mEdit: SharedPreferences.Editor? = null

    // private Spinner mCountry, mState;
    // private Spinner mState;
    private var mCountry: EditText? = null
    private var mState: EditText? = null
    private val mModelCountry: MutableList<AllDetails> = ArrayList<AllDetails>()
    private val arrCountrystdcode: List<Countrystdcode> = ArrayList<Countrystdcode>()
    private val mCountryList: MutableList<String> = ArrayList()
    private var mStateList: MutableList<StateModel> = ArrayList<StateModel>()
    private var count_id: String? = null
    private var country_id: String? = null
    private var stateid: String? = null
    private var stdcountrycode: String? = null
    private var country_name = " "
    private var state_name = " "
    private var selectcat: String? = null
    private var mOutputText: TextView? = null
    private var closeicon: ImageView? = null
    private var backicon: ImageView? = null
//   var prgindian: KProgressHUD? = null
//  var prgoversea: KProgressHUD? = null

    // String[] mCountryList;
    lateinit var stringArray: Array<String>
    var countryList: List<String>? = null
    lateinit var statearr: Array<String>
    var statelist: List<String>? = null
    var i = 1
    var width = 0
    var height = 0

    //double gst_value = 29500.00;
    // double gst_value = 2250.00;
    var result = 0.0
    var us_gst = 180.00
    var us_result = 0.0
    private var mGstTxt: TextView? = null
    private var mRstTotal_txt: TextView? = null
    private var mCountry_txt = "null"
    private var currencytype = "inr"
    var list: ArrayList<Countrystdcode> = ArrayList<Countrystdcode>()
    var isdelgatedeleted = false
    var mProgressBar: ProgressDialog? = null
    var mdelegatemodel: Adddelegatemodel? = null

    // var mdeladatper: DelegateAdapter? = null
    //  var showprogress: KProgressHUD? = null
    private var country_recycler_delegate: RecyclerView? = null
    private var mStateRecycler_delegate: RecyclerView? = null
    private var mCountryadapter_delegate: CountryAdapter? = null
    private var mStateadatper_delegate: StateAdapter? = null
    private var mStallReg: TextView? = null
    private var mRegFee: TextView? = null
    private var mGstFee: TextView? = null
    private var mTotalFee: TextView? = null
    private var tvaddnewdelegate: TextView? = null

    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    private var edtdelegatename: EditText? = null
    private var eddesginationname: EditText? = null
    private var edtphonemobile: EditText? = null
    private var edtdelegateemailaddress: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (NetworkConnect.isNetworkConnected(this@MainActivity)) {
            countryDetails
        } else {
            Toast.makeText(
                this@MainActivity,
                "Check your Internet Connection & try again",
                Toast.LENGTH_SHORT
            ).show()
        }
        initview()
        initText()

        val ss = SpannableString("I accept the Terms and Conditions")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun onClick(textView: View) {

                startActivity(Intent(this@MainActivity, TermsConditionActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.setColor(Color.parseColor("#17a2b8"))
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 13, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val boldSpan = StyleSpan(Typeface.BOLD)
        ss.setSpan(boldSpan, 13, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        val textView = findViewById<TextView>(R.id.Terms_and_condition_text)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
        //
    }

    private fun initText() {
        mStallReg = findViewById(R.id.tvdelreg)
        mRegFee = findViewById(R.id.tvregfee)
        mGstFee = findViewById(R.id.tvgstfee)
        mTotalFee = findViewById(R.id.tvtotalfee)
        mRegular = Typeface.createFromAsset(assets, "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(assets, "montserrat/Montserrat-Medium.otf")
        edtdelgatename!!.typeface = mRegular
        edtdelgatedesignation!!.typeface = mRegular
        edtdelgatecompany!!.typeface = mRegular
        edtaddress!!.typeface = mRegular
        edtcity!!.typeface = mRegular
        edtzippincode!!.typeface = mRegular
        edttelephone!!.typeface = mRegular
        edtfax!!.typeface = mRegular
        edtmobileno!!.typeface = mRegular
        edtemailid!!.typeface = mRegular
        edtgstno!!.typeface = mRegular
        edtothercategory!!.typeface = mRegular
        tvdelregdetail!!.typeface = mMedium
        edtdelegatename = findViewById<View>(R.id.eddelegatename) as EditText
        edtdelegatename!!.typeface = mRegular
        eddesginationname = findViewById<View>(R.id.eddesginationname) as EditText
        eddesginationname!!.typeface = mRegular
        edtphonemobile = findViewById<View>(R.id.edtphonemobile) as EditText
        edtphonemobile!!.typeface = mRegular
        edtdelegateemailaddress = findViewById<View>(R.id.edtdelegateemailaddress) as EditText
        edtdelegateemailaddress!!.typeface = mRegular
        mGstTxt!!.typeface = mMedium
        tvtotalpaymentamt!!.typeface = mMedium
        mRstTotal_txt!!.typeface = mMedium
        mOutputText!!.typeface = mMedium
        tvaddnewdelegate!!.typeface = mMedium
        btnmakepayment!!.typeface = mMedium
        mStallReg!!.typeface = mMedium
        mRegFee!!.typeface = mMedium
        mGstFee!!.typeface = mMedium
    }

    private fun initview() {
        jarrdelegate = JSONArray()
        jarrfinaldelegate = JSONArray()
        mPref = getSharedPreferences("Data", MODE_PRIVATE)
        mEdit = mPref!!.edit()
        mCountry = findViewById(R.id.country_spinner)
        mState = findViewById(R.id.state_spinner)
        mOutputText = findViewById(R.id.output_gst_txt)
        mGstTxt = findViewById(R.id.gst_txt)
        mRstTotal_txt = findViewById(R.id.rs_total_txt)
        mainLayout = findViewById<View>(R.id.lldelegate) as LinearLayout
        rllineregdetail = findViewById(R.id.rllineregdetail) as View
        rllineregdetail!!.visibility = View.GONE

        // getLoginDetails();
        deletgate_recycler = findViewById(R.id.deletgate_recycler)
        fontAwesomewebFont = Typeface.createFromAsset(assets, "fonts/fontawesome-webfont.ttf")
        fontAwesomeFont = Typeface.createFromAsset(assets, "fonts/FontAwesome.ttf")
        val tvplusicon = findViewById<View>(R.id.tvplusicon) as TextView
        tvplusicon.setTypeface(fontAwesomewebFont)
        tvplusicon.text = "\uf055"
        tvvalregfee = findViewById<View>(R.id.tvvalregfee) as TextView
        tvvalgstfee = findViewById<View>(R.id.tvvalgstfee) as TextView
        tvvaltotalfee = findViewById<View>(R.id.tvvaltotalfee) as TextView
        tvcurrencyregfee = findViewById<View>(R.id.tvcurrencyregfee) as TextView
        tvcurrencyregfeeone = findViewById<View>(R.id.tvcurrencyregfeeone) as TextView
        tvcurrencytotalfee = findViewById<View>(R.id.tvcurrencytotalfee) as TextView
        val radioGroupdelegate = findViewById<RadioGroup>(R.id.radioGroupdelegate)
        val radioIndian = findViewById<RadioButton>(R.id.radioIndian)
        val radioOverSeas = findViewById<RadioButton>(R.id.radioOverSeas)
        cddelegatereg = findViewById(R.id.cddelegatereg)
        rllineregdetail = findViewById(R.id.rllineregdetail)
        tvdelregdetail = findViewById(R.id.tvdelregdetail)
        tvtotalpaymentamt = findViewById(R.id.tvtotalpaymentamt)
        if (width == 720) {
            mOutputText!!.setTextSize(14f)
        }
        tvaddnewdelegate = findViewById(R.id.tvaddnewdelegate)
        edtdelgatename = findViewById(R.id.edtdelgatename)
        edtdelgatedesignation = findViewById(R.id.edtdelgatedesignation)
        edtdelgatecompany = findViewById(R.id.edtdelgatecompany)
        edtaddress = findViewById(R.id.edtaddress)
        edtzippincode = findViewById(R.id.edtzippincode)
        edtmobileno = findViewById(R.id.edtmobileno)
        edtemailid = findViewById(R.id.edtemailid)
        edtgstno = findViewById(R.id.edtgstno)
        edtothercategory = findViewById(R.id.edtothercategory)
        edtfax = findViewById(R.id.edtfax)
        edtcity = findViewById(R.id.edtcity)
        edttelephone = findViewById(R.id.edttelephone)
        spnselectcategory = findViewById<View>(R.id.spnselectcategory) as Spinner
        backicon = findViewById(R.id.backicon)
        closeicon = findViewById(R.id.closeicon)
        val mCheckBox = findViewById<CheckBox>(R.id.check_box)
        backicon!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })
        closeicon!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })
        spncategoryitem.add("Select Cateogory *")
        spncategoryitem.add("Service Provider")
        spncategoryitem.add("Machinery Manufacture")
        spncategoryitem.add("Buyer")
        spncategoryitem.add("Buyer Agent")
        spncategoryitem.add("Exporter")
        spncategoryitem.add("Exporter Staff")
        spncategoryitem.add("Other")
        spnselectcategory!!.adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_dropdown_item,
            spncategoryitem
        )
        spnselectcategory!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    selectcat = spncategoryitem[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
        tvaddnewdelegate!!.setOnClickListener(View.OnClickListener {
            if (tvdelregdetail!!.visibility == View.GONE) {
                tvdelregdetail!!.visibility = View.VISIBLE
                cddelegatereg!!.visibility = View.VISIBLE
            } else {
                adddelegatetwo()
            }
        })
        tvplusicon.setOnClickListener {
            if (tvdelregdetail!!.visibility == View.GONE) {
                tvdelregdetail!!.visibility = View.VISIBLE
                cddelegatereg!!.visibility = View.VISIBLE
            } else {
                adddelegatetwo()
            }
        }
        tvcurrencyregfee!!.typeface = fontAwesomeFont
        tvcurrencyregfeeone!!.typeface = fontAwesomeFont
        tvcurrencytotalfee!!.typeface = fontAwesomeFont
        tvcurrencyregfee!!.text = "\uf156"
        tvcurrencyregfeeone!!.text = "\uf156"
        tvcurrencytotalfee!!.text = "\uf156"
        val radio = findViewById<View>(R.id.radioGroupdelegate) as RadioGroup
        radio.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = radio.findViewById<View>(checkedId)
            val index = radio.indexOfChild(radioButton)
            if (checkedId == R.id.radioIndian) {
//                prgindian = KProgressHUD.create(this@MainActivity)
//                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                    .setLabel("Loading..") //  .setAnimationSpeed(2)
//                    .setDimAmount(0.5f)
//                    .setMaxProgress(100)
//                    .show()
//                prgindian.setProgress(40)
                mCountry_txt = "India"
                // Toast.makeText(getApplicationContext(), "Indian money" + index, Toast.LENGTH_LONG).show();
                tvcurrencyregfee!!.typeface = fontAwesomeFont
                tvcurrencyregfeeone!!.typeface = fontAwesomeFont
                tvcurrencytotalfee!!.typeface = fontAwesomeFont
                tvcurrencyregfee!!.text = "\uf156"
                tvcurrencyregfeeone!!.text = "\uf156"
                tvcurrencytotalfee!!.text = "\uf156"
                val rupee = resources.getString(R.string.Rs)
                //  int indianmoney = Integer.parseInt(mPref.getString("indian", null));
                val strindianmy: String =
                    NumberFormat.getIntegerInstance().format(ConstApi.totalindianmoney)
                // mGstTxt.setText(R.string.strfeegst);
                //mGstTxt.setText(R.string.strfeegst);
                // mGstTxt.setText("Fee and GST " + rupee + " 14750.0");
                tvvalregfee!!.text = strindianmy
                val strgstfee: String =
                    NumberFormat.getIntegerInstance().format(ConstApi.gst_value)
                tvvalgstfee!!.text = strgstfee
                // tvvaltotalfee.setText(strforegin);
                // to uncomment  totalindianmoney = ConstApi.totalindianmoney + (int) ConstApi.gst_value;
                val strtotalindianmoney =
                    NumberFormat.getIntegerInstance().format(totalindianmoney)
                tvvaltotalfee!!.text = strtotalindianmoney.toString()
                result = 0.0
                us_result = 0.0
                // totalindianmoney=0;
                totalamtpasstoccavenue = 0.0
                if (result == 0.0) {
                    //  mOutputText.setText("29,750.00");
                    //  mOutputText.setText("14,750.00");
                    mOutputText!!.setText(strtotalindianmoney.toString())
                    mRstTotal_txt!!.setText(rupee)
                } else {
                    mRstTotal_txt!!.setText(rupee)
                    mOutputText!!.setText(result.toString() + "")
                }
                currencytype = "inr"
                totalamtpasstoccavenue = 0.0
                if (mainLayout != null) {
                    mainLayout!!.removeAllViews()
                }
                arrdelegatelist = ArrayList<Adddelegatemodel>()
                deletgate_recycler!!.layoutManager = LinearLayoutManager(this@MainActivity)
                mdeladatper = DelegateAdapter(this@MainActivity, arrdelegatelist, this@MainActivity)
                deletgate_recycler!!.adapter = mdeladatper
                resetallvalue()
                setdefaultdelgatevalue()
                //
                Handler().postDelayed({ //removeWorkingDialog();
                    // prgindian.dismiss()
                }, 1000)
            } else if (checkedId == R.id.radioOverSeas) {
//                prgoversea = KProgressHUD.create(this@MainActivity)
//                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                    .setLabel("Loading..")
//                    .setAnimationSpeed(2)
//                    .setDimAmount(0.5f)
//                    .setMaxProgress(100)
//                    .show()
//                prgoversea.setProgress(40)
                mCountry_txt = "Us"
                ConstApi.foreign_gst_value =
                    util.calculategstprice(ConstApi.foreign_reg_value, ConstApi.gstpercentage)
                // Toast.makeText(getApplicationContext(), "USD Dollar" + index, Toast.LENGTH_LONG).show();
                tvcurrencyregfee!!.typeface = fontAwesomeFont
                tvcurrencyregfeeone!!.typeface = fontAwesomeFont
                tvcurrencytotalfee!!.typeface = fontAwesomeFont
                tvcurrencyregfee!!.text = "\uf155"
                tvcurrencyregfeeone!!.text = "\uf155"
                tvcurrencytotalfee!!.text = "\uf155"
                result = 0.0
                us_result = 0.0


                // foreignmoney = Integer.parseInt(mPref.getString("overseas", null));
                // foreignmoney = foreignmoney + Constants.forgstfee;
                foreignmoney = ConstApi.foreign_reg_value + ConstApi.foreign_gst_value
                // String strforegin = NumberFormat.getIntegerInstance().format(Constants.foreginregfee);
                //  String strforegin = NumberFormat.getIntegerInstance().format(foreignmoney);
                //   mGstTxt.setText("Fee and GST"+""+""+""+" $ 1.18");
                // mGstTxt.setText("Fee and GST" + "" + "" + "" + " $ 212.4");
                mGstTxt!!.setText("Fee and GST :  $ $foreignmoney")
                if (us_result == 0.0) {
                    mRstTotal_txt!!.setText("$")
                    //     mOutputText.setText("2.36");
                    // mOutputText.setText("212.4");
                    mOutputText!!.setText(foreignmoney.toString())
                } else {
                    mRstTotal_txt!!.setText("$")
                    mOutputText!!.setText(us_result.toString() + "")
                }


                // tvvalregfee.setText(ConstApi.foreign_reg_value + "." + "00");
                tvvalregfee!!.setText(java.lang.String.valueOf(ConstApi.foreign_reg_value))
                // tvvalgstfee.setText("32.40");
                tvvalgstfee!!.setText(java.lang.String.valueOf(ConstApi.foreign_gst_value))
                val totalforeignfee =
                    tvvalregfee!!.text.toString().toDouble() + tvvalgstfee!!.text.toString()
                        .toDouble()
                //  int totalforeignfee = tvvalregfee.getText() + tvvalgstfee.getText()
                tvvaltotalfee!!.text = totalforeignfee.toString()
                currencytype = "USD"
                if (mainLayout != null) {
                    mainLayout!!.removeAllViews()
                }
                arrdelegatelist = ArrayList<Adddelegatemodel>()
                deletgate_recycler!!.layoutManager = LinearLayoutManager(this@MainActivity)
                mdeladatper = DelegateAdapter(this@MainActivity, arrdelegatelist, this@MainActivity)
                deletgate_recycler!!.adapter = mdeladatper
                resetallvalue()
                Handler().postDelayed({ //removeWorkingDialog();
                    //   prgindian.dismiss();
                    // prgoversea.dismiss()
                }, 1000)

                val intent = Intent(this, usdPaymentGatewayActivity::class.java)
                startActivity(intent)
            }
        }
        mCountry!!.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onEditClick: ")
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.custom_dialog)
            country_recycler_delegate = dialog.findViewById(R.id.country_recycler)
            val edit_search = dialog.findViewById<EditText>(R.id.edit_search)
            val ok_txt = dialog.findViewById<TextView>(R.id.ok_text)
            val cancel_txt = dialog.findViewById<TextView>(R.id.cancel_text)
            ok_txt.setOnClickListener {
                Log.d(
                    TAG,
                    "onDialogClick: $country_name$country_id"
                )
                getStateDetails(country_id)
                mCountry!!.setText(country_name)
                dialog.dismiss()
            }
            cancel_txt.setOnClickListener { dialog.dismiss() }
            edit_search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filter(s.toString())
                }
            })
            if (mModelCountry.size > 0) {
                //   value = "Country";
                country_recycler_delegate!!.setLayoutManager(LinearLayoutManager(this@MainActivity))
                mCountryadapter_delegate =
                    CountryAdapter(this@MainActivity, mModelCountry, " ", this@MainActivity)
                country_recycler_delegate!!.setAdapter(mCountryadapter_delegate)
            }
            dialog.show()
            val window = dialog.window
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })
        mState!!.setOnClickListener(View.OnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.custom_state_dialog)
            mStateRecycler_delegate = dialog.findViewById(R.id.state_recycler)
            if (mStateList.size > 0) {
                //value = "State";
                mStateRecycler_delegate!!.setLayoutManager(LinearLayoutManager(this@MainActivity))
                mStateadatper_delegate =
                    StateAdapter(this@MainActivity, mStateList, this@MainActivity)
                mStateRecycler_delegate!!.setAdapter(mStateadatper_delegate)
                dialog.show()
                val window = dialog.window
                window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            } else {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please Select State")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            }
            val edit_search = dialog.findViewById<EditText>(R.id.edit_search_state)
            edit_search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filterState(s.toString())
                }
            })
            val ok_txt = dialog.findViewById<TextView>(R.id.ok_state_txt)
            ok_txt.setOnClickListener {
                mState!!.setText(state_name)
                dialog.dismiss()
            }
            val cancel_txt = dialog.findViewById<TextView>(R.id.cancel_state_txt)
            cancel_txt.setOnClickListener { dialog.dismiss() }
        })


        mCheckBox.setOnClickListener { v ->
            val checked = (v as CheckBox).isChecked
            if (!checked) {

                isAcceptedConditions = false
                mCheckBox.setTextColor(Color.parseColor("#FF0000"))
            } else {

                isAcceptedConditions = true
                Toast.makeText(applicationContext, "Accepted", Toast.LENGTH_SHORT).show()
            }
        }
        btnmakepayment = findViewById(R.id.payment_button)
        btnmakepayment!!.setOnClickListener(View.OnClickListener {


            if (edtdelgatename!!.text.isEmpty()) {
                //edtdelgatename.setError("Please enter delegate name");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please enter delegate name")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtdelgatedesignation!!.text.isEmpty()) {
                // edtdelgatedesignation.setError("Please enter delegate designation");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please enter delegate designation")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtdelgatecompany!!.text.isEmpty()) {
                //  edtdelgatecompany.setError("Please enter delegate company name");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please enter delegate company name")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtaddress!!.text.isEmpty()) {
                // edtaddress.setError("Please enter address");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please enter address")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (!edtemailid!!.text.toString().trim { it <= ' ' }
                    .matches(emailPattern.toRegex())) {
                // edtemailid.setError("Invalid Email Id");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Invalid Email Id")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (TextUtils.isEmpty(country_name)) {
                // else if (country_name.equals("") || country_name.length() == 0 || country_name.isEmpty() || country_name == null){

                // else if (ServiceUtility.chkNull(country_name).toString().trim())
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please Enter Country")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (TextUtils.isEmpty(state_name)) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please Enter State")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtzippincode!!.text.isEmpty()) {
                // edtzippincode.setError("Please enter zip/pin code");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please enter zip/pin code")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtmobileno!!.text.isEmpty()) {
                //  edtmobileno.setError("Please enter mobile number");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please enter mobile number")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtgstno!!.text.isEmpty()) {
                //  edtgstno.setError("Please Enter GST No");
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Message")
                    .setMessage("Please Enter GST No")
                    .setCancelable(false)
                    .setPositiveButton(
                        "ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (!isAcceptedConditions) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("Please Accept the terms and conditions")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Ok"
                    ) { dialog, which ->
                        // Whatever...
                   }.show()
                    }

                else {
                    if (NetworkConnect.isNetworkConnected(this@MainActivity)) {
                        if (TextUtils.isEmpty(selectcat)) {
                            selectcat = " "
                        }
                        PostdataAsyncTask().execute()
                    } else {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Message")
                            .setMessage("Check your Internet Connection & try again")
                            .setCancelable(false)
                            .setPositiveButton(
                                "ok"
                            ) { dialog, which ->
                                // Whatever...
                            }.show()
                    }
                }
            })
            setdefaultdelgatevalue()
        }

            var noofdelgate = 0
        var mainLayout: LinearLayout? = null
        private var deletgate_recycler: RecyclerView? = null
        private fun adddelegatetwo() {
            if (edtdelegatename!!.text.toString().isEmpty()) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Alert")
                    .setMessage("Please Enter Delegate Name")
                    .setCancelable(false)
                    .setPositiveButton("ok") { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (eddesginationname!!.text.toString().isEmpty()) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Alert")
                    .setMessage("Please Enter Designation Name")
                    .setCancelable(false)
                    .setPositiveButton("ok") { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtphonemobile!!.text.toString().isEmpty()) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Alert")
                    .setMessage("Please Enter Mobile Number")
                    .setCancelable(false)
                    .setPositiveButton("ok") { dialog, which ->
                        // Whatever...
                    }.show()
            } else {
                updateamountfordelegate()
                noofdelgate += 1
                arrdelegatelist.add(
                    Adddelegatemodel(
                        edtdelegatename!!.text.toString(),
                        eddesginationname!!.text.toString(),
                        edtphonemobile!!.text.toString()
                    )
                )
                deletgate_recycler!!.layoutManager = LinearLayoutManager(this@MainActivity)
                mdeladatper = DelegateAdapter(this@MainActivity, arrdelegatelist, this@MainActivity)
                deletgate_recycler!!.adapter = mdeladatper
                Log.d(TAG, "getAllData: " + arrdelegatelist.size)
                edtdelegatename!!.text.clear()
                eddesginationname!!.text.clear()
                edtphonemobile!!.text.clear()
                edtdelegateemailaddress!!.text.clear()
                edtdelegatename!!.requestFocus()
            }
        }

        private fun adddelegate() {
            val edtdelegatename = findViewById<View>(R.id.eddelegatename) as EditText
            val eddesginationname = findViewById<View>(R.id.eddesginationname) as EditText
            val edtphonemobile = findViewById<View>(R.id.edtphonemobile) as EditText
            val edtdelegateemailaddress =
                findViewById<View>(R.id.edtdelegateemailaddress) as EditText
            if (edtdelegatename.text.toString().isEmpty()) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Alert")
                    .setMessage("Please Enter Delegate Name")
                    .setCancelable(false)
                    .setPositiveButton("ok") { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (eddesginationname.text.toString().isEmpty()) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Alert")
                    .setMessage("Please Enter Designation Name")
                    .setCancelable(false)
                    .setPositiveButton("ok") { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (edtphonemobile.text.toString().isEmpty()) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Alert")
                    .setMessage("Please Enter Mobile Number")
                    .setCancelable(false)
                    .setPositiveButton("ok") { dialog, which ->
                        // Whatever...
                    }.show()
            } else {

                //  if (mCountry_txt.contains("Us"))

                // if (noofdelgate>1) {
                updateamountfordelegate()
                //  }
                noofdelgate = noofdelgate + 1

                //  tvtotalpaymentamt.setText()

                //create a view to inflate the layout_item (the xml with the textView created before)
                val view = layoutInflater.inflate(R.layout.row_add_delegate, mainLayout, false)
                view.id = noofdelgate
                val tvvwdelegatename = view.findViewById<TextView>(R.id.tvrowdelegatename)
                tvvwdelegatename.text = edtdelegatename.text.toString()
                val tvdesignation = view.findViewById<TextView>(R.id.tvdesignation)
                tvdesignation.text = eddesginationname.text.toString()
                val tvPhone = view.findViewById<TextView>(R.id.tvPhone)
                tvPhone.text = edtphonemobile.text.toString()

                /*try {

                    jobjectdelegate = new JSONObject();
                    jobjectdelegate.put("DelegateName", edtdelegatename.getText().toString());
                    jobjectdelegate.put("DelegateDesignation", eddesginationname.getText().toString());
                    jobjectdelegate.put("DelegateMobile", edtphonemobile.getText().toString());
                    jarrdelegate.put(jobjectdelegate);
                } catch (JSONException je) {
                    Log.d("jsonexception", je.toString());
                }*/arrdelegatelist.add(
                    Adddelegatemodel(
                        edtdelegatename.text.toString(),
                        eddesginationname.text.toString(),
                        edtphonemobile.text.toString()
                    )
                )
                val action = view.findViewById<TextView>(R.id.action)
                action.setTypeface(fontAwesomeFont)
                action.text = "\uf1f8"
                action.id = noofdelgate
                action.setOnClickListener { vv ->
                    /*  int childcount = mainLayout.getChildCount();
                                for(int cc=0;cc<childcount;cc++) {

                                    View childView =  mainLayout.getChildAt(cc);

                                    TextView childTextView = (TextView)(childView.findViewById(R.id.tvrowdelegatename));
                                    String childTextViewText = (String)(childTextView.getText());
                                    Log.d("data",childTextViewText);
                                }*/
                    val id = vv.id
                    if (noofdelgate > 0) {
                        try {
                            if (noofdelgate == 1) {
                                arrdelegatelist.removeAt(0)
                            } else {
                                arrdelegatelist.removeAt(id)
                            }
                        } catch (e: Exception) {
                            Log.d("Exception", e.toString())
                        }
                        mainLayout!!.removeView(vv.parent as View)
                        if (currencytype.equals("inr", ignoreCase = true)) {

                            //  totalindianmoney = totalindianmoney - totalindianmoney;

                            // totalindianmoney = totalindianmoney - 14750;

                            // result = totalindianmoney;
                            // totalindianmoney = (int)result - 14750;
                            // to uncomment  totalindianmoney = (int)result - ConstApi.totalindianmoney - (int) ConstApi.gst_value;
                            result = totalindianmoney
                            totalamtpasstoccavenue = totalindianmoney
                            noofdelgate = noofdelgate - 1
                            val finn_rslt =
                                NumberFormat.getNumberInstance(Locale.US)
                                    .format(totalindianmoney)
                            Log.d(
                                TAG,
                                "adddelegate: $totalindianmoney"
                            )
                            mOutputText!!.text = "$finn_rslt.00"
                        } else {

                            //  foreignmoney = foreignmoney - foreignmoney;
                            foreignmoney = foreignmoney - 212.4
                            us_result = foreignmoney
                            val finn_rslt =
                                NumberFormat.getNumberInstance(Locale.US)
                                    .format(foreignmoney)
                            Log.d(
                                TAG,
                                "adddelegate: $foreignmoney"
                            )
                            mOutputText!!.text = "$finn_rslt.00"
                        }

                        //   noofdelgate = noofdelgate - 1;
                    } else if (noofdelgate == 0) {
                        if (currencytype.equals("inr", ignoreCase = true)) {
                            //  totalindianmoney = 14750;
                            totalindianmoney = totalindianmoney + ConstApi.gst_value as Int
                            totalamtpasstoccavenue = totalindianmoney
                            noofdelgate = noofdelgate - 1
                            result = totalindianmoney
                            val finn_rslt =
                                NumberFormat.getNumberInstance(Locale.US)
                                    .format(totalindianmoney)
                            Log.d(
                                TAG,
                                "adddelegate: $totalindianmoney"
                            )
                            mOutputText!!.text = "$finn_rslt.00"
                        } else {
                            foreignmoney = 212.4
                            us_result = foreignmoney
                            val finn_rslt =
                                NumberFormat.getNumberInstance(Locale.US)
                                    .format(foreignmoney)
                            Log.d(
                                TAG,
                                "adddelegate: $foreignmoney"
                            )
                            mOutputText!!.text = "$finn_rslt.00"
                        }
                    }
                }

                //add the view to the main layout
                // mainLayout.addView(view);
                edtdelegatename.text.clear()
                eddesginationname.text.clear()
                edtphonemobile.text.clear()
                edtdelegateemailaddress.text.clear()
                edtdelegatename.requestFocus()
                mainLayout!!.addView(view, mainLayout!!.childCount - 1)
            }
        }

        private fun resetdelegatedetail() {
            val edtdelegatename = findViewById<View>(R.id.eddelegatename) as EditText
            val eddesginationname = findViewById<View>(R.id.eddesginationname) as EditText
            val edtphonemobile = findViewById<View>(R.id.edtphonemobile) as EditText
            val edtdelegateemailaddress =
                findViewById<View>(R.id.edtdelegateemailaddress) as EditText
            edtdelegatename.text.clear()
            eddesginationname.text.clear()
            edtphonemobile.text.clear()
            edtdelegateemailaddress.text.clear()
            edtdelegatename.requestFocus()
        }

        // var hud: KProgressHUD? = null// mCountryList.add("Country");

        //showprogress.dismiss();
//for (int i = 0; i < jsonArray.length(); i++) {
        //  JSONObject jsonObject = jresultobj.getJSONObject(i);


        // String overseas = jresultobj.getString("REG_FEE_OVERSEAS_DEL");
/* String status = response.getString("status");
                        String msg = response.getString("msg");
                        if (status.equalsIgnoreCase("200")) {

                            String result = response.getString("result");
                            JSONArray jsonArray = new JSONArray(result);
                            Log.d(TAG, "onArrayResponse: " + result);
                            // JSONArray jsonArray = new JSONArray();
                           // for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String indian = jsonObject.getString("REG_FEE_NON_MEMBER");
                                ConstApi.totalindianmoney = Integer.parseInt(indian);
                               // ConstApi.totalindianmoney = 10000;
                                ConstApi.gstpercentage = Double.valueOf(jsonObject.getString("GST"));
                               // ConstApi.gstpercentage = 10;

                                ConstApi.gst_value =  util.calculategstprice(ConstApi.totalindianmoney,ConstApi.gstpercentage);
                                   Log.d("gstvalue",String.valueOf(ConstApi.gst_value));


                                   String overseas = jsonObject.getString("REG_FEE_OVERSEAS_DEL");
                                   ConstApi.foreign_reg_value = Double.valueOf(jsonObject.getString("REG_FEE_OVERSEAS_DEL"));
                                mEdit.putString("indian", indian);
                                mEdit.putString("overseas", overseas).commit();


                                Log.d(TAG, "onGetResponse: " + "indian" + indian + "overseas" + overseas);
                                JSONArray country_array = jsonObject.getJSONArray("countryArray");
                              / *  for (int j = 0; j < country_array.length(); j++) {
                                    JSONObject country_obj = country_array.getJSONObject(j);
                                    count_id = country_obj.getString("id");
                                    final String count_name = country_obj.getString("name");
                                    AllDetails allDetails = new AllDetails();
                                    allDetails.setCountry_id(count_id);
                                    allDetails.setCountry_name(count_name);
                                    // mCountryList.add("Country");
                                    mModelCountry.add(allDetails);
                                    mCountryList.add(allDetails.getCountry_name());
                                   // arrCountrystdcode.add(new Countrystdcode(count_name,count_id,))

                                    //  mCountry.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,mCountryList));
                                    //  mCountry.setSelection(0);
                                  //  Log.d(TAG, "onCountResponse: " + count_name);
                                }*/
        /* AllDetails allDetails = new AllDetails();
                allDetails.setCountry_id("0");
                allDetails.setCountry_name("Country *");

                mModelCountry.add(allDetails);*/
        //

        // mCountryList = new String[1];
        //mCountryList.("Country*");
        //mCountryList.add("Country*");
        // mStateList.add("State *");
        //

        // for live
        // RequestQueue requestQueue = Volley.newRequestQueue(this);
        // for simulator 4.0
        private val countryDetails: Unit
        private get () {
            try {
//                hud = KProgressHUD.create(this@MainActivity)
//                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                    .setLabel("Loading..")
//                    .setAnimationSpeed(2)
//                    .setDimAmount(0.5f)
//                    .setMaxProgress(100)
//                    .show()
//                hud.setProgress(50)


                /* AllDetails allDetails = new AllDetails();
                 allDetails.setCountry_id("0");
                 allDetails.setCountry_name("Country *");

                 mModelCountry.add(allDetails);*/
                //

                // mCountryList = new String[1];
                //mCountryList.("Country*");
                //mCountryList.add("Country*");
                // mStateList.add("State *");
                //
                list.add(Countrystdcode("Afghanistan", "93", "AF"))
                list.add(Countrystdcode("Albania", "355", "AL"))
                list.add(Countrystdcode("Algeria", "213", "DZ"))
                list.add(Countrystdcode("American Samoa", "1684", "AS"))
                list.add(Countrystdcode("Andorra ", "376", "AD"))
                list.add(Countrystdcode("Angola ", "244", "AO"))
                list.add(Countrystdcode("Anguilla ", "1264", "AI"))
                list.add(Countrystdcode("Antarctica", "672", "AQ"))
                list.add(Countrystdcode("Antigua", "1268", "AG"))
                list.add(Countrystdcode("Argentina", "54", "AR"))
                list.add(Countrystdcode("Armenia ", "374", "AM"))
                list.add(Countrystdcode("Aruba", "297", "AW"))
                list.add(Countrystdcode("Ascension", "247", ""))
                list.add(Countrystdcode("Australia ", "61", "AU"))
                list.add(Countrystdcode("Australian External Territories", "672", ""))
                list.add(Countrystdcode("Austria", "43", "AT"))
                list.add(Countrystdcode("Azerbaijan", "994", "AZ"))
                list.add(Countrystdcode("Bahamas", "1242", "BS"))
                list.add(Countrystdcode("Bahrain", "973", "BH"))
                list.add(Countrystdcode("Bangladesh", "880", "BD"))
                list.add(Countrystdcode("Barbados", "1246", "BB"))
                list.add(Countrystdcode("Barbuda", "1268", ""))
                list.add(Countrystdcode("Belarus", "375", "BY"))
                list.add(Countrystdcode("Belgium", "32", "BE"))
                list.add(Countrystdcode("Belize", "501", "BZ"))
                list.add(Countrystdcode("Benin", "229", "BJ"))
                list.add(Countrystdcode("Bermuda", "1441", "BM"))
                list.add(Countrystdcode("Bhutan", "975", "BT"))
                list.add(Countrystdcode("Bolivia ", "591", "BO"))
                list.add(Countrystdcode("Bosnia & Herzegovina ", "387", "BA"))
                list.add(Countrystdcode("Botswana ", "267", "BW"))
                list.add(Countrystdcode("Brazil ", "55", "BR"))
                list.add(Countrystdcode("British Virgin Islands", "1284", "VG"))
                list.add(Countrystdcode("Brunei Darussalam", "673", "brn"))
                list.add(Countrystdcode("Bulgaria", "359", "BG"))
                list.add(Countrystdcode("Burkina Faso ", "226", "BF"))
                list.add(Countrystdcode("Burundi", "257", "BN"))
                list.add(Countrystdcode("Cambodia", "855", "KH"))
                list.add(Countrystdcode("Cameroon", "237", "CM"))
                list.add(Countrystdcode("Canada", "1", "CA"))
                list.add(Countrystdcode("Cape Verde Islands", "238", "CV"))
                list.add(Countrystdcode("Cayman Islands", "1345", "KY"))
                list.add(Countrystdcode("Central African Republic", "236", "CF"))
                list.add(Countrystdcode("Chad ", "235", "TD"))
                list.add(Countrystdcode("Chatham Island (New Zealand)", "64", "CL"))
                list.add(Countrystdcode("Chile ", "56", "CL"))
                list.add(Countrystdcode("China", "86", "CN"))
                list.add(Countrystdcode("Christmas Island", "61", "CX"))
                list.add(Countrystdcode("Cocos-Keeling Islands", "61", "CK"))
                list.add(Countrystdcode("Colombia ", "57", "CO"))
                list.add(Countrystdcode("Comoros", "269", "KM"))
                list.add(Countrystdcode("Congo", "242", ""))
                list.add(Countrystdcode("Congo, Dem. Rep. of  (former Zaire) ", "243", ""))
                list.add(Countrystdcode("Cook Islands", "682", ""))
                list.add(Countrystdcode("Costa Rica", "506", "CR"))
                list.add(Countrystdcode("Côte d'Ivoire (Ivory Coast)", "225", ""))
                list.add(Countrystdcode("Croatia", "385", "HR"))
                list.add(Countrystdcode("Cuba", "53", "CU"))
                list.add(Countrystdcode("Cuba (Guantanamo Bay)", "5399", "CU"))
                list.add(Countrystdcode("Curaçao", "5999", "CW"))
                list.add(Countrystdcode("Cyprus", "357", "CY"))
                list.add(Countrystdcode("Czech Republic", "420", "CZ"))
                list.add(Countrystdcode("Denmark", "45", "DK"))
                list.add(Countrystdcode("Diego Garcia", "246", ""))
                list.add(Countrystdcode("Djibouti", "253", "DJ"))
                list.add(Countrystdcode("Dominica", "1767", "DM"))
                list.add(Countrystdcode("Dominican Republic ", "1809", "DO"))
                list.add(Countrystdcode("East Timor", "670", "TL"))
                list.add(Countrystdcode("Easter Island", "56", ""))
                list.add(Countrystdcode("Ecuador ", "593", "EC"))
                list.add(Countrystdcode("Egypt", "20", "EG"))
                list.add(Countrystdcode("El Salvador", "503", "SV"))
                list.add(Countrystdcode("Equatorial Guinea", "240", "GQ"))
                list.add(Countrystdcode("Eritrea", "291", "ER"))
                list.add(Countrystdcode("Estonia", "372", "EE"))
                list.add(Countrystdcode("Ethiopia", "251", "ET"))
                list.add(Countrystdcode("Falkland Islands (Malvinas)", "500", "FK"))
                list.add(Countrystdcode("Faroe Islands", "298", "FO"))
                list.add(Countrystdcode("Fiji Islands", "679", "FJ"))
                list.add(Countrystdcode("Finland", "358", "FI"))
                list.add(Countrystdcode("France", "33", "FR"))
                list.add(Countrystdcode("French Antilles", "596", ""))
                list.add(Countrystdcode("French Guiana", "594", ""))
                list.add(Countrystdcode("French Polynesia", "689", "PF"))
                list.add(Countrystdcode("Gabonese Republic", "241", "GA"))
                list.add(Countrystdcode("Gambia", "220", "GM"))
                list.add(Countrystdcode("Georgia", "995", "GE"))
                list.add(Countrystdcode("Germany", "49", "DE"))
                list.add(Countrystdcode("Ghana ", "233", "GH"))
                list.add(Countrystdcode("Gibraltar ", "350", "GI"))
                list.add(Countrystdcode("Greece ", "30", "GR"))
                list.add(Countrystdcode("Greenland ", "299", "GL"))
                list.add(Countrystdcode("Grenada", "1473", ""))
                list.add(Countrystdcode("Guadeloupe", "590", ""))
                list.add(Countrystdcode("Guam", "1671", "GU"))
                list.add(Countrystdcode("Guantanamo Bay", "5399", ""))
                list.add(Countrystdcode("Guatemala ", "502", "GT"))
                list.add(Countrystdcode("Guinea-Bissau ", "245", "GW"))
                list.add(Countrystdcode("Guinea", "224", "GN"))
                list.add(Countrystdcode("Guyana", "592", "GY"))
                list.add(Countrystdcode("Haiti ", "509", "HT"))
                list.add(Countrystdcode("Honduras", "504", "HN"))
                list.add(Countrystdcode("Hong Kong", "852", "HK"))
                list.add(Countrystdcode("Hungary ", "36", "HU"))
                list.add(Countrystdcode("Iceland", "354", "IS"))
                list.add(Countrystdcode("India", "91", "IN"))
                list.add(Countrystdcode("Indonesia", "62", "ID"))
                list.add(Countrystdcode("Iran", "98", "IR"))
                list.add(Countrystdcode("Iraq", "964", ""))
                list.add(Countrystdcode("Ireland", "353", "IE"))
                list.add(Countrystdcode("Israel ", "972", "IL"))
                list.add(Countrystdcode("Italy ", "39", "IT"))
                list.add(Countrystdcode("Jamaica ", "1876", "JM"))
                list.add(Countrystdcode("Japan ", "81", "JP"))
                list.add(Countrystdcode("Jordan", "962", "JO"))
                list.add(Countrystdcode("Kazakhstan", "7", "KZ"))
                list.add(Countrystdcode("Kenya", "254", "KE"))
                list.add(Countrystdcode("Kiribati ", "686", "KI"))
                list.add(Countrystdcode("Korea (North)", "850", ""))
                list.add(Countrystdcode("Korea (South)", "82", ""))
                list.add(Countrystdcode("Kuwait ", "965", "KW"))
                list.add(Countrystdcode("Kyrgyz Republic", "996", ""))
                list.add(Countrystdcode("Laos", "856", "LA"))
                list.add(Countrystdcode("Latvia ", "371", "LV"))
                list.add(Countrystdcode("Lebanon", "961", "LB"))
                list.add(Countrystdcode("Lesotho", "266", "LS"))
                list.add(Countrystdcode("Liberia", "231", "LR"))
                list.add(Countrystdcode("Libya", "218", "LY"))
                list.add(Countrystdcode("Liechtenstein", "423", "LI"))
                list.add(Countrystdcode("Lithuania ", "370", "LT"))
                list.add(Countrystdcode("Luxembourg", "352", "LU"))
                list.add(Countrystdcode("Macao", "853", "MO"))
                list.add(Countrystdcode("Macedonia (Former Yugoslav Rep of.)", "389", "MK"))
                list.add(Countrystdcode("Madagascar", "261", "MG"))
                list.add(Countrystdcode("Malawi ", "265", "MW"))
                list.add(Countrystdcode("Malaysia", "60", "MY"))
                list.add(Countrystdcode("Maldives", "960", "MV"))
                list.add(Countrystdcode("Mali Republic", "223", "ML"))
                list.add(Countrystdcode("Malta", "356", "MT"))
                list.add(Countrystdcode("Marshall Islands", "692", "MH"))
                list.add(Countrystdcode("Martinique", "596", ""))
                list.add(Countrystdcode("Mauritania", "222", "MR"))
                list.add(Countrystdcode("Mauritius", "230", "MU"))
                list.add(Countrystdcode("Mayotte Island", "269", ""))
                list.add(Countrystdcode("Mexico", "52", "MX"))
                list.add(Countrystdcode("Micronesia, (Federal States of)", "691", "FM"))
                list.add(Countrystdcode("Midway Island", "1808", ""))
                list.add(Countrystdcode("Moldova ", "373", ""))
                list.add(Countrystdcode("Monaco", "377", "MC"))
                list.add(Countrystdcode("Mongolia ", "976", "MN"))
                list.add(Countrystdcode("Montenegro", "382", "ME"))
                list.add(Countrystdcode("Montserrat ", "1664", "MS"))
                list.add(Countrystdcode("Morocco", "212", "MA"))
                list.add(Countrystdcode("Mozambique", "258", "MZ"))
                list.add(Countrystdcode("Myanmar", "95", "MM"))
                list.add(Countrystdcode("Namibia", "264", "NA"))
                list.add(Countrystdcode("Nauru", "674", "NR"))
                list.add(Countrystdcode("Nepal ", "977", "NP"))
                list.add(Countrystdcode("Netherlands", "31", "NL"))
                list.add(Countrystdcode("Netherlands Antilles", "599", "AN"))
                list.add(Countrystdcode("Nevis", "1869", ""))
                list.add(Countrystdcode("New Caledonia", "687", "NC"))
                list.add(Countrystdcode("New Zealand", "64", "NZ"))
                list.add(Countrystdcode("Nicaragua", "505", "NI"))
                list.add(Countrystdcode("Niger", "227", ""))
                list.add(Countrystdcode("Nigeria", "234", "NG"))
                list.add(Countrystdcode("Niue", "683", "NU"))
                list.add(Countrystdcode("Norfolk Island", "672", ""))
                list.add(
                    Countrystdcode(
                        "Northern Marianas Islands (Saipan, Rota, & Tinian)",
                        "1670",
                        "MP"
                    )
                )
                list.add(Countrystdcode("Norway", "47", "NO"))
                list.add(Countrystdcode("Oman", "968", "OM"))
                list.add(Countrystdcode("Pakistan", "92", "PK"))
                list.add(Countrystdcode("Palau", "680", "PW"))
                list.add(Countrystdcode("Palestinian Settlements", "970", ""))
                list.add(Countrystdcode("Panama", "507", "PA"))
                list.add(Countrystdcode("Papua New Guinea", "675", "PG"))
                list.add(Countrystdcode("Paraguay", "595", "PY"))
                list.add(Countrystdcode("Peru", "51", "PE"))
                list.add(Countrystdcode("Philippines", "63", "PH"))
                list.add(Countrystdcode("Poland", "48", "PL"))
                list.add(Countrystdcode("Portugal", "351", "PT"))
                list.add(Countrystdcode("Puerto Rico", "1787", "PR"))
                list.add(Countrystdcode("Qatar", "974", "QA"))
                list.add(Countrystdcode("Réunion Island", "262", ""))
                list.add(Countrystdcode("Romania", "40", "RO"))
                list.add(Countrystdcode("Russia", "7", "RU"))
                list.add(Countrystdcode("Rwandese Republic", "250", "RW"))
                list.add(Countrystdcode("St. Helena", "290", "SH"))
                list.add(Countrystdcode("St. Kitts/Nevis", "1869", "KN"))
                list.add(Countrystdcode("St. Lucia", "1758", "LC"))
                list.add(Countrystdcode("St. Pierre & Miquelon", "508", "PM"))
                list.add(Countrystdcode("St. Vincent & Grenadines", "1784", "VC"))
                list.add(Countrystdcode("Samoa", "685", "WS"))
                list.add(Countrystdcode("San Marino", "378", "SM"))
                list.add(Countrystdcode("São Tomé and Principe", "239", "ST"))
                list.add(Countrystdcode("Saudi Arabia", "966", "SA"))
                list.add(Countrystdcode("Senegal ", "221", "SN"))
                list.add(Countrystdcode("Serbia", "381", "RS"))
                list.add(Countrystdcode("Seychelles Republic", "248", ""))
                list.add(Countrystdcode("Sierra Leone", "232", "SL"))
                list.add(Countrystdcode("Singapore", "65", "SG"))
                list.add(Countrystdcode("Slovak Republic", "421", "SK"))
                list.add(Countrystdcode("Slovenia ", "386", "SI"))
                list.add(Countrystdcode("Solomon Islands", "677", "SB"))
                list.add(Countrystdcode("Somali Democratic Republic", "252", ""))
                list.add(Countrystdcode("South Africa", "27", "ZA"))
                list.add(Countrystdcode("Spain", "34", "ES"))
                list.add(Countrystdcode("Sri Lanka", "94", "LK"))
                list.add(Countrystdcode("Sudan", "249", "SD"))
                list.add(Countrystdcode("Suriname ", "597", "SR"))
                list.add(Countrystdcode("Swaziland", "268", "SZ"))
                list.add(Countrystdcode("Sweden", "46", "SE"))
                list.add(Countrystdcode("Switzerland", "41", "CH"))
                list.add(Countrystdcode("Syria", "963", "SY"))
                list.add(Countrystdcode("Taiwan", "886", "TW"))
                list.add(Countrystdcode("Tajikistan", "992", "TJ"))
                list.add(Countrystdcode("Tanzania", "255", "TZ"))
                list.add(Countrystdcode("Thailand", "66", "TH"))
                list.add(Countrystdcode("Timor Leste", "670", ""))
                list.add(Countrystdcode("Togolese Republic", "228", ""))
                list.add(Countrystdcode("Tokelau ", "690", "TK"))
                list.add(Countrystdcode("Tonga Islands", "676", ""))
                list.add(Countrystdcode("Trinidad & Tobago", "1868", "TT"))
                list.add(Countrystdcode("Tunisia", "216", "TN"))
                list.add(Countrystdcode("Turkey", "90 ", "TR"))
                list.add(Countrystdcode("Turkmenistan ", "993", "TM"))
                list.add(Countrystdcode("Turks and Caicos Islands", "1649", "TC"))
                list.add(Countrystdcode("Tuvalu", "688", "TV"))
                list.add(Countrystdcode("Uganda", "256", "UG"))
                list.add(Countrystdcode("Ukraine", "380", "UA"))
                list.add(Countrystdcode("United Arab Emirates", "971", "AE"))
                list.add(Countrystdcode("United Kingdom", "44", "GB"))
                list.add(Countrystdcode("United States of America", "1", "US"))
                list.add(Countrystdcode("US Virgin Islands", "1340", ""))
                list.add(Countrystdcode("Uruguay", "598", "UY"))
                list.add(Countrystdcode("Uzbekistan", "998", "UZ"))
                list.add(Countrystdcode("Vanuatu", "678", "VU"))
                list.add(Countrystdcode("Venezuela", "58", "VE"))
                list.add(Countrystdcode("Vietnam", "84", "VN"))
                list.add(Countrystdcode("Wake Island", "808", ""))
                list.add(Countrystdcode("Wallis and Futuna Islands", "681", "WF"))
                list.add(Countrystdcode("Yemen", "967", "YE"))
                list.add(Countrystdcode("Zambia ", "260", "ZM"))
                list.add(Countrystdcode("Zanzibar", "255", ""))
                list.add(Countrystdcode("Zimbabwe ", "263", "ZW"))

                // for live
                // RequestQueue requestQueue = Volley.newRequestQueue(this);
                // for simulator 4.0
                val requestQueue: RequestQueue = Volley.newRequestQueue(this)
                val jsonBody = JSONObject()
                // getconstant()

//                jsonBody.put("methodname", "getConstant")
//                val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
//                    Request.Method.POST,
//                    ConstApi.BASE_URL.toString() + "indianseafood",
//                    jsonBody,
//                    object : Response.Listener<JSONObject?> {
//
//
//                        override fun onResponse(response: JSONObject?) {
//                            Log.d(TAG, "onResponse: $response")
//                            try {
//                                /* String status = response.getString("status");
//                             String msg = response.getString("msg");
//                             if (status.equalsIgnoreCase("200")) {
//
//                                 String result = response.getString("result");
//                                 JSONArray jsonArray = new JSONArray(result);
//                                 Log.d(TAG, "onArrayResponse: " + result);
//                                 // JSONArray jsonArray = new JSONArray();
//                                // for (int i = 0; i < jsonArray.length(); i++) {
//                                     JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                     String indian = jsonObject.getString("REG_FEE_NON_MEMBER");
//                                     ConstApi.totalindianmoney = Integer.parseInt(indian);
//                                    // ConstApi.totalindianmoney = 10000;
//                                     ConstApi.gstpercentage = Double.valueOf(jsonObject.getString("GST"));
//                                    // ConstApi.gstpercentage = 10;
//
//                                     ConstApi.gst_value =  util.calculategstprice(ConstApi.totalindianmoney,ConstApi.gstpercentage);
//                                        Log.d("gstvalue",String.valueOf(ConstApi.gst_value));
//
//
//                                        String overseas = jsonObject.getString("REG_FEE_OVERSEAS_DEL");
//                                        ConstApi.foreign_reg_value = Double.valueOf(jsonObject.getString("REG_FEE_OVERSEAS_DEL"));
//                                     mEdit.putString("indian", indian);
//                                     mEdit.putString("overseas", overseas).commit();
//
//
//                                     Log.d(TAG, "onGetResponse: " + "indian" + indian + "overseas" + overseas);
//                                     JSONArray country_array = jsonObject.getJSONArray("countryArray");
//                                   / *  for (int j = 0; j < country_array.length(); j++) {
//                                         JSONObject country_obj = country_array.getJSONObject(j);
//                                         count_id = country_obj.getString("id");
//                                         final String count_name = country_obj.getString("name");
//                                         AllDetails allDetails = new AllDetails();
//                                         allDetails.setCountry_id(count_id);
//                                         allDetails.setCountry_name(count_name);
//                                         // mCountryList.add("Country");
//                                         mModelCountry.add(allDetails);
//                                         mCountryList.add(allDetails.getCountry_name());
//                                        // arrCountrystdcode.add(new Countrystdcode(count_name,count_id,))
//
//                                         //  mCountry.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,mCountryList));
//                                         //  mCountry.setSelection(0);
//                                       //  Log.d(TAG, "onCountResponse: " + count_name);
//                                     }*/
//
//
//
///*//                                val status = response!!.getString("status")
////                                val msg = response!!.getString("msg")
////                                if (status.equals("200", ignoreCase = true)) {
////                                    val jresultobj = response!!.getJSONObject("result")
////                                    Log.d(
////                                        TAG,
////                                        "onobjectResponse: $jresultobj"
////                                    )
////
////                                    //for (int i = 0; i < jsonArray.length(); i++) {
////                                    //  JSONObject jsonObject = jresultobj.getJSONObject(i);
////                                    val indian = jresultobj.getString("REG_FEE_NON_MEMBER")
////                                    val overseas = jresultobj.getString("REG_FEE_OVERSEAS_DEL")
////                                    ConstApi.gstpercentage =
////                                        java.lang.Double.valueOf(jresultobj.getString("GST"))
////                                    ConstApi.totalindianmoney = indian.toInt().toDouble()
////                                    ConstApi.gst_value = util.calculategstprice(
////                                        ConstApi.totalindianmoney,
////                                        ConstApi.gstpercentage
////                                    )
////                                    Log.d("gstvalue", java.lang.String.valueOf(ConstApi.gst_value))
////
////
////                                    // String overseas = jresultobj.getString("REG_FEE_OVERSEAS_DEL");
////                                    ConstApi.foreign_reg_value =
////                                        java.lang.Double.valueOf(jresultobj.getString("REG_FEE_OVERSEAS_DEL"))
////                                    mEdit!!.putString("indian", indian)
////                                    mEdit!!.putString("overseas", overseas).commit()
////                                    Log.d(
////                                        TAG,
////                                        "onGetResponse: " + "indian" + indian + "overseas" + overseas
////                                    )
////                                    val country_array = jresultobj.getJSONArray("countryArray")
////                                    for (j in 0 until country_array.length()) {
////                                        val country_obj = country_array.getJSONObject(j)
////                                        count_id = country_obj.getString("id")
////                                        val count_name = country_obj.getString("name")
////                                        val allDetails = AllDetails()
////                                        allDetails.country_id = count_id
////                                        allDetails.country_name = count_name
////                                        // mCountryList.add("Country");
////                                        mModelCountry.add(allDetails)
////                                        mCountryList.add(allDetails.country_name!!)
////                                    }
////                                }*/
//                                setdefaultdelgatevalue()
//                                // hud.dismiss()
//                            } catch (e: JSONException) {
//                                e.printStackTrace()
//                            }
//                        }
//
//
//                    },
//                    object : Response.ErrorListener {
//                        override fun onErrorResponse(error: VolleyError) {
//                            Log.d(TAG, "onErrorResponse: " + error.toString())
//                        }
//                    }) {
//                    //@get:Throws(AuthFailureError::class)
////                    val headers: Map<String, String>
////                        get() {
////                            val params: MutableMap<String, String> =
////                                HashMap()
////                            params["Content-Type"] = "application/json"
////                            params["authorizationkey"] = "20A0751C-9FEE-47F8-A6A9-335BE39"
////                            params["keypassword"] = "aW5kaWFuc2VhZm9vZEAyMDIwIQ"
////                            return params
////                        }
//                }
                //  requestQueue.add(jsonObjectRequest)

                //showprogress.dismiss();
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            getconstant()
        }


        private fun getconstant() {
            val jbookingbarcode = JsonObject()
            jbookingbarcode.addProperty("methodname", "getconstant")


            Log.d("userstatus", jbookingbarcode.toString())

            HttpTask {
                ""
                if (it == null) {
                    Log.d("connection error", "Some thing Went Wrong")

                    return@HttpTask
                }
                try {


                    val eventjsonobj = JSONObject(it.toString())
                    Log.d("respond", eventjsonobj.toString())

                    val status = eventjsonobj.getString("status");
                    val message = eventjsonobj.getString("msg");

                    if (status == "200") {

                        val jresultobj = eventjsonobj!!.getJSONObject("result")
                        Log.d(
                            TAG,
                            "onobjectResponse: $jresultobj"
                        )

                        //for (int i = 0; i < jsonArray.length(); i++) {
                        //  JSONObject jsonObject = jresultobj.getJSONObject(i);
                        val indian = jresultobj.getString("REG_FEE_NON_MEMBER")
                        val overseas = jresultobj.getString("REG_FEE_OVERSEAS_DEL")
                        ConstApi.gstpercentage =
                            java.lang.Double.valueOf(jresultobj.getString("GST"))
                        ConstApi.totalindianmoney = indian.toInt().toDouble()
                        ConstApi.gst_value = util.calculategstprice(
                            ConstApi.totalindianmoney,
                            ConstApi.gstpercentage
                        )
                        Log.d("gstvalue", java.lang.String.valueOf(ConstApi.gst_value))


                        // String overseas = jresultobj.getString("REG_FEE_OVERSEAS_DEL");
                        ConstApi.foreign_reg_value =
                            java.lang.Double.valueOf(jresultobj.getString("REG_FEE_OVERSEAS_DEL"))
                        mEdit!!.putString("indian", indian)
                        mEdit!!.putString("overseas", overseas).commit()
                        Log.d(
                            TAG,
                            "onGetResponse: " + "indian" + indian + "overseas" + overseas
                        )
                        val country_array = jresultobj.getJSONArray("countryArray")
                        for (j in 0 until country_array.length()) {
                            val country_obj = country_array.getJSONObject(j)
                            count_id = country_obj.getString("id")
                            val count_name = country_obj.getString("name")
                            val allDetails = AllDetails(count_id, count_name)
                            // allDetails.country_id = count_id
                            //  allDetails.country_name = count_name
                            // mCountryList.add("Country");
                            mModelCountry.add(allDetails)
                            mCountryList.add(allDetails.country_name!!)
                        }


                    } else {
                        Toast.makeText(MainActivity@ this, message, Toast.LENGTH_LONG).show()
                        //  LoadingScreen.hideLoading()
                        //  callotplatest(edtmobno.text.toString())
                    }

                    setdefaultdelgatevalue()
                } catch (e: JSONException) {
                    println("Exception caught");
                }


            }.execute(
                "POST",
                ConstApi.LIVE_URL + "api.php?requestparm=indianseafood",
                jbookingbarcode.toString().trim()
            )


        }

        private fun getStateDetails(id: String?) {
            try {
//            hud = KProgressHUD.create(this@MainActivity)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Loading..")
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .setMaxProgress(100)
//                .show()
//            hud.setProgress(50)
                mStateList.clear()

                // mStateList.add("State*");
                mState = findViewById(R.id.state_spinner)
                mState!!.getText().clear()
                // mState.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, mStateList));
                mStateList = ArrayList<StateModel>()
                val requestQueue: RequestQueue = Volley.newRequestQueue(this)
                val jsonBody = JSONObject()
                jsonBody.put("methodname", "getState")
                jsonBody.put("country_id", id)
                Log.d("statestatus", jsonBody.toString())

                HttpTask {
                    ""
                    if (it == null) {
                        Log.d("connection error", "Some thing Went Wrong")

                        return@HttpTask
                    }
                    try {


                        val statejsonobj = JSONObject(it.toString())
                        Log.d("respond", statejsonobj.toString())

                        val status = statejsonobj.getString("status");
                        val message = statejsonobj.getString("msg");

                        if (status == "200") {


                            if (message.contains("OK")) {

                                val result = statejsonobj.getString("result")
                                val jsonArray = JSONArray(result)

                                Log.d(
                                    TAG,
                                    "onArrayResponse: $jsonArray"
                                )
                                for (i in 0 until jsonArray.length()) {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    val c_id = jsonObject.getString("countryId")
                                    Log.d(
                                        TAG,
                                        "onIdResponse: $c_id"
                                    )
                                    val country_array = jsonObject.getJSONArray("statesArray")
                                    for (j in 0 until country_array.length()) {
                                        val country_obj = country_array.getJSONObject(j)
                                        //    String count_id = country_obj.getString("countryId");
                                        state_name = country_obj.getString("name")
                                        //  mStateList.add("State");
                                        stateid = country_obj.getString("id")
                                        val stateModel = StateModel()
                                        stateModel.state_id = stateid!!.toInt()
                                        stateModel.state = state_name
                                        mStateList.add(stateModel)
                                        Log.d(
                                            TAG,
                                            "onStateResponse: $state_name"
                                        )
                                    }
                                }
                                getcountrycode(country_name)
                                //  hud.dismiss()
                            }


                        } else {
                            Toast.makeText(MainActivity@ this, message, Toast.LENGTH_LONG).show()

                        }

                        setdefaultdelgatevalue()
                    } catch (e: JSONException) {
                        println("Exception caught");
                    }


                }.execute(
                    "POST",
                    ConstApi.LIVE_URL + "api.php?requestparm=indianseafood",
                    jsonBody.toString().trim()
                )
//            val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
//                Request.Method.POST,
//                ConstApi.BASE_URL.toString() + "indianseafood",
//                jsonBody,
//                object : Response.Listener<JSONObject?> {
//
//
//                    override fun onResponse(response: JSONObject?) {
//                        Log.d(TAG, "onStateResponse: $response")
//                        try {
//                            val status = response?.getString("status")
//                            val msg = response!!.getString("msg")
//                            if (msg.contains("OK")) {
//                                val result = response.getString("result")
//                                val jsonArray = JSONArray(result)
//                                Log.d(
//                                    TAG,
//                                    "onArrayResponse: $result"
//                                )
//                                for (i in 0 until jsonArray.length()) {
//                                    val jsonObject = jsonArray.getJSONObject(i)
//                                    val c_id = jsonObject.getString("countryId")
//                                    Log.d(
//                                        TAG,
//                                        "onIdResponse: $c_id"
//                                    )
//                                    val country_array = jsonObject.getJSONArray("statesArray")
//                                    for (j in 0 until country_array.length()) {
//                                        val country_obj = country_array.getJSONObject(j)
//                                        //    String count_id = country_obj.getString("countryId");
//                                        state_name = country_obj.getString("name")
//                                        //  mStateList.add("State");
//                                        stateid = country_obj.getString("id")
//                                        val stateModel = StateModel()
//                                        stateModel.state_id = stateid!!.toInt()
//                                        stateModel.state = state_name
//                                        mStateList.add(stateModel)
//                                        Log.d(
//                                            TAG,
//                                            "onStateResponse: $state_name"
//                                        )
//                                    }
//                                }
//                                getcountrycode(country_name)
//                                //  hud.dismiss()
//                            }
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                },
//                Response.ErrorListener { error ->
//                    Log.d(
//                        TAG,
//                        "onErrorResponse: " + error.toString()
//                    )
//                })

//            requestQueue.add(jsonObjectRequest)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        /*private void sendjsontoserver() {
            try {
                jarrdelegateregistration = new JSONArray();
                jobjectdelegateregistration = new JSONObject();
                jobjectdelegateregistration.put("methodname","addDelegate");
                jobjectdelegateregistration.put("delegate_name",edtdelgatename.getText().toString());
                jobjectdelegateregistration.put("designation",edtdelgatedesignation.getText().toString());
                jobjectdelegateregistration.put("company_name",edtdelgatecompany.getText().toString());
                jobjectdelegateregistration.put("address",edtaddress.getText().toString());
                jobjectdelegateregistration.put("city",edtcity.getText().toString());
                jobjectdelegateregistration.put("state","38");
                jobjectdelegateregistration.put("zipcode",edtzippincode.getText().toString());
                jobjectdelegateregistration.put("tel_country","91");
                jobjectdelegateregistration.put("fax_country","91");
                jobjectdelegateregistration.put("mob_country","91");
                jobjectdelegateregistration.put("fax","539053485");
                jobjectdelegateregistration.put("mobile","0347344544");
                jobjectdelegateregistration.put("email","test@asd.com");
                jobjectdelegateregistration.put("gst_number",edtgstno.getText().toString());
                jobjectdelegateregistration.put("category","Service Provider");
                jobjectdelegateregistration.put("category_other","sdfsdfsd");


                if (currencytype.equalsIgnoreCase("inr")) {
                    jobjectdelegateregistration.put("delegatetype","1");
                 //   jobjectdelegateregistration.put("totalpayableamount",);
                }
                else {
                    jobjectdelegateregistration.put("delegatetype","1");
                    jobjectdelegateregistration.put("totalpayableamount","44500");
                }
                jobjectdelegateregistration.put("currency",currencytype);
                jobjectdelegateregistration.put("StallDelegateDetails",jarrdelegate);
                jarrdelegateregistration.put(jobjectdelegateregistration);
            } catch (JSONException je) {
                Log.d("jsonexception", je.toString());
            }

        }*/
        fun updateamountfordelegate() {
            if (currencytype.equals("usd", ignoreCase = true)) // for usd dollar
            {
                if (us_result == 0.0) {
                    // us_result = us_gst + 1.18;

                    // us_result = us_gst + 32.40;
                    us_result = ConstApi.foreign_reg_value + ConstApi.foreign_gst_value
                    us_result = us_result + us_result
                    Log.d(TAG, "adddelegate: $us_result")
                    val fin_rslt = NumberFormat.getNumberInstance(Locale.US).format(us_result)
                    mOutputText!!.text = us_result.toString() + ""
                    //  result = gst_value+14750;
                    //   result = gst_value + 12500;
                    // result = ConstApi.gst_value + ConstApi.totalindianmoney;
                    //  totalindianmoney = (int) result;
                    result = 0.0
                    foreignmoney = us_result
                    Log.d(TAG, "adddelegate: $result")
                    val finn_rslt = NumberFormat.getNumberInstance(Locale.US).format(result)
                } else {
                    Log.d(
                        TAG,
                        "adddelegate: value of result$us_result"
                    )
                    // us_result = us_result+1.18;
                    //  us_result = us_result+32.40;
                    us_result = us_result + 212.4
                    Log.d(
                        TAG,
                        "adddelegate: Nextvalue of result$us_result"
                    )
                    val fin_rslt = NumberFormat.getNumberInstance(Locale.US).format(us_result)
                    Log.d(TAG, "adddelegate: $fin_rslt")
                    mOutputText!!.text = fin_rslt + ""
                    // result = result + 14750;
                    //  result = result + ConstApi.totalindianmoney + ConstApi.gst_value;
                    // totalindianmoney = (int) result;
                    result = 0.0
                    foreignmoney = us_result
                    Log.d(
                        TAG,
                        "adddelegate: Nextvalue of result$result"
                    )
                    val finn_rslt = NumberFormat.getNumberInstance(Locale.US).format(result)
                    Log.d(TAG, "adddelegate: $finn_rslt")
                }
            } else  // for indian money
            {
                if (result == 0.0) {
                    // us_result = us_gst+1.18;
                    us_result = us_gst + 32.40
                    Log.d(TAG, "adddelegate: $us_result")
                    val fin_rslt = NumberFormat.getNumberInstance(Locale.US).format(us_result)
                    //  result = gst_value + 12500 + 14750;
                    result =
                        ConstApi.gst_value + ConstApi.totalindianmoney + ConstApi.gst_value + ConstApi.totalindianmoney
                    //  result = ConstApi.gst_value + ConstApi.totalindianmoney;
                    totalindianmoney = result
                    totalamtpasstoccavenue = totalindianmoney
                    Log.d(TAG, "adddelegate: $result")
                    Log.d("TotalIndianMoney", totalindianmoney.toString())
                    val finn_rslt = NumberFormat.getNumberInstance(Locale.US).format(result)
                    mOutputText!!.text = "$finn_rslt.0"
                } else {
                    Log.d(
                        TAG,
                        "adddelegate: value of result$us_result"
                    )
                    //  us_result = us_result+1.18;
                    us_result = us_result + 32.40
                    Log.d(
                        TAG,
                        "adddelegate: Nextvalue of result$us_result"
                    )
                    val fin_rslt = NumberFormat.getNumberInstance(Locale.US).format(us_result)
                    Log.d(TAG, "adddelegate: $fin_rslt")
                    Log.d(
                        TAG,
                        "adddelegate: value of result$result"
                    )
                    // result = result + 14750;
                    result = result + ConstApi.totalindianmoney + ConstApi.gst_value
                    totalindianmoney = result
                    totalamtpasstoccavenue = totalindianmoney
                    Log.d(
                        TAG,
                        "adddelegate: Nextvalue of result$result"
                    )
                    val finn_rslt = NumberFormat.getNumberInstance(Locale.US).format(result)
                    Log.d(TAG, "adddelegate: $fin_rslt")
                    mOutputText!!.text = "$finn_rslt.00"
                }
            }
        }

        internal inner class webapiAsyncTask :
            AsyncTask<String?, Void?, Boolean>() {
            override fun onPreExecute() {
                super.onPreExecute()
                /* mProgressBar = new ProgressDialog(MainActivity.this);
                mProgressBar.setCancelable(false);
                mProgressBar.setMessage("Loading..");
                mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressBar.show();*/
            }

            override fun doInBackground(vararg p0: String?): Boolean? {
                countryDetails
                return false
            }

            override fun onPostExecute(result: Boolean) {


                // mProgressBar.hide();
            }


        }

        fun executePost(targetUrl: String?): String? {
            //String request = "http://android.schoolportal.gr/Service.svc/SaveValues";
            val response = StringBuffer()
            var connection: HttpURLConnection? = null
            try {
                jarrdelegateregistration = JSONArray()
                jobjectdelegateregistration = JSONObject()
                jobjectdelegateregistration!!.put("methodname", "addDelegate")
                jobjectdelegateregistration!!.put("delegate_name", edtdelgatename!!.text.toString())
                jobjectdelegateregistration!!.put(
                    "designation",
                    edtdelgatedesignation!!.text.toString()
                )
                jobjectdelegateregistration!!.put(
                    "company_name",
                    edtdelgatecompany!!.text.toString()
                )
                jobjectdelegateregistration!!.put("address", edtaddress!!.text.toString())
                jobjectdelegateregistration!!.put("city", edtcity!!.text.toString())
                //           jobjectdelegateregistration.put("city","Noida");
                jobjectdelegateregistration!!.put("state", stateid)
                jobjectdelegateregistration!!.put("country", country_id)
                jobjectdelegateregistration!!.put("zipcode", edtzippincode!!.text.toString())
                jobjectdelegateregistration!!.put("tel_country", "+$stdcountrycode")
                jobjectdelegateregistration!!.put("fax_country", "+$stdcountrycode")
                jobjectdelegateregistration!!.put("mob_country", "+$stdcountrycode")
                jobjectdelegateregistration!!.put("fax", edtfax!!.text.toString())
                jobjectdelegateregistration!!.put("mobile", edtmobileno!!.text.toString())
                jobjectdelegateregistration!!.put("email", edtemailid!!.text.toString())
                jobjectdelegateregistration!!.put("gst_number", edtgstno!!.text.toString())
                jobjectdelegateregistration!!.put("category", selectcat.toString())
                jobjectdelegateregistration!!.put(
                    "category_other",
                    edtothercategory!!.text.toString()
                )
                if (currencytype.equals("inr", ignoreCase = true)) {
                    if (noofdelgate == 0) {
                        // totalamtpasstoccavenue =  totalindianmoney+ (int) gst_value;
                        totalamtpasstoccavenue = total_inr
                        Log.d(
                            TAG,
                            "executePost: $totalamtpasstoccavenue"
                        )
                        jobjectdelegateregistration!!.put(
                            "totalpayableamount",
                            totalamtpasstoccavenue
                        )
                    } else {
                        // totalamtpasstoccavenue =  totalindianmoney;
                        jobjectdelegateregistration!!.put(
                            "totalpayableamount",
                            totalamtpasstoccavenue
                        )
                    }
                    jobjectdelegateregistration!!.put("delegatetype", "1")
                } else {
                    if (noofdelgate == 0) {
                        // totalamtpasstoccavenue = foreignmoney+ (int) 32.40;
                        //  jobjectdelegateregistration.put("totalpayableamount", foreignmoney+32.40);
                        foreignmoney = ConstApi.foreign_reg_value + ConstApi.foreign_gst_value
                        jobjectdelegateregistration!!.put("totalpayableamount", foreignmoney)
                    } else {
                        jobjectdelegateregistration!!.put("totalpayableamount", foreignmoney)
                    }
                    jobjectdelegateregistration!!.put("delegatetype", "2")
                }
                jobjectdelegateregistration!!.put("currency", currencytype)

                /* if (!isdelgatedeleted) {
                   createfinaljsondelegatenotdeleted();
               }*/

                //jarrdelegate = new JSONArray(arrdelegatelist);
                createfinaljsondelegate()
                jobjectdelegateregistration!!.put("StallDelegateDetails", jarrdelegate)
                val url = URL(targetUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.doOutput = false
                connection.doInput = true
                connection.instanceFollowRedirects = false
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                connection.setRequestProperty("authorizationkey", "20A0751C-9FEE-47F8-A6A9-335BE39")
                connection.setRequestProperty("keypassword", "aW5kaWFuc2VhZm9vZEAyMDIwIQ")
                //  connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty(
                    "Content-Length",
                    "" + Integer.toString(jobjectdelegateregistration.toString().toByteArray().size)
                )
                connection.useCaches = false
                val wr = DataOutputStream(connection.outputStream)
                wr.writeBytes(jobjectdelegateregistration.toString())

                /* JSONObject jsonParam = new JSONObject();
               jsonParam.put("ID", "25");
               jsonParam.put("description", "Real");
               jsonParam.put("enable", "true");*/

                // wr.writeBytes(jsonParam.toString());
                wr.flush()
                wr.close()

                //Get Response
                val `in`: InputStream = BufferedInputStream(connection.inputStream)
                val rd = BufferedReader(InputStreamReader(`in`))
                var line: String?
                while (rd.readLine().also { line = it } != null) {
                    response.append(line)
                    response.append('\r')
                }
                rd.close()
                strresultrespnse = response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                connection?.disconnect()
            }
            return strresultrespnse
        }

        internal inner class PostdataAsyncTask :
            AsyncTask<String?, Void?, String?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun doInBackground(vararg p0: String?): String? {
                executePost(ConstApi.BASE_URL.toString() + "indianseafood")
                return strresultrespnse
            }

            override fun onPostExecute(resultresponse: String?) {


                // parsejsonresponse();
                var resultresponse = resultresponse
                try {
                    resultresponse = resultresponse!!.replace("^\"|\"$".toRegex(), "")
                    resultresponse = resultresponse.trim { it <= ' ' }
                    jresultresponse = JSONObject(resultresponse)
                    val resultdatanew = jresultresponse!!.getString("result")


                    //resultdata = resultdata.replaceAll("\\\\", "");
                    //resultdata = resultdata.replaceAll("""","");
                    // resultdata = resultdata.replaceAll("^\"|\"$", "");
                    // resultdata = resultdata.trim();
                    Log.d("resultdata", resultdatanew.toString())
                    jresultresponse = JSONObject(resultdatanew)
                    val status = jresultresponse!!.getString("status")
                    val returnStatus = jresultresponse!!.getInt("returnStatus")
                    val txnid = jresultresponse!!.getString("txnId")
                    val orderId = jresultresponse!!.getInt("orderId")
                    totalamtpasstoccavenue = jresultresponse!!.getDouble("totalAmount")
                    //totalamtpasstoccavenue = Double.valueOf(totalamt);
                    val invoiceid = jresultresponse!!.getInt("invoiceId")
                    if (status.equals("200", ignoreCase = true)) {

                        /* */

                        /* Random random = new Random();
                    String orderid = String.format("%07d", random.nextInt(1000001));
                    Log.d("MyApp", "Orderid : " + orderid);*/
                        if (returnStatus == 1) {
                            if (!Constants.ACCESS_CODE_DELEGATE.equals("") && !Constants.MERCHANT_ID_delegate.equals(
                                    ""
                                ) && !Constants.CURRENCY.equals("") && totalamtpasstoccavenue > 0
                            ) {
                                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                                intent.putExtra(
                                    AvenuesParams.ACCESS_CODE,
                                    ServiceUtility.chkNull(Constants.ACCESS_CODE_DELEGATE)
                                        .toString()
                                        .trim()
                                )
                                Log.d(
                                    TAG,
                                    "onPostExecute: delegateCode" + Constants.ACCESS_CODE_DELEGATE.toString()
                                )
                                intent.putExtra(
                                    AvenuesParams.MERCHANT_ID,
                                    ServiceUtility.chkNull(Constants.MERCHANT_ID_delegate)
                                        .toString()
                                        .trim()
                                )
                                Log.d(
                                    TAG,
                                    "onPostExecute: MERCHANT_ID_delegate" + Constants.MERCHANT_ID_delegate.toString()
                                )
                                intent.putExtra(
                                    AvenuesParams.ORDER_ID,
                                    ServiceUtility.chkNull(orderId).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.CURRENCY,
                                    ServiceUtility.chkNull(currencytype).toString().trim()
                                )
                                Log.d(
                                    TAG,
                                    "onPostExecute: CurrencyType" + currencytype.trim { it <= ' ' })
                                intent.putExtra(
                                    AvenuesParams.Merchant_param1,
                                    ServiceUtility.chkNull(txnid).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.Merchant_param3,
                                    ServiceUtility.chkNull(invoiceid).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.Delivery_Name, ServiceUtility.chkNull(
                                        edtdelgatename!!.text.toString()
                                    ).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.Delivery_Address, ServiceUtility.chkNull(
                                        edtaddress!!.text.toString()
                                    ).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.delivery_zip, ServiceUtility.chkNull(
                                        edtzippincode!!.text.toString()
                                    ).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.delivery_country,
                                    ServiceUtility.chkNull(country_name).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.delivery_state,
                                    ServiceUtility.chkNull(state_name).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.delivery_tel, ServiceUtility.chkNull(
                                        edtmobileno!!.text.toString()
                                    ).toString().trim()
                                )
                                intent.putExtra(
                                    AvenuesParams.Delivery_city, ServiceUtility.chkNull(
                                        edtcity!!.text.toString()
                                    ).toString().trim()
                                )
                                if (currencytype.equals("inr", ignoreCase = true)) {
                                    // jobjectdelegateregistration.put("totalpayableamount",totalindianmoney);
                                    // jobjectdelegateregistration.put("delegatetype","1");
                                    intent.putExtra(
                                        AvenuesParams.AMOUNT,
                                        ServiceUtility.chkNull(totalamtpasstoccavenue).toString()
                                            .trim()
                                    )
                                } else {
                                    //  jobjectdelegateregistration.put("totalpayableamount",foreignmoney);
                                    //  jobjectdelegateregistration.put("delegatetype","2");
                                    intent.putExtra(
                                        AvenuesParams.AMOUNT,
                                        ServiceUtility.chkNull(foreignmoney).toString().trim()
                                    )
                                }
                                intent.putExtra(
                                    AvenuesParams.REDIRECT_URL,
                                    Constants.REDIRECT_URL_delegate
                                )
                                intent.putExtra(
                                    AvenuesParams.CANCEL_URL,
                                    Constants.CANCEL_URL_delegate
                                )
                                intent.putExtra(
                                    AvenuesParams.RSA_KEY_URL,
                                    Constants.RSA_KEY_DELEGATE_URL.trim()
                                )
                                Log.d(
                                    TAG,
                                    "onPostExecute:RSA_KEY_DELEGATE_URL " + Constants.RSA_KEY_DELEGATE_URL
                                )
                                intent.putExtra("resultdata", resultdatanew.toString())
                                intent.putExtra("type", "2")
                                startActivity(intent)
                            }
                        } else {
                            AlertDialog.Builder(this@MainActivity)
                                .setTitle("Alert")
                                .setMessage("Something Went Wrong, please try again")
                                .setCancelable(false)
                                .setPositiveButton(
                                    "ok"
                                ) { dialog, which ->
                                    // Whatever...
                                }.show()
                        }
                    } else {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Alert")
                            .setMessage("Something Went Wrong, please try again")
                            .setCancelable(false)
                            .setPositiveButton(
                                "ok"
                            ) { dialog, which ->
                                // Whatever...
                            }.show()
                    }
                } catch (je: JSONException) {
                    Log.d("exception", je.toString())
                }
            }
        }

        fun resetallvalue() {
            edtdelgatename!!.text.clear()
            edtdelgatedesignation!!.text.clear()
            edtdelgatecompany!!.text.clear()
            edtaddress!!.text.clear()
            edtcity!!.text.clear()
            edtzippincode!!.text.clear()
            edtfax!!.text.clear()
            edtmobileno!!.text.clear()
            edtemailid!!.text.clear()
            edtgstno!!.text.clear()
            edttelephone!!.text.clear()
            edtothercategory!!.text.clear()
            spnselectcategory!!.setSelection(0)
            mCountry!!.setSelection(0)
            mState!!.setSelection(0)
            tvdelregdetail!!.visibility = View.GONE
            cddelegatereg!!.visibility = View.GONE
            edtdelgatename!!.isFocusable = true
        }

        fun parsejsonresponse() {}

        // ON DELETE delegate get called
        fun createfinaljsondelegate() {
            try {
                for (i in arrdelegatelist.indices) {
                    jarrdelegate?.put(arrdelegatelist[i].jSONObject)
                }
            } catch (je: Exception) {
                Log.d("exception", je.toString())
            }
        }

        fun createfinaljsondelegatenotdeleted() {
            for (i in 0 until jarrdelegate!!.length()) {
                try {
                    //if (i != position) {
                    jarrfinaldelegate!!.put(jarrdelegate!![i])
                    // }
                } catch (je: JSONException) {
                    Log.d("Exception", je.toString())
                }
            }
        }

        fun getcountrycode(countryname: String?) {
            for (i in list.indices) {
                if (list[i].countryname.equals(countryname, ignoreCase = true)) {
                    Log.d("countrycode", list[i].countrystdcode)
                    stdcountrycode = list[i].countrystdcode
                    break
                }
            }
        }

        /* public void getcountryidbyapi(String countryname) {
            for (int ii=0;ii<mModelCountry.size();ii++) {
                if (mModelCountry.get(ii).getCountry_name().equalsIgnoreCase(countryname)) {
                    country_id = mModelCountry.get(ii).getCountry_id();
                    break;

                }
            }
            getStateDetails(country_id);
        }*/
        fun setdefaultdelgatevalue() {
            mCountry_txt = "India"
            // Toast.makeText(getApplicationContext(), "Indian money" + index, Toast.LENGTH_LONG).show();
            tvcurrencyregfee!!.typeface = fontAwesomeFont
            tvcurrencyregfeeone!!.typeface = fontAwesomeFont
            tvcurrencytotalfee!!.typeface = fontAwesomeFont
            tvcurrencyregfee!!.text = "\uf156"
            tvcurrencyregfeeone!!.text = "\uf156"
            tvcurrencytotalfee!!.text = "\uf156"
            val radioIndian = findViewById<RadioButton>(R.id.radioIndian)
            val radioOverSeas = findViewById<RadioButton>(R.id.radioOverSeas)
            radioIndian.isChecked = true
            val rupee = resources.getString(R.string.Rs)

            //  int indianmoney = Integer.parseInt(mPref.getString("indian", null));
            val strindianmy: String =
                NumberFormat.getIntegerInstance().format(ConstApi.totalindianmoney)
            // mGstTxt.setText(R.string.strfeegst);
            // mGstTxt.setText(R.string.strfeegst);
            tvvalregfee!!.text = strindianmy
            val strgstfee: String =
                java.lang.String.valueOf(ConstApi.gst_value) //NumberFormat.getIntegerInstance().format(ConstApi.gst_value);
            tvvalgstfee!!.text = strgstfee
            // tvvaltotalfee.setText(strforegin);
            //  totalindianmoney = ConstApi.totalindianmoney +  ConstApi.gst_value;
            total_inr = ConstApi.totalindianmoney + ConstApi.gst_value
            val strtotalindianmoney = total_inr.toString()
            Log.d(
                TAG,
                "setdefaultdelgatevalue: $strtotalindianmoney"
            )
            tvvaltotalfee!!.text = strtotalindianmoney
            mGstTxt!!.text = "Fee and GST : $rupee $strtotalindianmoney"
            if (result == 0.0) {
                //  mOutputText.setText("29,750.00");
                //  mOutputText.setText("14,750.00");
                mOutputText!!.text = strtotalindianmoney
                mRstTotal_txt!!.text = rupee
            } else {
                mRstTotal_txt!!.text = rupee
                mOutputText!!.text = result.toString() + ""
            }
            currencytype = "inr"
            totalamtpasstoccavenue = 0.0
            totalindianmoney = 0.0
            if (mainLayout != null) {
                mainLayout!!.removeAllViews()
            }

            // resetallvalue();
            edtdelgatename!!.requestFocus()
        }

        override fun deletedelgateitem(position: Int, name: String?) {
            try {
                if (arrdelegatelist.size == 1) {
                    arrdelegatelist.removeAt(0)
                } else {
                    arrdelegatelist.removeAt(position)
                }
                mdeladatper!!.notifyDataSetChanged()
                if (noofdelgate > 0) {
                    if (currencytype.equals("inr", ignoreCase = true)) {

                        //  totalindianmoney = totalindianmoney - totalindianmoney;

                        // totalindianmoney = totalindianmoney - 14750;

                        // result = totalindianmoney;
                        // totalindianmoney = (int)result - 14750;
                        totalindianmoney = result - ConstApi.totalindianmoney - ConstApi.gst_value
                        result = totalindianmoney
                        totalamtpasstoccavenue = totalindianmoney
                        noofdelgate = noofdelgate - 1
                        val finn_rslt =
                            NumberFormat.getNumberInstance(Locale.US).format(totalindianmoney)
                        Log.d(TAG, "adddelegate: $totalindianmoney")
                        mOutputText!!.text = finn_rslt
                    } else {

                        //  foreignmoney = foreignmoney - foreignmoney;
                        // foreignmoney = foreignmoney - 212.4;
                        foreignmoney =
                            foreignmoney - (ConstApi.foreign_reg_value + ConstApi.foreign_gst_value)
                        us_result = foreignmoney
                        val finn_rslt =
                            NumberFormat.getNumberInstance(Locale.US).format(foreignmoney)
                        Log.d(TAG, "adddelegate: $foreignmoney")
                        //mOutputText.setText(finn_rslt + "." + "00");
                        mOutputText!!.text = finn_rslt
                    }

                    //   noofdelgate = noofdelgate - 1;
                } else if (noofdelgate == 0) {
                    if (currencytype.equals("inr", ignoreCase = true)) {
                        //  totalindianmoney = 14750;
                        totalindianmoney += ConstApi.gst_value.toInt()
                        totalamtpasstoccavenue = totalindianmoney
                        noofdelgate -= 1
                        result = totalindianmoney
                        val finn_rslt =
                            NumberFormat.getNumberInstance(Locale.US).format(totalindianmoney)
                        Log.d(TAG, "adddelegate: $totalindianmoney")
                        mOutputText!!.text = finn_rslt
                    } else {
                        // foreignmoney = 212.4;
                        us_result = ConstApi.foreign_reg_value + ConstApi.foreign_gst_value
                        // us_result = foreignmoney;
                        foreignmoney = us_result
                        val finn_rslt =
                            NumberFormat.getNumberInstance(Locale.US).format(foreignmoney)
                        Log.d(TAG, "adddelegate: $foreignmoney")
                        // mOutputText.setText(finn_rslt + "." + "00");
                        mOutputText!!.text = finn_rslt
                    }
                }
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
        }

        private fun filter(text: String) {
            val country_list: MutableList<AllDetails> = ArrayList<AllDetails>()
            for (allDetails in mModelCountry) {
                if (allDetails.country_name?.lowercase(Locale.ROOT)
                    !!.contains(text.lowercase(Locale.getDefault()))
                ) {
                    country_list.add(allDetails)
                }
            }
            mCountryadapter_delegate!!.filterList(country_list)
        }

        private fun filterState(text: String) {
            val state_list: MutableList<StateModel> = ArrayList<StateModel>()
            for (stateModel in mStateList) {
                if (stateModel.state?.lowercase(Locale.ROOT)!!
                        .contains(text.lowercase(Locale.getDefault()))
                ) {
                    state_list.add(stateModel)
                }
            }
            mStateadatper_delegate!!.filterList(state_list)
        }

        override fun onSelectCountry(position: Int, id: Int, country_name: String) {
            this.country_name = country_name
            country_id = id.toString()
            Log.d(TAG, "onSelectCountry: $country_name")
        }

        override fun clicktogetState(id: Int, state_name: String) {
            stateid = id.toString()
            this.state_name = state_name
        }

        companion object {
            private val TAG = MainActivity::class.java.simpleName
        }
    }


















