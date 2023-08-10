package com.app.seafoodapp.ui


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.app.seafoodapp.*
import com.app.seafoodapp.Const.ConstApi
import com.app.seafoodapp.Interface.*
import com.app.seafoodapp.Network.HttpTask
import com.app.seafoodapp.Network.NetworkConnect
import com.app.seafoodapp.lib.activity.WebViewActivity
import com.app.seafoodapp.lib.utility.AvenuesParams
import com.app.seafoodapp.lib.utility.Constants
import com.app.seafoodapp.lib.utility.ServiceUtility
import com.app.seafoodapp.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.model.AllResult
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class StallFormActivity : AppCompatActivity(), View.OnClickListener,
    StallInterface, RemoveStall,
    CountryInterface, StateInterface {
    private var mCategory: Spinner? = null
    private var mCountry: EditText? = null
    private var mState: EditText? = null
    private var fontAwesomewebFont: Typeface? = null
    private var fontAwesomeFont: Typeface? = null
    private var country_recycler: RecyclerView? = null
    private var mStateRecycler: RecyclerView? = null
    private val mCategoryList: MutableList<String> = ArrayList()
    private var mStallList: List<String> = ArrayList()
    private val mAllStallList: MutableList<String> = ArrayList()
    private var mModelList: List<AllResult> = ArrayList<AllResult>()
    private var mMemberStallList:MutableList<AllResult> = ArrayList<AllResult>()
    private val mModelCountry: MutableList<AllDetails> = ArrayList<AllDetails>()
    private val mCountryList: List<String> = ArrayList()
    private val mStateList: MutableList<StateModel> = ArrayList<StateModel>()
    private val mStallModelList: MutableList<StallDetailModel> = ArrayList<StallDetailModel>()
    private val mStallDelegateCounter: MutableList<StallDelegateCounter> =
        ArrayList<StallDelegateCounter>()
    private var mStallSpinner: Spinner? = null
    private var mPaymentButton: Button? = null
    private var btnchkvalidity: Button? = null
    private var mStallLayout: LinearLayout? = null
    private var mpedaseaichk: LinearLayout? = null
    private var mStallText: TextView? = null
    private var mDomainText: TextView? = null
    private var tvPowercheck: TextView? = null
    private var tvfreezerchk: TextView? = null
    private var tvrawspacecheck: TextView? = null
    private var tvshellschemechk: TextView? = null
    private var tvmpedaseairegchk: TextView? = null
    private var mpedaricon: TextView? = null
    private var datempedaicon: TextView? = null
    private var seaiicon: TextView? = null
    private var dateseaiicon: TextView? = null
    private var tvreggivempeda: TextView? = null
    private var tvreggiveseai: TextView? = null
    private var mStallView: View? = null
    private var mStallReg: CardView? = null
    private var mpedaseaichkcrd: CardView? = null
    private var cdmpedaregno: CardView? = null
    private var cdmpedaregdate: CardView? = null
    private var cdseaidate: CardView? = null
    private var cdseairegno: CardView? = null
    private var mStallRecycler: RecyclerView? = null
    private var mStallDetailRecycler: RecyclerView? = null
    private var mBack: ImageView? = null
    private var mClose: ImageView? = null
    private var currency_img_stall_form: ImageView? = null
    private val mStallDelegate: EditText? = null
    private var mStallfasicaname: EditText? = null
    private var mStallCompany: EditText? = null
    private var mStallAddress: EditText? = null
    private var mStallCity: EditText? = null
    private var mStallPin: EditText? = null
    private var mStallTelephone: EditText? = null
    private var mStallFax: EditText? = null
    private var mStallMobile: EditText? = null
    private var mStallEmail: EditText? = null
    private var mStallGst: EditText? = null
    private var mStallProducts: EditText? = null
    private var mStallOther: EditText? = null
    private var mDelegateName: EditText? = null
    private var mDesignation: EditText? = null
    private var mPhone: EditText? = null
    private var mEmail: EditText? = null
    private var editmpedaregdate: EditText? = null
    private var editseairegdate: EditText? = null
    private var editmpedaregnumber: EditText? = null
    private var editseairegnumber: EditText? = null
    private var edtotherreqspecify: EditText? = null
    private var edtothercategory_stall: EditText? = null
    private val mDomainString = "null"
    private var count_id: String? = null
    lateinit var stringArray: Array<String>
    var countryList: List<String>? = null
    lateinit var statearr: Array<String>
    var statelist: List<String>? = null
    private var country_name = "null"
    private var state_name = "null"
    private var selectcatstall = "null"
    private var fasica_name: String? = null
    private val designation: String? = null
    private var company: String? = null
    private var address: String? = null
    private var city: String? = null
    private var pin: String? = null
    private var telephone: String? = null
    private var fax: String? = null
    private var mobile: String? = null
    private var email: String? = null
    private var gst: String? = null
    private var products: String? = null
    private var other: String? = null
    private var final_result = 0.0
    var isAcceptedConditions = false

    //private int member_total_price = 0;
    private var member_total_price = 0.0
    private var non_member_total_price = 0.0
    private var categoryposition = 0
    private val getMember_total_price = 0
    private var stall_position = 0
    private var mTotalValue: TextView? = null
    private var tvmpedaseairegmem: TextView? = null
    private var mRegType = "null"
    private var mSeatType = "null"
    private var country_id: String? = null
    private var state_id: String? = null
    private var delegateArray: JsonArray? = null
    private var designationArray: JsonArray? = null
    private var phoneArray: JsonArray? = null
    private var emailArray: JsonArray? = null
    private var stallArray: JsonArray? = null
    private var memberArray: JsonArray? = null
    private var nonMemberArray: JsonArray? = null
    private var stallObject: JsonObject? = null
    private var stall_delegate_name: String? = null
    private var stall_delegate_designation: String? = null
    private var stall_delegate_phone: String? = null
    private var stall_delegate_email: String? = null
    var mStallDetailAdapter: StallDetailAdapter? = null


    private var mpedastatus: String? = null
    private var currencytype: String? = null
    private var regtype = 1
    private var valpwer = 0
    private var valfreezer = 0
    private var valrawspace = 0
    private var valshellscheme = 0
    private var ismpedaseaimember = 0
    private var radioGroupmpedaseaichk //1 for mpeda 2 for seai
            : RadioGroup? = null
    private var mParent: LinearLayout? = null
    private var mCountryadapter: CountryAdapter? = null
    private var mCountryName: String? = null
    private var mCountryId = 0
    private var mState_id = 0
    private var mStateName: String? = null
    private var value: String? = null
    private var mStateAdapter: StateAdapter? = null
    private var mStallRegText: TextView? = null
    private var mSelectedStall: TextView? = null
    private var mEnterDetail: TextView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stall_form)
        if (getIntent().getStringExtra("RegType") != null) {
            mRegType = getIntent().getStringExtra("RegType").toString()
        }
        if (getIntent().getStringExtra("SeatType") != null) {
            mSeatType = getIntent().getStringExtra("SeatType").toString()
        }
        if (getIntent().getStringExtra("currencytype") != null) {
            currencytype = getIntent().getStringExtra("currencytype")
        }
        init()
        initText()
        initializeArray()
        getCountryDetails(this@StallFormActivity)
        /*if (mDomainString!="null")
        {
            mDomainText.setText(mDomainString);
        }*/if (mRegType.equals("1", ignoreCase = true)) {
            mDomainText!!.setText("Indian")
        } else if (mRegType.equals("2", ignoreCase = true)) {
            mDomainText!!.setText("Overseas")
        }
        setCategoryData(this)
        getModelListData(this)
        getListData(this)
        val mCheckBox = findViewById<CheckBox>(R.id.check_box)
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

        val ss = SpannableString("I accept the Terms and Conditions")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun onClick(textView: View) {

                startActivity(Intent(this@StallFormActivity, TermsConditionActivity::class.java))
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
        mPaymentButton!!.setOnClickListener(this)
        mStallLayout!!.setOnClickListener(this)
        mBack!!.setOnClickListener(this)
        mClose!!.setOnClickListener(this)
        tvPowercheck!!.setOnClickListener(this)
        tvfreezerchk!!.setOnClickListener(this)
        tvrawspacecheck!!.setOnClickListener(this)
        tvshellschemechk!!.setOnClickListener(this)
        tvmpedaseairegchk!!.setOnClickListener(this)
        editmpedaregdate!!.setOnClickListener(this)
        editseairegdate!!.setOnClickListener(this)
        btnchkvalidity!!.setOnClickListener(this)
        mCountry!!.setOnClickListener(this)
        mState!!.setOnClickListener(this)
        radioGroupmpedaseaichk!!.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                if (radioGroupmpedaseaichk!!.getCheckedRadioButtonId() == R.id.radiompeda) {
                    regtype = 1 // FOR MPEDA
                    Log.d(TAG, "onCheckedChanged: $mRegType")
                    btnchkvalidity!!.isEnabled = true
                    // setBlockData(mRegType,mSeatType);
                    mpedaseaichkcrd!!.setVisibility(View.VISIBLE)
                    tvreggivempeda!!.setVisibility(View.VISIBLE)
                    cdmpedaregno!!.setVisibility(View.VISIBLE)
                    cdmpedaregdate!!.setVisibility(View.VISIBLE)
                    tvreggiveseai!!.setVisibility(View.GONE)
                    cdseaidate!!.setVisibility(View.GONE)
                    cdseairegno!!.setVisibility(View.GONE)
                } else {
                    regtype = 2 // FOR SEAI
                    Log.d(TAG, "onCheckedChanged: $mRegType")
                    btnchkvalidity!!.isEnabled = true
                    //  setBlockData(mRegType,mSeatType);
                    mpedaseaichkcrd!!.setVisibility(View.VISIBLE)
                    tvreggivempeda!!.setVisibility(View.GONE)
                    cdmpedaregno!!.setVisibility(View.GONE)
                    cdmpedaregdate!!.setVisibility(View.GONE)
                    tvreggiveseai!!.setVisibility(View.VISIBLE)
                    cdseaidate!!.setVisibility(View.VISIBLE)
                    cdseairegno!!.setVisibility(View.VISIBLE)
                }
            }
        })
    }

    private fun initText() {
        mStallRegText = findViewById<TextView>(R.id.form_stall_reg)
        mSelectedStall = findViewById<TextView>(R.id.selected_stall_txt)
        mEnterDetail = findViewById<TextView>(R.id.enter_details_txt)
        mRegular = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Medium.otf")
        mStallRegText!!.setTypeface(mMedium)
        mDomainText!!.setTypeface(mMedium)
        mSelectedStall!!.setTypeface(mMedium)
        mEnterDetail!!.setTypeface(mMedium)
        mStallCompany!!.setTypeface(mRegular)
        mStallfasicaname!!.setTypeface(mRegular)
        mStallAddress!!.setTypeface(mRegular)
        mStallCity!!.setTypeface(mRegular)
        mStallPin!!.setTypeface(mRegular)
        mStallTelephone!!.setTypeface(mRegular)
        mStallFax!!.setTypeface(mRegular)
        mStallMobile!!.setTypeface(mRegular)
        mStallEmail!!.setTypeface(mRegular)
        mStallGst!!.setTypeface(mRegular)
        mStallProducts!!.setTypeface(mRegular)
        mStallOther!!.setTypeface(mRegular)
        mDelegateName!!.setTypeface(mRegular)
        mDesignation!!.setTypeface(mRegular)
        mPhone!!.setTypeface(mRegular)
        mEmail!!.setTypeface(mRegular)
        mPaymentButton!!.typeface = mRegular
        val other_req: TextView = findViewById<TextView>(R.id.tvotherreq)
        other_req.setTypeface(mMedium)
        mStallText!!.setTypeface(mMedium)
        val add_new_text: TextView = findViewById<TextView>(R.id.txtadddelegate_stall)
        add_new_text.setTypeface(mMedium)
        val max_txt: TextView = findViewById<TextView>(R.id.tvmaxdelegate)
        max_txt.setTypeface(mMedium)
        val category_text: TextView = findViewById<TextView>(R.id.tvvaldelegate)
        category_text.setTypeface(mRegular)
        val power: TextView = findViewById<TextView>(R.id.tvPower)
        power.setTypeface(mRegular)
        val freezer: TextView = findViewById<TextView>(R.id.tvfreezer)
        freezer.setTypeface(mRegular)
        val raw_space: TextView = findViewById<TextView>(R.id.tvrawspace)
        raw_space.setTypeface(mRegular)
        val scheme: TextView = findViewById<TextView>(R.id.tvshellscheme)
        scheme.setTypeface(mRegular)
    }

    private fun initializeArray() {
        nonMemberArray = JsonArray()
        memberArray = JsonArray()
        delegateArray = JsonArray()
        designationArray = JsonArray()
        phoneArray = JsonArray()
        emailArray = JsonArray()
        stallArray = JsonArray()
    }

    private fun init() {
        fontAwesomewebFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf")
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/FontAwesome.ttf")
        //  mStallDelegate = findViewById(R.id.edtdelgatename_stall);
        mStallfasicaname = findViewById<EditText>(R.id.edtfascianame)
        mStallCompany = findViewById<EditText>(R.id.edtcompany_stall)
        mStallAddress = findViewById<EditText>(R.id.edtaddress_stall)
        mStallCity = findViewById<EditText>(R.id.edtcity_stall)
        mStallPin = findViewById<EditText>(R.id.edtpincode_stall)
        mStallTelephone = findViewById<EditText>(R.id.edt_telephone_stall)
        mStallFax = findViewById<EditText>(R.id.edtfax_stall)
        mStallMobile = findViewById<EditText>(R.id.edtmobileno_stall)
        mStallEmail = findViewById<EditText>(R.id.edtemailid_stall)
        mStallGst = findViewById<EditText>(R.id.edtgstno_stall)
        mStallProducts = findViewById<EditText>(R.id.edtproducts_stall)
        mStallOther = findViewById<EditText>(R.id.edtothercategory_stall)
        mDelegateName = findViewById<EditText>(R.id.edit_stall_delegate_name)
        mDesignation = findViewById<EditText>(R.id.edit_stall_designation)
        mPhone = findViewById<EditText>(R.id.edit_stall_mobile)
        mEmail = findViewById<EditText>(R.id.edit_stall_email)
        mCategory = findViewById<Spinner>(R.id.spnselectcategory_stall)
        btnchkvalidity = findViewById<Button>(R.id.btnchkvalidity)
        tvPowercheck = findViewById<TextView>(R.id.tvPowercheck)
        tvPowercheck!!.setTypeface(fontAwesomewebFont)
        tvPowercheck!!.setText("\uf096")
        tvfreezerchk = findViewById<TextView>(R.id.tvfreezerchk)
        tvfreezerchk!!.setTypeface(fontAwesomewebFont)
        tvfreezerchk!!.setText("\uf096")
        tvrawspacecheck = findViewById<TextView>(R.id.tvrawspacecheck)
        tvrawspacecheck!!.setTypeface(fontAwesomewebFont)
        tvrawspacecheck!!.setText("\uf096")
        tvshellschemechk = findViewById<TextView>(R.id.tvshellschemechk)
        tvshellschemechk!!.setTypeface(fontAwesomewebFont)
        tvshellschemechk!!.setText("\uf096")
        tvmpedaseairegchk = findViewById<TextView>(R.id.tvmpedaseairegchk)
        tvmpedaseairegchk!!.setTypeface(fontAwesomewebFont)
        tvmpedaseairegchk!!.setText("\uf096")
        // tvmpedaseairegchk.setVisibility(View.GONE);
        tvmpedaseairegmem = findViewById<TextView>(R.id.tvmpedaseairegmem)
        // tvmpedaseairegmem.setVisibility(View.GONE);
        mpedaseaichk = findViewById<LinearLayout>(R.id.mpedaseaichk)
        //  mpedaseaichk.setVisibility(View.GONE);
        radioGroupmpedaseaichk = findViewById<RadioGroup>(R.id.radioGroupmpedaseaichk)
        radioGroupmpedaseaichk!!.setVisibility(View.GONE)
        mpedaseaichkcrd = findViewById<CardView>(R.id.mpedaseaichkcrd)
        mpedaseaichkcrd!!.setVisibility(View.GONE)
        /*work on mpeda chk or seai chk*/tvreggivempeda =
            findViewById<TextView>(R.id.tvreggivempeda)
        tvreggiveseai = findViewById<TextView>(R.id.tvreggiveseai)
        cdmpedaregno = findViewById<CardView>(R.id.cdmpedaregno)
        cdmpedaregdate = findViewById<CardView>(R.id.cdmpedaregdate)
        cdseaidate = findViewById<CardView>(R.id.cdseaidate)
        cdseairegno = findViewById<CardView>(R.id.cdseairegno)
        tvreggivempeda!!.setVisibility(View.GONE)
        cdmpedaregno!!.setVisibility(View.GONE)
        cdmpedaregdate!!.setVisibility(View.GONE)
        tvreggiveseai!!.setVisibility(View.GONE)
        cdseaidate!!.setVisibility(View.GONE)
        cdseairegno!!.setVisibility(View.GONE)
        mStallSpinner = findViewById<Spinner>(R.id.spin_to_select_stall)
        mPaymentButton = findViewById<Button>(R.id.payment_button)
        mStallLayout = findViewById<LinearLayout>(R.id.new_stall_layout)
        mStallText = findViewById<TextView>(R.id.stall_text)
        mDomainText = findViewById<TextView>(R.id.stall_domain_txt)
        mStallView = findViewById<View>(R.id.stall_view)
        mStallReg = findViewById<CardView>(R.id.stall_reg_card)
        mStallRecycler = findViewById<RecyclerView>(R.id.selectedstall_recycler)
        mStallDetailRecycler = findViewById<RecyclerView>(R.id.stall_detail_recycler)
        mBack = findViewById<ImageView>(R.id.bacformkicon)
        mClose = findViewById<ImageView>(R.id.closeformicon)
        mCountry = findViewById<EditText>(R.id.country_edit)
        mState = findViewById<EditText>(R.id.state_edit)
        mTotalValue = findViewById<TextView>(R.id.total_stallform_txt)
        mpedaricon = findViewById<TextView>(R.id.mpedaricon)

        mpedaricon!!.setTypeface(fontAwesomeFont)
        mpedaricon!!.setText("\uf25d")
        datempedaicon = findViewById<TextView>(R.id.datempedaicon)
        datempedaicon!!.setTypeface(fontAwesomeFont)
        datempedaicon!!.setText("\uf073")
        seaiicon = findViewById<TextView>(R.id.seaiicon)
        seaiicon!!.setTypeface(fontAwesomeFont)
        seaiicon!!.setText("\uf25d")
        dateseaiicon = findViewById<TextView>(R.id.dateseaiicon)
        dateseaiicon!!.setTypeface(fontAwesomeFont)
        dateseaiicon!!.setText("\uf073")
        editmpedaregnumber = findViewById<EditText>(R.id.editmpedaregnumber)
        editseairegnumber = findViewById<EditText>(R.id.editseairegnumber)
        editmpedaregdate = findViewById<EditText>(R.id.editmpedaregdate)
        editseairegdate = findViewById<EditText>(R.id.editseairegdate)
        edtotherreqspecify = findViewById<EditText>(R.id.edtotherreqspecify)
        edtothercategory_stall = findViewById<EditText>(R.id.edtothercategory_stall)
        mParent = findViewById<LinearLayout>(R.id.stall_form_parent_layout)
        currency_img_stall_form = findViewById<ImageView>(R.id.currency_img_stall_form)
        if (currencytype.equals("$", ignoreCase = true)) {
            val uri = "@drawable/dollar"
            val imgRes: Int = getApplication().getResources()
                .getIdentifier(uri, null, getApplication().getPackageName())
            val resource: Drawable = getApplication().getResources().getDrawable(imgRes)
            currency_img_stall_form!!.setImageDrawable(resource)
        } else if (currencytype.equals("inr", ignoreCase = true)) {
            val uri = "@drawable/rupee_indian"
            val imgRes: Int = getApplication().getResources()
                .getIdentifier(uri, null, getApplication().getPackageName())
            val resource: Drawable = getApplication().getResources().getDrawable(imgRes)
            currency_img_stall_form!!.setImageDrawable(resource)
        }
    }

    private fun setCategoryData(context: Context) {
        mCategoryList.add("Select Category *")
        mCategoryList.add("Service Provider")
        mCategoryList.add("Machinery Manufacture")
        mCategoryList.add("Buyer")
        mCategoryList.add("Buyer Agent")
        mCategoryList.add("Exporter")
        mCategoryList.add("Exporter Staff")
        mCategoryList.add("Service Provider")
        mCategory!!.setAdapter(
            ArrayAdapter<String>(
                context,
                R.layout.simple_spinner_dropdown_item,
                mCategoryList
            )
        )
        mCategory!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    selectcatstall = mCategoryList[position]
                    categoryposition = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: ")
            }
        })
    }

    fun showdatepicker(et: EditText?) {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            this@StallFormActivity,
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker, year: Int,
                    monthOfYear: Int, dayOfMonth: Int
                ) {
                    // et.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    et!!.setText(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                }
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun getModelListData(context: Context) {
        mModelList = (intent.getSerializableExtra("DataList") as List<AllResult>?)!!

        Log.d(TAG, "getModelListData: " + mModelList.size)
        for (i in mModelList.indices) {

            //
            if (mModelList[i].getmstallCategory().equals("A", ignoreCase = true)) {
                mStallDelegateCounter.add(
                    StallDelegateCounter(
                        mModelList[i].getmstallName(),
                        ConstApi.maxvaluecategoryA,
                        0
                    )
                )
            } else if (mModelList[i].getmstallCategory().equals("B", ignoreCase = true)) {
                mStallDelegateCounter.add(
                    StallDelegateCounter(
                        mModelList[i].getmstallName(),
                        ConstApi.maxvaluecategoryB,
                        0
                    )
                )
            } else if (mModelList[i].getmstallCategory().equals("C", ignoreCase = true)) {
                mStallDelegateCounter.add(
                    StallDelegateCounter(
                        mModelList[i].getmstallName(),
                        ConstApi.maxvaluecategoryC,
                        0
                    )
                )
            } else if (mModelList[i].getmstallCategory().equals("D", ignoreCase = true)) {
                mStallDelegateCounter.add(
                    StallDelegateCounter(
                        mModelList[i].getmstallName(),
                        ConstApi.maxvaluecategoryD,
                        0
                    )
                )
            } else if (mModelList[i].getmstallCategory().equals("E", ignoreCase = true)) {
                mStallDelegateCounter.add(
                    StallDelegateCounter(
                        mModelList[i].getmstallName(),
                        ConstApi.maxvaluecategoryE,
                        0
                    )
                )
            }
            stallObject = JsonObject()
            stallObject!!.addProperty("stallid", mModelList[i].id)
            stallObject!!.addProperty("stallname", mModelList[i].getmstallName())
            stallObject!!.addProperty("stallprice", mModelList[i].getmstallPrice())
            stallObject!!.addProperty("stallgst", mModelList[i].getmnonmGstprice())
            stallObject!!.addProperty("stallpricetotal", mModelList[i].getmnonmTotalprice())
            stallObject!!.addProperty("length", mModelList[i].getmstallLength())
            stallObject!!.addProperty("width", mModelList[i].getmstallWidth())
            stallObject!!.addProperty("cornor", mModelList[i].mtotalCorner)

            nonMemberArray?.add(stallObject)
        }
        Log.d(TAG, "ArrayServer: " + " Delegate " + nonMemberArray.toString())
        final_result = getIntent().getDoubleExtra("total_stall_price", 0.0)

        // Log.d(TAG, "getModelListData: " + mModelList.get(0).getStall_price());
        mStallRecycler!!.setLayoutManager(LinearLayoutManager(context))
        mStallRecycler!!.setAdapter(
            StallAdapter(
                context,
                mModelList,
                2,
                currencytype!!,
                this@StallFormActivity
            )
        )
        mTotalValue!!.setText(final_result.toString() + "")
        Log.d(TAG, "getModelListData: FinalResult$final_result")
        // getListData(StallFormActivity.this);
    }

    @SuppressLint("SetTextI18n")
    private fun datawithvalidMember(context: Context) {
        mModelList = (intent.getSerializableExtra("DataList") as List<AllResult>?)!!
        mMemberStallList=ArrayList()
        Log.d(TAG, "getModelListData: " + mModelList.size)
        for (i in mModelList.indices) {
            stallObject = JsonObject()

            stallObject!!.addProperty("stallid", mModelList[i].id)


            stallObject!!.addProperty("stallname", mModelList[i].getmstallName())
            stallObject!!.addProperty("stallprice", mModelList[i].getmnonmStallprice())
            stallObject!!.addProperty("stallgst", mModelList[i].getmnonmGstprice())
            stallObject!!.addProperty("stallpricetotal", mModelList[i].getmnonmTotalprice())
            stallObject!!.addProperty("length", mModelList[i].getmstallLength())
            stallObject!!.addProperty("width", mModelList[i].getmstallWidth())
            stallObject!!.addProperty("cornor", mModelList[i].mtotalCorner)
       //    mModelList[i].

            ConstApi.exuser =1
          //   memberObj=AllResult()
            val allResult = AllResult()
            allResult.id = mModelList[i].id
            allResult.setmstallName(mModelList[i].getmstallName())
            allResult.setmstallLength(mModelList[i].getmstallLength())
            allResult.setmstallWidth(mModelList[i].getmstallWidth())
            allResult.setmnonmStallprice(
                //  mStallList[mStall_position - 1].getmnonmStallprice().toString() + ".0"
                mModelList[i].getmnonmStallprice().toString()
            )
            // for nonmember
            allResult.setmnonmGstprice(mModelList[i].getmnonmGstprice())
            allResult.setmnonmTotalprice(mModelList[i].getmnonmTotalprice())
            //  allResult.member_price = mStallList[mStall_position - 1].member_price
            allResult.mnonmStallprice = mModelList[i].mnonmStallprice
            allResult.mnonmGstprice = mModelList[i].mnonmGstprice
            allResult.mnonmTotalprice = mModelList[i].mnonmTotalprice

            // for member
            allResult.setmmemGstprice(mModelList[i].getmmemGstprice())
            allResult.setmmemTotalprice(mModelList[i].getmmemTotalprice())

            allResult.mmemStallprice = mModelList[i].mmemStallprice
            allResult.mmemGstprice = mModelList[i].mmemGstprice
            allResult.mmemTotalprice = mModelList[i].mmemTotalprice


            allResult.setmstallCategory(mModelList[i].getmstallCategory())
            allResult.setmtotalCorner(mModelList[i].getmtotalCorner())
            Log.d(
                StallFormActivity.TAG,
                "onMemberClick: " + "price " + mModelList[i].mnonmStallprice +
                        "gst " + mModelList[i].mnonmGstprice +
                        "total " + mModelList[i].mnonmTotalprice
            )
            //int price = Integer.parseInt(mModelList.get(i).getMember_total_price());

            if(ConstApi.exuser==1){


                val price: Double = mModelList[i].mmemTotalprice!!.toDouble()

                Log.d(TAG, "datawithvalidMember: $price")
                val totalcornerprice =
                    ConstApi.mcorner_price.toDouble() * mModelList[i].getmtotalCorner()!!
                        .toDouble()

                val cornergstpercentage = (ConstApi.gstpercentage / 100) * totalcornerprice
                val totalStallPrice = totalcornerprice + cornergstpercentage


                member_total_price =
                    member_total_price + totalStallPrice + price
                        .toDouble()
                //  member_total_price = member_total_price + price
                Log.d(TAG, "member_total_price: $member_total_price")
                Log.d(TAG, "ArrayServer: " + " Delegate " + memberArray.toString())

            } else{
                val price: Double = mModelList[i].mnonmTotalprice!!.toDouble()

                Log.d(TAG, "datawithvalidMember: $price")
                val totalcornerprice =
                    ConstApi.mcorner_price.toDouble() * mModelList[i].getmtotalCorner()!!
                        .toDouble()

                val cornergstpercentage = (ConstApi.gstpercentage / 100) * totalcornerprice
                val totalStallPrice = totalcornerprice + cornergstpercentage


                non_member_total_price =
                    non_member_total_price + totalStallPrice + price
                        .toDouble()
                //  member_total_price = member_total_price + price
                Log.d(TAG, "non_member_total_price: $non_member_total_price")
                Log.d(TAG, "ArrayServer: " + " Delegate " + memberArray.toString())
               // mTotalValue!!.setText(non_member_total_price.toString() + "")
            }

            mTotalValue!!.setText(member_total_price.toString() + "")
            memberArray!!.add(stallObject)
            mMemberStallList.add(allResult)

        }

        // Log.d(TAG, "getModelListData: " + mModelList.get(0).getStall_price());
        mStallRecycler!!.setLayoutManager(LinearLayoutManager(context))
        mStallRecycler!!.adapter!!.notifyDataSetChanged();
//        mStallRecycler!!.setAdapter(
//            StallAdapter(
//                context,
//                mMemberStallList,
//
//                3,
//                currencytype!!,
//                this@StallFormActivity
//            )
//        )
    }

    private fun getListData(context: Context) {

        mStallList = intent.getSerializableExtra("StallList") as List<String>
        mAllStallList.add("Select Stall")
        mAllStallList.addAll(mStallList)
        if (mAllStallList.size > 0) {
            val spinnerArrayAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(
                context, R.layout.spinner_item, mAllStallList as List<String?>
            ) {
                override fun isEnabled(position: Int): Boolean {
                    return if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        false
                    } else {
                        true
                    }
                }

                override fun getDropDownView(
                    position: Int, convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: View = super.getDropDownView(position, convertView, parent)
                    val tv: TextView = view as TextView
                    if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor(Color.GRAY)
                    } else {
                        tv.setTextColor(Color.BLACK)
                    }
                    return view
                }
            }
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item)
            mStallSpinner!!.setAdapter(spinnerArrayAdapter)
            mStallSpinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        stall_position = position - 1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d(TAG, "onNothingSelected: ")
                }
            })
        }
    }

    override fun onClick(v: View) {
        if (v === mPaymentButton) {
            if (!isAcceptedConditions) {
                AlertDialog.Builder(this@StallFormActivity)
                    .setMessage("Please Accept the terms and conditions")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Ok"
                    ) { dialog, which ->
                        // Whatever...
                    }.show()
            } else if (NetworkConnect.isNetworkConnected(this@StallFormActivity)) {
                fasica_name = mStallfasicaname!!.getText().toString().trim { it <= ' ' }
                company = mStallCompany!!.getText().toString().trim { it <= ' ' }
                address = mStallAddress!!.getText().toString().trim { it <= ' ' }
                city = mStallCity!!.getText().toString().trim { it <= ' ' }
                pin = mStallPin!!.getText().toString().trim { it <= ' ' }
                country_name = mCountry!!.getText().toString().trim { it <= ' ' }
                state_name = mState!!.getText().toString().trim { it <= ' ' }
                telephone = mStallTelephone!!.getText().toString().trim { it <= ' ' }
                fax = mStallFax!!.getText().toString().trim { it <= ' ' }
                mobile = mStallMobile!!.getText().toString().trim { it <= ' ' }
                email = mStallEmail!!.getText().toString().trim { it <= ' ' }
                gst = mStallGst!!.getText().toString().trim { it <= ' ' }
                products = mStallProducts!!.getText().toString().trim { it <= ' ' }
                other = mStallOther!!.getText().toString().trim { it <= ' ' }

                if (mStallModelList.size == 0) {

                    if (company!!.isEmpty()) {
                        //  mStallCompany.setError("Enter Company Name");
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter Company Name")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (fasica_name!!.isEmpty()) {
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter Fasica Name")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (address!!.isEmpty()) {
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter Address")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (city!!.isEmpty()) {
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter City")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (pin!!.isEmpty()) {
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter Pin/Zip Code")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (mobile!!.isEmpty() || mobile!!.length < 10 || mobile!!.length > 10) {
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter Valid Mobile No.")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (email!!.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email)
                            .matches()
                    ) {
                        // mStallEmail.setError("Enter Valid Email");
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter Valid Email")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (gst!!.isEmpty()) {
                        // mStallGst.setError("Enter Gst");
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Enter GST NO")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (categoryposition == 0) {
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Please Select Category")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (country_name.isEmpty()) {
                        // Toast.makeText(StallFormActivity.this,"Select Country",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Please Select Country")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (state_name.isEmpty()) {
                        // Toast.makeText(StallFormActivity.this,"Select State",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder(this@StallFormActivity)
                            .setTitle("Message")
                            .setMessage("Please Select State")
                            .setCancelable(false)
                            .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // Whatever...
                                }
                            }).show()
                    } else if (mStallModelList.size == 0) {
                        val snackbar: Snackbar = Snackbar.make(
                            mParent!!,
                            "Please Add at least One Delegate details",
                            Snackbar.LENGTH_SHORT
                        )
                        val view: View = snackbar.getView()
                        view.setBackgroundColor(Color.RED)
                        snackbar.show()
                    }
                } else {
                    stall_delegate_name =
                        mDelegateName!!.getText().toString().trim { it <= ' ' }
                    stall_delegate_designation =
                        mDesignation!!.getText().toString().trim { it <= ' ' }
                    stall_delegate_phone = mPhone!!.getText().toString()
                    stall_delegate_email = mEmail!!.getText().toString()
                    sendDatatoServer()
                }

            } else {
                Toast.makeText(
                    this@StallFormActivity,
                    "Check your Internet Connection & try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (v === tvPowercheck) {
            valpwer = if (tvPowercheck!!.getText().toString()
                    .equals("\uf096", ignoreCase = true)
            ) {   // uncheck condition
                tvPowercheck!!.setText("\uf046")
                1
            } else {
                tvPowercheck!!.setText("\uf096") // check condition
                0
            }
        }
        if (v === tvfreezerchk) {
            valfreezer = if (tvfreezerchk!!.getText().toString()
                    .equals("\uf096", ignoreCase = true)
            ) {   // uncheck condition
                tvfreezerchk!!.setText("\uf046")
                1
            } else {
                tvfreezerchk!!.setText("\uf096") // check condition
                0
            }
        }
        if (v === tvrawspacecheck) {
            valrawspace = if (tvrawspacecheck!!.getText().toString()
                    .equals("\uf096", ignoreCase = true)
            ) {   // uncheck condition
                tvrawspacecheck!!.setText("\uf046")
                1
            } else {
                tvrawspacecheck!!.setText("\uf096") // check condition
                0
            }
        }
        if (v === tvshellschemechk) {
            valshellscheme = if (tvshellschemechk!!.getText().toString()
                    .equals("\uf096", ignoreCase = true)
            ) {   // uncheck condition
                tvshellschemechk!!.setText("\uf046")
                1
            } else {
                tvshellschemechk!!.setText("\uf096") // check condition
                0
            }
        }
        if (v === tvmpedaseairegchk) {
            // btnchkvalidity.setEnabled(true);
            if (tvmpedaseairegchk!!.getText().toString().equals("\uf096", ignoreCase = true)) {
                tvmpedaseairegchk!!.setText("\uf046")
                mpedaseaichk!!.setVisibility(View.VISIBLE)
                radioGroupmpedaseaichk!!.setVisibility(View.VISIBLE)
                radioGroupmpedaseaichk!!.clearCheck()
                mpedaseaichkcrd!!.setVisibility(View.GONE)
            } else if (tvmpedaseairegchk!!.getText().toString()
                    .equals("\uf046", ignoreCase = true)
            ) {
                ismpedaseaimember = 0
                editmpedaregdate!!.getText().clear()
                editmpedaregnumber!!.getText().clear()
                editseairegnumber!!.getText().clear()
                editseairegdate!!.getText().clear()
                // btnchkvalidity.setEnabled(true);
                tvmpedaseairegchk!!.setText("\uf096")
                mpedaseaichk!!.setVisibility(View.GONE)
                radioGroupmpedaseaichk!!.setVisibility(View.GONE)
                mpedaseaichkcrd!!.setVisibility(View.GONE)
                getModelListData(this@StallFormActivity)
            }
        }
        if (v === editmpedaregdate) {
            regtype = 1
            showdatepicker(editmpedaregdate)
        }
        if (v === editseairegdate) {
            regtype = 2
            showdatepicker(editseairegdate)
        }
        if (v === btnchkvalidity) {
            if (regtype == 1) {
                val registered_number: String =
                    editmpedaregnumber!!.getText().toString().trim { it <= ' ' }
                val date: String = editmpedaregdate!!.getText().toString().trim { it <= ' ' }
                Log.d(TAG, "RegClick: $registered_number")
                if (registered_number.isEmpty()) {
                    editmpedaregnumber!!.setError("Enter Registration Number")
                } else if (date.isEmpty()) {
                    editmpedaregdate!!.setError("Enter Date")
                } else {
                    var strmpedano = registered_number
                    strmpedano = strmpedano.replace("\\\\".toRegex(), "")
                    if (NetworkConnect.isNetworkConnected(this@StallFormActivity)) {
                        checkmpedauser(strmpedano, date)
                    } else {
                        Toast.makeText(
                            this@StallFormActivity,
                            "Enable your Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else if (regtype == 2) {
                val reg_seai_number: String = editseairegnumber!!.getText().toString().uppercase(
                    Locale.getDefault()
                ).trim { it <= ' ' }
                val seai_date: String = editseairegdate!!.getText().toString().trim { it <= ' ' }
                if (reg_seai_number.isEmpty()) {
                    editseairegnumber!!.setError("Enter Registration Number")
                } else if (seai_date.isEmpty()) {
                    editseairegdate!!.setError("Enter Date")
                } else {
                    checkmpedauser(reg_seai_number, seai_date)
                }
            }
        }
        if (v === mCountry) {
            Log.d(TAG, "onEditClick: ")
            val dialog = Dialog(this@StallFormActivity)
            dialog.setContentView(R.layout.custom_dialog)
            country_recycler = dialog.findViewById<RecyclerView>(R.id.country_recycler)
            val edit_search: EditText = dialog.findViewById<EditText>(R.id.edit_search)
            val ok_txt: TextView = dialog.findViewById<TextView>(R.id.ok_text)
            val cancel_txt: TextView = dialog.findViewById<TextView>(R.id.cancel_text)
            ok_txt.setOnClickListener(View.OnClickListener {
                Log.d(TAG, "onDialogClick: $mCountryName")
                getStateDetails(this@StallFormActivity, mCountryId.toString())
                mCountry!!.setText(mCountryName)
                dialog.dismiss()
            })
            cancel_txt.setOnClickListener(View.OnClickListener { dialog.dismiss() })
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
                value = "Country"
                country_recycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mCountryadapter = CountryAdapter(
                    this@StallFormActivity,
                    mModelCountry,
                    value!!,
                    this@StallFormActivity
                )
                country_recycler!!.setAdapter(mCountryadapter)
            }
            dialog.show()
            val window = dialog.window
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        if (v === mState) {
            // mStateList.clear();
            val dialog = Dialog(this@StallFormActivity)
            dialog.setContentView(R.layout.custom_state_dialog)
            mStateRecycler = dialog.findViewById<RecyclerView>(R.id.state_recycler)
            if (mStateList.size > 0) {
                value = "State"
                mStateRecycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mStateAdapter =
                    StateAdapter(this@StallFormActivity, mStateList, this@StallFormActivity)
                mStateRecycler!!.setAdapter(mStateAdapter)
                dialog.show()
                val window = dialog.window
                window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            } else {
                AlertDialog.Builder(this@StallFormActivity)
                    .setTitle("Message")
                    .setMessage("Please Select Country First")
                    .setCancelable(false)
                    .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            // Whatever...
                        }
                    }).show()
            }
            val edit_search: EditText = dialog.findViewById<EditText>(R.id.edit_search_state)
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
            val ok_txt: TextView = dialog.findViewById<TextView>(R.id.ok_state_txt)
            ok_txt.setOnClickListener(View.OnClickListener {
                mState!!.setText(mStateName)
                dialog.dismiss()
            })
            val cancel_txt: TextView = dialog.findViewById<TextView>(R.id.cancel_state_txt)
            cancel_txt.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        }
        if (v === mStallLayout) {
            //  if (mStallText.getVisibility()==View.VISIBLE && mStallView.getVisibility()==View.VISIBLE && mStallReg.getVisibility()==View.VISIBLE)
            //  {
            stall_delegate_name = mDelegateName!!.getText().toString().trim { it <= ' ' }
            stall_delegate_designation = mDesignation!!.getText().toString().trim { it <= ' ' }
            stall_delegate_phone = mPhone!!.getText().toString()
            stall_delegate_email = mEmail!!.getText().toString()




            if (stall_delegate_name!!.isEmpty()) {
                mDelegateName!!.setError("Enter Delegate Name")
            } else if (stall_delegate_designation!!.isEmpty()) {
                mDesignation!!.setError("Enter Designation")
            } else if (stall_delegate_phone!!.isEmpty() || stall_delegate_phone!!.length < 10) {
                mPhone!!.setError("Enter Valid Mobile No.")
            } else if (stall_delegate_email!!.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                    stall_delegate_email
                ).matches()
            ) {
                mEmail!!.setError("Enter Valid Email")
            } else if (mStallSpinner!!.getSelectedItem().toString() === "Select Stall") {
                Toast.makeText(
                    this@StallFormActivity,
                    "Please Select Stall",
                    Toast.LENGTH_SHORT
                )
                    .show()

            } else {
                insertdelegate(
                    mModelList[stall_position].getmstallCategory()!!,
                    mModelList[stall_position].getmstallName()
                )
            }
            //}
            /* else
            {
                mStallText.setVisibility(View.VISIBLE);
                mStallView.setVisibility(View.VISIBLE);
                mStallReg.setVisibility(View.VISIBLE);
            }*/
        }
        if (v === mBack) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        if (v === mClose) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
    }

    private fun sendDatatoServer() {
        //Toast.makeText(StallFormActivity.this,"Display",Toast.LENGTH_SHORT).show();
        Log.d(
            TAG,
            "Mpeda: " + editmpedaregnumber!!.getText()
                .toString() + "  " + editmpedaregdate!!.getText()
                .toString()
        )
        for (i in mStallModelList.indices) {
            val delegate_name: String? = mStallModelList[i].delegate_name
            val delegate_desig: String? = mStallModelList[i].designation
            val mobile: String? = mStallModelList[i].mobile
            val email: String? = mStallModelList[i].email
            val selected_stall: String? = mStallModelList[i].selected_stall
            delegateArray!!.add(delegate_name)
            designationArray!!.add(delegate_desig)
            phoneArray!!.add(mobile)
            emailArray!!.add(email)
            stallArray!!.add(selected_stall)
            Log.d(
                TAG, "sendArraytoServer: " + "Delegate " + delegateArray.toString() +
                        "Designation " + designationArray.toString() + "Phone " + phoneArray.toString()
                        + "Email " + emailArray.toString() + "Stall " + stallArray.toString()
            )
        }
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("methodname", "stallsave")
            jsonObject.addProperty("stallregtype", mRegType)
            jsonObject.addProperty("stallseattype", mSeatType)
            jsonObject.addProperty("reg_fascianame", fasica_name)
            //  jsonObject.addProperty("designation",designation);
            jsonObject.addProperty("reg_compname", company)
            jsonObject.addProperty("reg_address", address)
            jsonObject.addProperty("reg_city", city)
            jsonObject.addProperty("reg_zip", pin)
            jsonObject.addProperty("reg_country", country_id)
            jsonObject.addProperty("reg_state", state_id)
            // jsonObject.addProperty("tel_country","91");
            jsonObject.addProperty("reg_tel", telephone)
            //  jsonObject.addProperty("fax_country","91");
            jsonObject.addProperty("reg_fax", mStallFax!!.getText().toString())
            jsonObject.addProperty("reg_mobile", mobile)
            jsonObject.addProperty("reg_email", email)
            jsonObject.addProperty("reg_gst", gst)
            jsonObject.addProperty("reg_prodservice", "Services")
            jsonObject.addProperty("reg_category", mCategory!!.getSelectedItem().toString())
            jsonObject.addProperty(
                "reg_othercategory",
                edtothercategory_stall!!.getText().toString()
            )
            jsonObject.addProperty("reg_powerbackup", valpwer)
            jsonObject.addProperty("reg_freezer", valfreezer)
            jsonObject.addProperty("reg_otherreq", edtotherreqspecify!!.getText().toString())
            jsonObject.addProperty("reg_rowspace", valrawspace)
            jsonObject.addProperty("reg_shell", valshellscheme)
            jsonObject.addProperty("reg_mpeda", ismpedaseaimember)
            jsonObject.addProperty("reg_mpedaregno", editmpedaregnumber!!.getText().toString())
            jsonObject.addProperty("reg_mpedaregdate", editmpedaregdate!!.getText().toString())
            jsonObject.addProperty("reg_seairegno", editseairegnumber!!.getText().toString())
            jsonObject.addProperty("reg_seairegdate", editseairegdate!!.getText().toString())
            if (mStallModelList.size > 0) {
                jsonObject.add("delegate_names", delegateArray)
                jsonObject.add("delegate_desig", designationArray)
                jsonObject.add("delegate_mobile", phoneArray)
                //  jsonObject.add("delegate_desig",designationArray);
                jsonObject.add("delegate_linkstall", stallArray)
                if (ismpedaseaimember == 1) {
                    jsonObject.add("stall_selected", memberArray)
                } else {
                    jsonObject.add("stall_selected", nonMemberArray)
                }
            }
            jsonObject.addProperty("currency", currencytype)
            jsonObject.addProperty("exUser", ConstApi.exuser)
            Log.d(TAG, "sendDatatoServer: $jsonObject")
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ConstApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val retroInterface: DataInterface = retrofit.create(DataInterface::class.java)

            val call: Call<StallModel> = retroInterface.getStallResult(jsonObject)
            call.enqueue(object : Callback<StallModel> {
                override fun onResponse(call: Call<StallModel>, response: Response<StallModel>) {
                    //kProgressHUD.dismiss();
                    Log.d(TAG, "onRetroResponse: " + response.body().getmStatus())
                    val message: String? = response.body().getmMsg()
                    val status_code: String? = response.body().getmStatus()
                    if (status_code!!.contains("200")) {
                        val status_result: String? = response.body().getmResult()!!.resultStatus

                        val message: String? = response.body().getmResult()!!.error

                        if (status_result!!.contains("1")) {
                            val transaction_id: String? =
                                response.body().getmResult()!!.transaction_id
                            val reg_id: String? = response.body().getmResult()!!.registratio_id
                            val amount: String? = response.body().getmResult()!!.final_amount
                            val currency: String? = response.body().getmResult()!!.currency
                            val invoice_id: String? = response.body().getmResult()!!.invoice_id
                            val paymentid: String? = response.body().getmResult()!!.payment_id
                            val order_id: String? = response.body().getmResult()!!.order_id
                            Log.d(
                                TAG, "onTrackResponse: " + "status" + status_result
                                        + "tran" + transaction_id + "reg" + reg_id + "amount" + amount
                                        + "currency" + currency + "invoice" + invoice_id
                            )
                            passingData(
                                order_id!!,
                                paymentid!!,
                                currency!!,
                                transaction_id!!,
                                invoice_id!!,
                                amount!!
                            )
                        } else {
                            Toast.makeText(
                                this@StallFormActivity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                            resetform()
                        }
                    }
                }

                override fun onFailure(call: Call<StallModel>, t: Throwable) {
                    Toast.makeText(
                        this@StallFormActivity,
                        "Unable to Fetch data from Server",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onFailure: ")
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun deleteData(id: Int, position: Int, name: String) {}

    // var hud: KProgressHUD? = null
    private fun getCountryDetails(context: Context) {
        try {
//            hud = KProgressHUD.create(this@StallFormActivity)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Loading..")
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .setMaxProgress(100)
//                .show()
//            hud.setProgress(50)
            val requestQueue: RequestQueue = Volley.newRequestQueue(this)
            val jsonBody = JSONObject()
            jsonBody.put("methodname", "getConstant")
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

                    if (status.equals("200", ignoreCase = true)) {
                        val jresultobj: JSONObject = eventjsonobj.getJSONObject("result")
                        Log.d(TAG, "onobjectResponse: $jresultobj")
                        val indian: String = jresultobj.getString("REG_FEE_NON_MEMBER")
                        val overseas: String = jresultobj.getString("REG_FEE_OVERSEAS_DEL")
                        Log.d(
                            TAG,
                            "onGetResponse: " + "indian" + indian + "overseas" + overseas
                        )
                        val country_array: JSONArray =
                            jresultobj.getJSONArray("countryArray")
                        for (j in 0 until country_array.length()) {
                            val country_obj: JSONObject = country_array.getJSONObject(j)
                            count_id = country_obj.getString("id")
                            val count_name: String = country_obj.getString("name")
                            val allDetails = AllDetails(count_id, count_name)
                            //  allDetails.country_id!!.get(count_id!!.toInt())
                            // allDetails.country_name!!.get(count_name!!.toInt())
                            mModelCountry.add(allDetails)
                        }
                    } else {
                        Toast.makeText(StallFormActivity@ this, message, Toast.LENGTH_LONG).show()

                    }


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
//                object : com.android.volley.Response.Listener<JSONObject?> {
//
//                    override fun onResponse(response: JSONObject?) {
//                        Log.d(TAG, "onResponse: " + response.toString())
//                        try {
//                            val status: String = response!!.getString("status")
//                            val msg: String = response.getString("msg")
//                            if (status.equals("200", ignoreCase = true)) {
//                                val jresultobj: JSONObject = response.getJSONObject("result")
//                                Log.d(TAG, "onobjectResponse: $jresultobj")
//                                val indian: String = jresultobj.getString("REG_FEE_NON_MEMBER")
//                                val overseas: String = jresultobj.getString("REG_FEE_OVERSEAS_DEL")
//                                Log.d(
//                                    TAG,
//                                    "onGetResponse: " + "indian" + indian + "overseas" + overseas
//                                )
//                                val country_array: JSONArray =
//                                    jresultobj.getJSONArray("countryArray")
//                                for (j in 0 until country_array.length()) {
//                                    val country_obj: JSONObject = country_array.getJSONObject(j)
//                                    count_id = country_obj.getString("id")
//                                    val count_name: String = country_obj.getString("name")
//                                    val allDetails = AllDetails()
//                                    allDetails.country_id!!.get(count_id!!.toInt())
//                                    allDetails.country_name!!.get(count_name!!.toInt())
//                                    mModelCountry.add(allDetails)
//                                }
//                            }
//                          //  hud.dismiss()
//                        } catch (ex: JSONException) {
//                            ex.printStackTrace()
//                        }
//                    }
//                },
//                object : com.android.volley.Response.ErrorListener {
//                    override fun onErrorResponse(error: VolleyError) {
//                        Log.d(TAG, "onErrorResponse: " + error.toString())
//                    }
//                }) {
////                @get:Throws(AuthFailureError::class)
////                val headers: Map<String, String>
////                    get() {
////                        val params: MutableMap<String, String> = HashMap()
////                        params["Content-Type"] = "application/json"
////                        params["authorizationkey"] = "20A0751C-9FEE-47F8-A6A9-335BE39"
////                        params["keypassword"] = "aW5kaWFuc2VhZm9vZEAyMDIwIQ "
////                        return params
////                    }
//            }
//            requestQueue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun checkmpedauser(mpedano: String?, mpedavalidity: String?) {
        try {
            val requestQueue: RequestQueue = Volley.newRequestQueue(this)
            val jsonBody = JSONObject()
            jsonBody.put("methodname", "checkmpedauser")
            jsonBody.put("mpedano", mpedano)
            jsonBody.put("mpedavalidity", mpedavalidity)
            jsonBody.put("mpedatype", " $regtype")
            Log.d(TAG, "checkmpedauser: json before" + jsonBody.toString())

            HttpTask {
                ""
                if (it == null) {
                    Log.d("connection error", "Some thing Went Wrong")

                    return@HttpTask
                }
                try {


                    val checkmpedauserjsonobj = JSONObject(it.toString())
                    Log.d("respond", checkmpedauserjsonobj.toString())

                    val status = checkmpedauserjsonobj.getString("status");
                    val message = checkmpedauserjsonobj.getString("msg");

                    if (status.contains("200")) {
                        val result: JSONObject = checkmpedauserjsonobj.getJSONObject("result")
                        mpedastatus = result.getString("status")
                        if (mpedastatus.equals("1", ignoreCase = true)) {
                            Log.d(TAG, "disocunted price")
                            if (regtype == 1) {
                                ismpedaseaimember = 1
                                btnchkvalidity!!.isEnabled = false
                                datawithvalidMember(this@StallFormActivity)
                                Toast.makeText(
                                    this@StallFormActivity,
                                    "Valid MPEDA Member",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (regtype == 2) {
                                ismpedaseaimember = 1
                                btnchkvalidity!!.isEnabled = false
                                datawithvalidMember(this@StallFormActivity)
                                Toast.makeText(
                                    this@StallFormActivity,
                                    "Valid SEAI Member",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            tvmpedaseairegchk!!.setText("\uf046")
                            mpedaseaichk!!.setVisibility(View.GONE)
                            radioGroupmpedaseaichk!!.setVisibility(View.GONE)
                            mpedaseaichkcrd!!.setVisibility(View.GONE)
                        } else {
                            Log.d(TAG, "nondisocunted price")
                            ismpedaseaimember = 0
                            Toast.makeText(
                                this@StallFormActivity,
                                "Not a Valid Member",
                                Toast.LENGTH_LONG
                            ).show()
                            tvmpedaseairegchk!!.setText("\uf096")
                            mpedaseaichk!!.setVisibility(View.GONE)
                            radioGroupmpedaseaichk!!.setVisibility(View.GONE)
                            mpedaseaichkcrd!!.setVisibility(View.GONE)
                        }
                    } else {
                        Toast.makeText(StallFormActivity@ this, message, Toast.LENGTH_LONG).show()

                    }


                } catch (e: JSONException) {
                    println("Exception caught");
                }


            }.execute(
                "POST",
                ConstApi.LIVE_URL + "api.php?requestparm=indianseafood",
                jsonBody.toString().trim()
            )

//
//            val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
//                Request.Method.POST,
//                ConstApi.BASE_URL.toString() + "indianseafood",
//                jsonBody,
//                object : com.android.volley.Response.Listener<JSONObject?> {
//
//
//                    override fun onResponse(response: JSONObject?) {
//                        Log.d(TAG, "MpedeaonResponse: " + response.toString())
//                        try {
//                            val status: String = response!!.getString("status")
//                            val msg: String = response.getString("msg")
//                            if (status.contains("200")) {
//                                val result: JSONObject = response.getJSONObject("result")
//                                mpedastatus = result.getString("status")
//                                if (mpedastatus.equals("1", ignoreCase = true)) {
//                                    Log.d(TAG, "disocunted price")
//                                    if (regtype == 1) {
//                                        ismpedaseaimember = 1
//                                        btnchkvalidity!!.isEnabled = false
//                                        datawithvalidMember(this@StallFormActivity)
//                                        Toast.makeText(
//                                            this@StallFormActivity,
//                                            "Valid MPEDA Member",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    } else if (regtype == 2) {
//                                        ismpedaseaimember = 1
//                                        btnchkvalidity!!.isEnabled = false
//                                        datawithvalidMember(this@StallFormActivity)
//                                        Toast.makeText(
//                                            this@StallFormActivity,
//                                            "Valid SEAI Member",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                    tvmpedaseairegchk!!.setText("\uf046")
//                                    mpedaseaichk!!.setVisibility(View.GONE)
//                                    radioGroupmpedaseaichk!!.setVisibility(View.GONE)
//                                    mpedaseaichkcrd!!.setVisibility(View.GONE)
//                                } else {
//                                    Log.d(TAG, "nondisocunted price")
//                                    ismpedaseaimember = 0
//                                    Toast.makeText(
//                                        this@StallFormActivity,
//                                        "Not a Valid Member",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                    tvmpedaseairegchk!!.setText("\uf096")
//                                    mpedaseaichk!!.setVisibility(View.GONE)
//                                    radioGroupmpedaseaichk!!.setVisibility(View.GONE)
//                                    mpedaseaichkcrd!!.setVisibility(View.GONE)
//                                }
//                            }
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                },
//                object : com.android.volley.Response.ErrorListener {
//                    override fun onErrorResponse(error: VolleyError) {
//                        Log.d(TAG, "onErrorResponse: " + error.toString())
//                    }
//                }) {
////                @get:Throws(AuthFailureError::class)
////                val headers: Map<String, String>
////                    get() {
////                        val params: MutableMap<String, String> = HashMap()
////                        params["Content-Type"] = "application/json"
////                        params["authorizationkey"] = "20A0751C-9FEE-47F8-A6A9-335BE39"
////                        params["keypassword"] = "aW5kaWFuc2VhZm9vZEAyMDIwIQ "
////                        return params
////                    }
//            }
//            requestQueue.add(jsonObjectRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStateDetails(context: Context, id: String?) {
        try {
//            hud = KProgressHUD.create(this@StallFormActivity)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Loading..")
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .setMaxProgress(100)
//                .show()
//            hud.setProgress(50)
            mStateList.clear()
            val requestQueue: RequestQueue = Volley.newRequestQueue(this)
            val jsonBody = JSONObject()
            jsonBody.put("methodname", "getState")
            jsonBody.put("country_id", id)

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
                            val result: String = statejsonobj!!.getString("result")
                            val jsonArray = JSONArray(result)
                            Log.d(TAG, "onArrayResponse: $result")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                                val c_id: String = jsonObject.getString("countryId")
                                Log.d(TAG, "onIdResponse: $c_id")
                                val country_array: JSONArray =
                                    jsonObject.getJSONArray("statesArray")
                                Log.d(TAG, "onStateArrayResponse: " + country_array.toString())
                                for (j in 0 until country_array.length()) {
                                    val country_obj: JSONObject = country_array.getJSONObject(j)
                                    state_id = country_obj.getString("id")
                                    state_name = country_obj.getString("name")
                                    val stateModel = StateModel()
                                    stateModel.state_id = state_id!!.toInt()
                                    stateModel.state = state_name
                                    mStateList.add(stateModel)
                                }
                                Log.d(TAG, "onListResponse: " + mStateList.size)
                            }
                        }


                    } else {
                        Toast.makeText(MainActivity@ this, message, Toast.LENGTH_LONG).show()

                    }

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
//                object : com.android.volley.Response.Listener<JSONObject?> {
//                  override  fun onResponse(response: JSONObject?) {
//                        Log.d(TAG, "onStateResponse: " + response.toString())
//                        try {
//                            val status: String = response!!.getString("status")
//                            val msg: String = response!!.getString("msg")
//                            if (msg.contains("OK")) {
//                                val result: String = response!!.getString("result")
//                                val jsonArray = JSONArray(result)
//                                Log.d(TAG, "onArrayResponse: $result")
//                                for (i in 0 until jsonArray.length()) {
//                                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)
//                                    val c_id: String = jsonObject.getString("countryId")
//                                    Log.d(TAG, "onIdResponse: $c_id")
//                                    val country_array: JSONArray =
//                                        jsonObject.getJSONArray("statesArray")
//                                    Log.d(TAG, "onStateArrayResponse: " + country_array.toString())
//                                    for (j in 0 until country_array.length()) {
//                                        val country_obj: JSONObject = country_array.getJSONObject(j)
//                                        state_id = country_obj.getString("id")
//                                        state_name = country_obj.getString("name")
//                                        val stateModel = StateModel()
//                                        stateModel.state_id = state_id!!.toInt()
//                                        stateModel.state = state_name
//                                        mStateList.add(stateModel)
//                                    }
//                                    Log.d(TAG, "onListResponse: " + mStateList.size)
//                                }
//                            }
//                          //  hud.dismiss()
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                },
//                object : com.android.volley.Response.ErrorListener{
//                    override fun onErrorResponse(error: VolleyError) {
//                        Log.d(TAG, "onErrorResponse: " + error.toString())
//                    }
//                })
//            {
////                @get:Throws(AuthFailureError::class)
////                val headers: Map<String, String>
////                    get() {
////                        val params: MutableMap<String, String> = HashMap()
////                        params["Content-Type"] = "application/json"
////                        params["authorizationkey"] = "20A0751C-9FEE-47F8-A6A9-335BE39"
////                        params["keypassword"] = "aW5kaWFuc2VhZm9vZEAyMDIwIQ "
////                        return params
////                    }
//            }
            // requestQueue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun passingData(
        order_id: String,
        pay_id: String,
        currency: String,
        trans_id: String,
        invoice_id: String,
        amount: String
    ) {
        val intent = Intent(this@StallFormActivity, WebViewActivity::class.java)
        intent.putExtra(
            AvenuesParams.ACCESS_CODE,
            ServiceUtility.chkNull(Constants.ACCESS_CODE_STALL).toString().trim()
        )
        Log.d(TAG, "passingData: access_code " + Constants.ACCESS_CODE_STALL.toString())
        intent.putExtra(
            AvenuesParams.MERCHANT_ID,
            ServiceUtility.chkNull(Constants.MERCHANT_ID_stall).toString().trim()
        )
        Log.d(TAG, "passingData: merchantid " + Constants.MERCHANT_ID_stall.toString())
        intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(order_id).toString().trim())
        if (ServiceUtility.chkNull(currency).toString().contains("$")) {
            intent.putExtra(AvenuesParams.CURRENCY, "USD")
        } else {
            intent.putExtra(AvenuesParams.CURRENCY, "INR")
        }
        intent.putExtra(
            AvenuesParams.Merchant_param1,
            ServiceUtility.chkNull(trans_id).toString().trim()
        )
        intent.putExtra(
            AvenuesParams.Merchant_param2,
            ServiceUtility.chkNull(invoice_id).toString().trim()
        )
        intent.putExtra(
            AvenuesParams.Merchant_param3,
            ServiceUtility.chkNull(pay_id).toString().trim()
        )
        intent.putExtra(
            AvenuesParams.Delivery_Name,
            ServiceUtility.chkNull(company).toString().trim()
        )
        intent.putExtra(
            AvenuesParams.Delivery_Address,
            ServiceUtility.chkNull(address).toString().trim()
        )
        intent.putExtra(AvenuesParams.delivery_zip, ServiceUtility.chkNull(pin).toString().trim())
        //  intent.putExtra(AvenuesParams.delivery_country, ServiceUtility.chkNull(mCountry.getSelectedItem()).toString().trim());
        intent.putExtra(
            AvenuesParams.delivery_country,
            ServiceUtility.chkNull(country_name).toString().trim()
        )
        // intent.putExtra(AvenuesParams.delivery_state, ServiceUtility.chkNull(mState.getSelectedItem()).toString().trim());
        intent.putExtra(
            AvenuesParams.delivery_state,
            ServiceUtility.chkNull(state_name).toString().trim()
        )
        intent.putExtra(
            AvenuesParams.delivery_tel,
            ServiceUtility.chkNull(mobile).toString().trim()
        )
        intent.putExtra(AvenuesParams.Delivery_city, ServiceUtility.chkNull(city).toString().trim())
        intent.putExtra("type", "1")
        if (currency.equals("inr", ignoreCase = true) || currency.equals(
                "INR",
                ignoreCase = true
            )
        ) {
            // jobjectdelegateregistration.addProperty("totalpayableamount",totalindianmoney);
            // jobjectdelegateregistration.addProperty("delegatetype","1");
            intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount).toString().trim())
        } else {
            //  jobjectdelegateregistration.addProperty("totalpayableamount",foreignmoney);
            //  jobjectdelegateregistration.addProperty("delegatetype","2");
            //  intent.addPropertyExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(foreignmoney).toString().trim());
            intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount).toString().trim())
        }


        //  intent.putExtra(AvenuesParams.REDIRECT_URL, "https://www.indianseafoodexpo.com/beta2020/mobileTest/responsehandlerstallbooked_Android.php");
        // intent.putExtra(AvenuesParams.CANCEL_URL, "https://www.indianseafoodexpo.com/beta2020/mobileTest/responsehandlerstallbooked_Android.php");
        intent.putExtra(AvenuesParams.REDIRECT_URL, Constants.REDIRECT_URL_stall.trim())
        intent.putExtra(AvenuesParams.CANCEL_URL, Constants.CANCEL_URL_stall.trim())
        intent.putExtra(AvenuesParams.RSA_KEY_URL, Constants.RSA_KEY_STALL_URL.trim())
        Log.d(TAG, "passingData: keyurl " + Constants.RSA_KEY_STALL_URL.toString())
        //  intent.putExtra(AvenuesParams.type, "two");
        startActivity(intent)
    }

    override fun removeElement(position: Int, stall_name: String?) {
        try {
            if (mStallModelList.size > 0) {
                mStallModelList.removeAt(position)
                mStallDetailAdapter!!.notifyDataSetChanged()
                deccounterforstalldelegate(stall_name)
            }
        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        }
    }

    fun deccounterforstalldelegate(stallname: String?) {
        for (st in mStallDelegateCounter.indices) {
            if (mStallDelegateCounter[st].stallName.equals(stallname, ignoreCase = true)) {
                if (mStallDelegateCounter[st].currentCounter > 0) {
                    mStallDelegateCounter[st].currentCounter =
                        mStallDelegateCounter[st].currentCounter - 1
                } else {
                    mStallDelegateCounter[st].currentCounter = 0
                }
            }
        }
    }

    private fun filter(text: String) {
        val country_list: MutableList<AllDetails> = ArrayList<AllDetails>()
        for (allDetails in mModelCountry) {
            if (allDetails.country_name!!.toLowerCase()
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                country_list.add(allDetails)
            }
        }
        mCountryadapter!!.filterList(country_list)
    }

    private fun filterState(text: String) {
        val state_list: MutableList<StateModel> = ArrayList<StateModel>()
        for (stateModel in mStateList) {
            if (stateModel.state!!.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                state_list.add(stateModel)
            }
        }
        mStateAdapter!!.filterList(state_list)
    }

    fun inccounterforstalldelegate(stallname: String?) {
        for (st in mStallDelegateCounter.indices) {
            if (mStallDelegateCounter[st].stallName.equals(stallname, ignoreCase = true)) {
                mStallDelegateCounter[st].currentCounter =
                    mStallDelegateCounter[st].currentCounter + 1
            }
        }
    }

    fun getcountryidbyapi(countryname: String?) {
        for (ii in mModelCountry.indices) {
            if (mModelCountry[ii].country_name.equals(countryname, ignoreCase = true)) {
                country_id = mModelCountry[ii].country_id
                break
            }
        }
        getStateDetails(this, country_id)
    }

    fun checkformaxstalldelegate(stallname: String?): Boolean {
        var ismaxcat = false
        for (jj in mStallDelegateCounter.indices) {
            if (mStallDelegateCounter[jj].stallName.equals(stallname, ignoreCase = true)) {
                if (mStallDelegateCounter[jj].currentCounter === mStallDelegateCounter[jj].maxCounter) {
                    ismaxcat = true
                    break
                } else {
                    ismaxcat = false
                }
            }
        }
        return ismaxcat
    }

    fun insertdelegate(categoryname: String, stallname: String?) {
        if (categoryname.equals("A", ignoreCase = true)) {
            //  if (chkmaxcategoryfordelegatestallA(stallname)) {
            if (checkformaxstalldelegate(stallname)) {
                val snackbar: Snackbar = Snackbar.make(
                    mParent!!,
                    "You have reached Maximum Delegate for a Stall",
                    Snackbar.LENGTH_SHORT
                )
                val view: View = snackbar.getView()
                view.setBackgroundColor(Color.RED)
                snackbar.show()
            } else {
                val stallDetailModel = StallDetailModel()
                stallDetailModel.stall_id = mModelList[stall_position].id
                stallDetailModel.delegate_name = stall_delegate_name
                stallDetailModel.designation = stall_delegate_designation
                stallDetailModel.mobile = stall_delegate_phone
                stallDetailModel.email = stall_delegate_email
                stallDetailModel.selected_stall = mModelList[stall_position].getmstallName()
                stallDetailModel.stall_price = mModelList[stall_position].getmstallPrice()
                stallDetailModel.stall_gst_price =
                    mModelList[stall_position].getmnonmGstprice()!!.toDouble()
                stallDetailModel.stall_total_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_length = mModelList[stall_position].getmstallLength()
                stallDetailModel.stall_width = mModelList[stall_position].getmstallWidth()
                stallDetailModel.stall_category = mModelList[stall_position].getmstallCategory()
                mStallModelList.add(stallDetailModel)
                mStallDetailRecycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mStallDetailAdapter = StallDetailAdapter(
                    this@StallFormActivity,
                    mStallModelList,
                    this@StallFormActivity
                )
                mStallDetailRecycler!!.setAdapter(mStallDetailAdapter)
                mDelegateName!!.getText().clear()
                mDesignation!!.getText().clear()
                mPhone!!.getText().clear()
                mEmail!!.getText().clear()
                mStallSpinner!!.setSelection(0)
                mDelegateName!!.requestFocus()
                // A_Categorycounter = A_Categorycounter + 1;
                inccounterforstalldelegate(stallname)
            }
        }
        if (categoryname.equals("B", ignoreCase = true)) {
            // if (chkmaxcategoryfordelegatestallB(stallname)) {
            if (checkformaxstalldelegate(stallname)) {
                val snackbar: Snackbar = Snackbar.make(
                    mParent!!,
                    "You have reached Maximum Delegate for a Stall",
                    Snackbar.LENGTH_SHORT
                )
                val view: View = snackbar.getView()
                view.setBackgroundColor(Color.RED)
                snackbar.show()
            } else {
                val stallDetailModel = StallDetailModel()
                stallDetailModel.stall_id = mModelList[stall_position].id
                stallDetailModel.delegate_name = stall_delegate_name
                stallDetailModel.designation = stall_delegate_designation
                stallDetailModel.mobile = stall_delegate_phone
                stallDetailModel.email = stall_delegate_email
                stallDetailModel.selected_stall = mModelList[stall_position].getmstallName()
                stallDetailModel.stall_price = mModelList[stall_position].getmnonmStallprice()
                stallDetailModel.stall_gst_price =
                    mModelList[stall_position].getmnonmGstprice()!!.toDouble()
                stallDetailModel.stall_total_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_length = mModelList[stall_position].getmstallLength()
                stallDetailModel.stall_width = mModelList[stall_position].getmstallWidth()
                stallDetailModel.stall_category = mModelList[stall_position].getmstallCategory()
                mStallModelList.add(stallDetailModel)
                mStallDetailRecycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mStallDetailAdapter = StallDetailAdapter(
                    this@StallFormActivity,
                    mStallModelList,
                    this@StallFormActivity
                )
                mStallDetailRecycler!!.setAdapter(mStallDetailAdapter)
                mDelegateName!!.getText().clear()
                mDesignation!!.getText().clear()
                mPhone!!.getText().clear()
                mEmail!!.getText().clear()
                mStallSpinner!!.setSelection(0)
                mDelegateName!!.requestFocus()
                //  B_Categorycounter = B_Categorycounter + 1;
                inccounterforstalldelegate(stallname)
            }
        }
        if (categoryname.equals("C", ignoreCase = true)) {
            // if (chkmaxcategoryfordelegatestallC(stallname)) {
            if (checkformaxstalldelegate(stallname)) {
                val snackbar: Snackbar = Snackbar.make(
                    mParent!!,
                    "You have reached Maximum Delegate for a Stall",
                    Snackbar.LENGTH_SHORT
                )
                val view: View = snackbar.getView()
                view.setBackgroundColor(Color.RED)
                snackbar.show()
            } else {
                val stallDetailModel = StallDetailModel()
                stallDetailModel.stall_id = mModelList[stall_position].id
                stallDetailModel.delegate_name = stall_delegate_name
                stallDetailModel.designation = stall_delegate_designation
                stallDetailModel.mobile = stall_delegate_phone
                stallDetailModel.email = stall_delegate_email
                stallDetailModel.selected_stall = mModelList[stall_position].getmstallName()
                stallDetailModel.stall_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_gst_price =
                    mModelList[stall_position].getmnonmGstprice()!!.toDouble()
                stallDetailModel.stall_total_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_length = mModelList[stall_position].getmstallLength()
                stallDetailModel.stall_width = mModelList[stall_position].getmstallWidth()
                stallDetailModel.stall_category = mModelList[stall_position].getmstallCategory()
                mStallModelList.add(stallDetailModel)
                mStallDetailRecycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mStallDetailAdapter = StallDetailAdapter(
                    this@StallFormActivity,
                    mStallModelList,
                    this@StallFormActivity
                )
                mStallDetailRecycler!!.setAdapter(mStallDetailAdapter)
                mDelegateName!!.getText().clear()
                mDesignation!!.getText().clear()
                mPhone!!.getText().clear()
                mEmail!!.getText().clear()
                mStallSpinner!!.setSelection(0)
                mDelegateName!!.requestFocus()
                //  C_Categorycounter = C_Categorycounter + 1;
                inccounterforstalldelegate(stallname)
            }
        }
        if (categoryname.equals("D", ignoreCase = true)) {
            // if (chkmaxcategoryfordelegatestallD(stallname)) {
            if (checkformaxstalldelegate(stallname)) {
                val snackbar: Snackbar = Snackbar.make(
                    mParent!!,
                    "You have reached Maximum Delegate for a Stall",
                    Snackbar.LENGTH_SHORT
                )
                val view: View = snackbar.getView()
                view.setBackgroundColor(Color.RED)
                snackbar.show()
            } else {
                val stallDetailModel = StallDetailModel()
                stallDetailModel.stall_id = mModelList[stall_position].id
                stallDetailModel.delegate_name = stall_delegate_name
                stallDetailModel.designation = stall_delegate_designation
                stallDetailModel.mobile = stall_delegate_phone
                stallDetailModel.email = stall_delegate_email
                stallDetailModel.selected_stall = mModelList[stall_position].getmstallName()
                stallDetailModel.stall_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_gst_price =
                    mModelList[stall_position].getmnonmGstprice()!!.toDouble()
                stallDetailModel.stall_total_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_length = mModelList[stall_position].getmstallLength()
                stallDetailModel.stall_width = mModelList[stall_position].getmstallWidth()
                stallDetailModel.stall_category = mModelList[stall_position].getmstallCategory()
                mStallModelList.add(stallDetailModel)
                mStallDetailRecycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mStallDetailAdapter = StallDetailAdapter(
                    this@StallFormActivity,
                    mStallModelList,
                    this@StallFormActivity
                )
                mStallDetailRecycler!!.setAdapter(mStallDetailAdapter)
                mDelegateName!!.getText().clear()
                mDesignation!!.getText().clear()
                mPhone!!.getText().clear()
                mEmail!!.getText().clear()
                mStallSpinner!!.setSelection(0)
                mDelegateName!!.requestFocus()
                //  D_Categorycounter = D_Categorycounter +1;
                inccounterforstalldelegate(stallname)
            }
        }
        if (categoryname.equals("E", ignoreCase = true)) {
            //  if (chkmaxcategoryfordelegatestallE(stallname)) {
            if (checkformaxstalldelegate(stallname)) {
                val snackbar: Snackbar = Snackbar.make(
                    mParent!!,
                    "You have reached Maximum Delegate for a Stall",
                    Snackbar.LENGTH_SHORT
                )
                val view: View = snackbar.getView()
                view.setBackgroundColor(Color.RED)
                snackbar.show()
            } else {
                val stallDetailModel = StallDetailModel()
                stallDetailModel.stall_id = mModelList[stall_position].id
                stallDetailModel.delegate_name = stall_delegate_name
                stallDetailModel.designation = stall_delegate_designation
                stallDetailModel.mobile = stall_delegate_phone
                stallDetailModel.email = stall_delegate_email
                stallDetailModel.selected_stall = mModelList[stall_position].getmstallName()
                stallDetailModel.stall_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_gst_price =
                    mModelList[stall_position].getmnonmGstprice()!!.toDouble()
                stallDetailModel.stall_total_price = mModelList[stall_position].getmnonmTotalprice()
                stallDetailModel.stall_length = mModelList[stall_position].getmstallLength()
                stallDetailModel.stall_width = mModelList[stall_position].getmstallWidth()
                stallDetailModel.stall_category = mModelList[stall_position].getmstallCategory()
                mStallModelList.add(stallDetailModel)
                mStallDetailRecycler!!.setLayoutManager(LinearLayoutManager(this@StallFormActivity))
                mStallDetailAdapter = StallDetailAdapter(
                    this@StallFormActivity,
                    mStallModelList,
                    this@StallFormActivity
                )
                mStallDetailRecycler!!.setAdapter(mStallDetailAdapter)
                mDelegateName!!.getText().clear()
                mDesignation!!.getText().clear()
                mPhone!!.getText().clear()
                mEmail!!.getText().clear()
                mDelegateName!!.requestFocus()
                mStallSpinner!!.setSelection(0)
                mDelegateName!!.requestFocus()
                //  D_Categorycounter = D_Categorycounter +1;
                inccounterforstalldelegate(stallname)
            }
        }
    }

    override fun onSelectCountry(position: Int, id: Int, country_name: String) {
        mCountryName = country_name
        mCountryId = id
        Log.d(TAG, "onSelectCountry: $country_name")
    }

    override fun clicktogetState(id: Int, state_name: String?) {
        mState_id = id
        mStateName = state_name
    }

    private fun resetform() {
        mStallfasicaname!!.getText().clear()
        mStallCompany!!.getText().clear()
        mStallAddress!!.getText().clear()
        mStallCity!!.getText().clear()
        mStallPin!!.getText().clear()
        mStallTelephone!!.getText().clear()
        mStallFax!!.getText().clear()
        mStallMobile!!.getText().clear()
        mStallEmail!!.getText().clear()
        mStallGst!!.getText().clear()
        mStallProducts!!.getText().clear()
        mStallOther!!.getText().clear()
        mDelegateName!!.getText().clear()
        mDesignation!!.getText().clear()
        mPhone!!.getText().clear()
        mEmail!!.getText().clear()
    }

    companion object {
        private val TAG = StallFormActivity::class.java.simpleName
    }
}