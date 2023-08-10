package com.app.seafoodapp.ui


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Const.ConstApi

import com.app.seafoodapp.Interface.RetroInterface
import com.app.seafoodapp.Interface.StallInterface
import com.app.seafoodapp.Network.HttpTask
import com.app.seafoodapp.Network.NetworkConnect
import com.app.seafoodapp.R
import com.app.seafoodapp.StallAdapter

import com.app.seafoodapp.lib.activity.usdStallPaymentActivity

import com.app.seafoodapp.model.StallResult
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.model.AllResult
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.text.DecimalFormat
import java.util.ArrayList

class StallActivity : AppCompatActivity(), View.OnClickListener,
    StallInterface {
    private var show_bttn: Button? = null
    private var mSubmit: Button? = null
    private var mAddStall: Button? = null
    private var mCountry_group: RadioGroup? = null
    private var mStallGroup: RadioGroup? = null
    private var mIndian: RadioButton? = null
    private var mOverSeas: RadioButton? = null
    private var mHanger: RadioButton? = null
    private var mExhibition: RadioButton? = null
    private var mStallText: TextView? = null
    private var mStallSpinner: Spinner? = null
    private var mBlockSpinner: Spinner? = null
    private val mSpinnerList: MutableList<String> = ArrayList()
    private val mBlockList: MutableList<String> = ArrayList()
    private var mStallList: List<AllResult> = ArrayList<AllResult>()
    private val mDataList: MutableList<AllResult> = ArrayList<AllResult>()
    private val mStallNameList: MutableList<String> = ArrayList()
    private val mPriceList: List<Double> = ArrayList()
    private var total_stall_price = 0.0
    private var mSelectCard: CardView? = null
    private var mStallRecycler: RecyclerView? = null
    private var mBack: ImageView? = null
    private var mClose: ImageView? = null
    private var mAdapter: StallAdapter? = null
    private var mStall_position = 0
    private var mTotalTxt: TextView? = null
    private var mRegType = "1"
    private var mSeatType = "null"
    private val mPref: SharedPreferences? = null
    private val mEdit: SharedPreferences.Editor? = null
    private var mParent: RelativeLayout? = null

    private var number_of_corners = 4

    //  private var kProgressHUD: KProgressHUD? = null
    private var mCurrency: ImageView? = null
    private var formatter: DecimalFormat? = null
    private var mStallNameSelected: String? = null
    private var currencytype = "INR"
    private var mStallReg: TextView? = null
    private var mDelegateType: TextView? = null
    private var mStallType: TextView? = null
    private var mHeadingBlock: TextView? = null
    private var mHeadingStall: TextView? = null
    private var mStallSelected: TextView? = null
    private var mTotal: TextView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stall)
        init()
        initTextStyle()
        spinnerDummyData()

        formatter = DecimalFormat("#,###,###")
        //kProgressHUD = KProgressHUD(this)
      //  mCountry_group!!.check(R.id.radioIndianStall)
//        if (mCountry_group!!.getCheckedRadioButtonId() == R.id.radioIndianStall) {
//            mRegType = "0"
//            Log.d(TAG, "onChanged: $mRegType")
//        }
        mStallText!!.setText("Indian")
        mCountry_group!!.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                Log.d(TAG, "onCreate: $checkedId")
                if (mCountry_group!!.getCheckedRadioButtonId() == R.id.radioIndianStall) {
                    if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
//                        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                            .setLabel("Please wait")
//                            .setDetailsLabel("Fetching data...")
//                            .setCancellable(true)
//                            .setAnimationSpeed(2)
//                            .setDimAmount(0.5f)
//                            .show()

                        mStallText!!.setText("Indian")
                        mRegType = "1"
                        currencytype = "inr"
                        setBlockData(mRegType, mSeatType)
                        // kProgressHUD.dismiss()
                        Log.d(TAG, "onICheckedChanged: $mRegType")
                        mBlockSpinner!!.setSelection(0)
                        mStallSpinner!!.setSelection(0)
                        mDataList.clear()
                        mStallNameList.clear()
                        total_stall_price = 0.0
                        val uri = "@drawable/rupee_indian"
                        val imgRes: Int = getResources().getIdentifier(uri, null, getPackageName())
                        val resource: Drawable = getResources().getDrawable(imgRes)
                        mCurrency!!.setImageDrawable(resource)
                        mSelectCard!!.setVisibility(View.GONE)
                        mSubmit!!.visibility = View.GONE
                        val intentoverstall = Intent(this@StallActivity, usdStallPaymentActivity::class.java)
                        intentoverstall.putExtra("stallurl", ConstApi.Live_Web_url + "indian_stall_register.php")
                        startActivity(intentoverstall)
                    } else {
                        Toast.makeText(
                            this@StallActivity,
                            "Enable Your Internet Connection",
                            Toast.LENGTH_SHORT
                        )
                    }
                } else {
                    if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
//                        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                            .setLabel("Please wait")
//                            .setDetailsLabel("Fetching data...")
//                            .setCancellable(true)
//                            .setAnimationSpeed(2)
//                            .setDimAmount(0.5f)
//                            .show()
                        mStallText!!.setText("Overseas")
                        mRegType = "2"
                        setBlockData(mRegType, mSeatType)
                        val uri = "@drawable/dollar"
                        currencytype = "$"
                        val imgRes: Int = getResources().getIdentifier(uri, null, getPackageName())
                        val resource: Drawable = getResources().getDrawable(imgRes)
                        mCurrency!!.setImageDrawable(resource)
                        Log.d(TAG, "onOCheckedChanged: $mRegType")
                        mBlockSpinner!!.setSelection(0)
                        mStallSpinner!!.setSelection(0)
                        mDataList.clear()
                        mStallNameList.clear()
                        total_stall_price = 0.0
                        mSelectCard!!.setVisibility(View.GONE)
                        mSubmit!!.visibility = View.GONE
                        //  kProgressHUD.dismiss()
                        val intentoverstall = Intent(this@StallActivity, usdStallPaymentActivity::class.java)
                        intentoverstall.putExtra("stallurl", ConstApi.Live_Web_url + "overseas_stall_register.php")
                        startActivity(intentoverstall)
                    } else {
                        Toast.makeText(
                            this@StallActivity,
                            "Enable Your Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
        mStallGroup!!.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                if (mStallGroup!!.getCheckedRadioButtonId() == R.id.radioHangerStall) {
                    mSeatType = "1"
                    Log.d(TAG, "onCCheckedChanged: $mRegType")
                    mBlockSpinner!!.setSelection(0)
                    mStallSpinner!!.setSelection(0)
                    mDataList.clear()
                    mStallNameList.clear()
                    total_stall_price = 0.0
                    mSelectCard!!.setVisibility(View.GONE)
                    mSubmit!!.visibility = View.GONE
                    if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
//                        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                            .setLabel("Please wait")
//                            .setDetailsLabel("Fetching data...")
//                            .setCancellable(true)
//                            .setAnimationSpeed(2)
//                            .setDimAmount(0.5f)
//                            .show()
                        setBlockData(mRegType, mSeatType)
                    } else {
                        Toast.makeText(
                            this@StallActivity,
                            "Enable Your Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    mSeatType = "2"
                    Log.d(TAG, "onCheckedChanged: $mRegType")
                    mBlockSpinner!!.setSelection(0)
                    mStallSpinner!!.setSelection(0)
                    mDataList.clear()
                    mStallNameList.clear()
                    total_stall_price = 0.0
                    mSelectCard!!.setVisibility(View.GONE)
                    mSubmit!!.visibility = View.GONE
                    if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
//                        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                            .setLabel("Please wait")
//                            .setDetailsLabel("Fetching data...")
//                            .setCancellable(true)
//                            .setAnimationSpeed(2)
//                            .setDimAmount(0.5f)
//                            .show()
                        setBlockData(mRegType, mSeatType)
                    } else {
                        Toast.makeText(
                            this@StallActivity,
                            "Enable Your Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
        show_bttn!!.setOnClickListener(this)
        mSubmit!!.setOnClickListener(this)
        mAddStall!!.setOnClickListener(this)
        mBack!!.setOnClickListener(this)
        mClose!!.setOnClickListener(this)
    }

    private fun initTextStyle() {
        mStallReg = findViewById<TextView>(R.id.stall_reg_txt)
        mDelegateType = findViewById<TextView>(R.id.delegate_type_text)
        mStallType = findViewById<TextView>(R.id.stall_type_txt)
        mHeadingBlock = findViewById<TextView>(R.id.block_txt)
        mHeadingStall = findViewById<TextView>(R.id.stall_txt)
        mStallSelected = findViewById<TextView>(R.id.selected_stall_txtvw)
        mTotal = findViewById<TextView>(R.id.total_amt)
        mTotal!!.setTypeface(mMedium)
        mStallSelected!!.setTypeface(mMedium)
        mRegular = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Medium.otf")
        mStallText!!.setTypeface(mMedium)
        mStallReg!!.setTypeface(mMedium)
        mDelegateType!!.setTypeface(mMedium)
        mAddStall!!.typeface = mMedium
        mStallType!!.setTypeface(mMedium)
        mHeadingBlock!!.setTypeface(mMedium)
        mHeadingStall!!.setTypeface(mMedium)
        mSubmit!!.typeface = mMedium
        mIndian = findViewById<RadioButton>(R.id.radioIndianStall)
        mIndian!!.setTypeface(mMedium)
        mOverSeas = findViewById<RadioButton>(R.id.radioOverSeasStall)
        mOverSeas!!.setTypeface(mMedium)
        mHanger = findViewById<RadioButton>(R.id.radioHangerStall)
        mHanger!!.setTypeface(mMedium)
        mExhibition = findViewById<RadioButton>(R.id.radioStall)
        mExhibition!!.setTypeface(mMedium)
    }

    private fun spinnerDummyData() {


        mBlockList.clear()
        mBlockList.add("Select Block")
        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this@StallActivity,
            R.layout.simple_spinner_dropdown_item,
            mBlockList as List<Any?>
        )
        mBlockSpinner!!.setAdapter(arrayAdapter)
        mSpinnerList.clear()
        mSpinnerList.add("Select Stall")
        val arrayAdapter1: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this@StallActivity,
            R.layout.simple_spinner_dropdown_item,
            mSpinnerList as List<Any?>
        )
        mStallSpinner!!.setAdapter(arrayAdapter1)

        mSeatType = "2"
        //
        getconstant()
    }

    private fun init() {
        show_bttn = findViewById<Button>(R.id.img_bttn)
        show_bttn!!.visibility = View.GONE
        mSubmit = findViewById<Button>(R.id.submit_bttn)
        mAddStall = findViewById<Button>(R.id.addstall_bttn)
        mCountry_group = findViewById<RadioGroup>(R.id.radioGroupStall)
        mStallGroup = findViewById<RadioGroup>(R.id.radioGroup)
        mStallText = findViewById<TextView>(R.id.stall_variable_txt)
        mBlockSpinner = findViewById<Spinner>(R.id.block_spinner)
        mStallSpinner = findViewById<Spinner>(R.id.stall_spinner)
        mSelectCard = findViewById<CardView>(R.id.selectCard)
        mStallRecycler = findViewById<RecyclerView>(R.id.stall_recycler)
        mBack = findViewById<ImageView>(R.id.backicon)
        mClose = findViewById<ImageView>(R.id.closeicon)
        mTotalTxt = findViewById<TextView>(R.id.total_stall_txt)
        mParent = findViewById<RelativeLayout>(R.id.stall_parent_layout)
        mCurrency = findViewById<ImageView>(R.id.currency_img)

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
                        StallActivity.TAG,
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
//                    mEdit!!.putString("indian", indian)
//                    mEdit!!.putString("overseas", overseas).commit()
                    Log.d(
                        StallActivity.TAG,
                        "onGetResponse: " + "indian" + indian + "overseas" + overseas
                    )
                    setBlockData(mRegType, mSeatType)


                } else {
                    Toast.makeText(StallActivity@ this, message, Toast.LENGTH_LONG).show()
                    //  LoadingScreen.hideLoading()
                    //  callotplatest(edtmobno.text.toString())
                }


            } catch (e: JSONException) {
                println("Exception caught");
            }


        }.execute(
            "POST",
            ConstApi.LIVE_URL + "api.php?requestparm=indianseafood",
            jbookingbarcode.toString().trim()
        )


    }


    override fun onClick(v: View) {
        /*if (v == show_bttn) {
            final Dialog dialog = new Dialog(StallActivity.this);
            dialog.setContentView(R.layout.img_show_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            ImageView close = (ImageView) dialog.findViewById(R.id.close_img);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }*/
        if (v === mSubmit) {
            if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
                val intent = Intent(this@StallActivity, StallFormActivity::class.java)
                intent.putExtra("DataList", mDataList as Serializable)
                intent.putExtra("total_stall_price", total_stall_price)
                Log.d(TAG, "onTotalClick: $total_stall_price")
                intent.putExtra("StallList", mStallNameList as Serializable)
                intent.putExtra("RegType", mRegType)
                intent.putExtra("SeatType", mSeatType)
                intent.putExtra("currencytype", currencytype)
                startActivity(intent)
                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
                Log.d(TAG, "onClick: listSize" + mStallList.size)
            } else {
                Toast.makeText(
                    this@StallActivity,
                    "Check your Internet Connection & try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (v === mBack) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        if (v === mClose) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        if (v === mAddStall) {



            if (mStallSpinner!!.getSelectedItem().toString()
                    .equals("Select Stall", ignoreCase = true)
            ) {
                val snackbar: Snackbar =
                    Snackbar.make(mParent!!, "Please select Stall", Snackbar.LENGTH_SHORT)
                val view: View = snackbar.getView()
                view.setBackgroundColor(Color.RED)
                snackbar.show()
            }
            else {
                mSelectCard!!.setVisibility(View.VISIBLE)
                mSubmit!!.visibility = View.VISIBLE
                if (mDataList.size > 0) {


                    val isduplicatestall = chkduplicatestall(mStallNameSelected)
                    if (isduplicatestall) {
                        val snackbar: Snackbar =
                            Snackbar.make(mParent!!, "Stall Already Added", Snackbar.LENGTH_SHORT)
                        val view: View = snackbar.getView()
                        view.setBackgroundColor(Color.RED)
                        snackbar.show()
                    } else {
                        Log.d(TAG, "onDataClick: ")
                        val allResult = AllResult()
                        allResult.id = mStallList[mStall_position - 1].id
                        allResult.setmstallName(mStallList[mStall_position - 1].getmstallName())
                        allResult.setmstallLength(mStallList[mStall_position - 1].getmstallLength())
                        allResult.setmstallWidth(mStallList[mStall_position - 1].getmstallWidth())
                        allResult.setmnonmStallprice(
                            //  mStallList[mStall_position - 1].getmnonmStallprice().toString() + ".0"
                            mStallList[mStall_position - 1].getmnonmStallprice().toString()
                        )
                        // for nonmember
                        allResult.setmnonmGstprice(mStallList[mStall_position - 1].getmnonmGstprice())
                        allResult.setmnonmTotalprice(mStallList[mStall_position - 1].getmnonmTotalprice())
                        //  allResult.member_price = mStallList[mStall_position - 1].member_price
                        allResult.mnonmStallprice = mStallList[mStall_position - 1].mnonmStallprice
                        allResult.mnonmGstprice = mStallList[mStall_position - 1].mnonmGstprice
                        allResult.mnonmTotalprice = mStallList[mStall_position - 1].mnonmTotalprice

                        // for member
                        allResult.setmmemGstprice(mStallList[mStall_position -1].getmmemGstprice())
                        allResult.setmmemTotalprice(mStallList[mStall_position -1].getmmemTotalprice())

                        allResult.mmemStallprice = mStallList[mStall_position -1].mmemStallprice
                        allResult.mmemGstprice = mStallList[mStall_position -1].mmemGstprice
                        allResult.mmemTotalprice = mStallList[mStall_position -1].mmemTotalprice


                        allResult.setmstallCategory(mStallList[mStall_position - 1].getmstallCategory())
                        allResult.setmtotalCorner(mStallList[mStall_position - 1].getmtotalCorner())
                        Log.d(
                            TAG,
                            "onMemberClick: " + "price " + mStallList[mStall_position - 1].mnonmStallprice +
                                    "gst " + mStallList[mStall_position - 1].mnonmGstprice +
                                    "total " + mStallList[mStall_position - 1].mnonmTotalprice
                        )


                        mDataList.add(allResult)
                        mStallNameList.add(allResult.getmstallName().toString())
                        val totalcornerprice =
                            ConstApi.mcorner_price.toDouble() * allResult.getmtotalCorner()!!
                                .toDouble()

                        val cornergstpercentage = (ConstApi.gstpercentage / 100) * totalcornerprice
                        val totalStallPrice = totalcornerprice + cornergstpercentage


                        total_stall_price =
                            total_stall_price + totalStallPrice + mStallList[mStall_position - 1].getmnonmTotalprice()!!
                                .toDouble()
                        Log.d(TAG, "onTotalClick: $total_stall_price")
                        val total_price = formatter!!.format(total_stall_price)
                        mTotalTxt!!.setText(total_stall_price.toString() + "")
                        mStallRecycler!!.setLayoutManager(LinearLayoutManager(this@StallActivity))
                        mAdapter = StallAdapter(
                            this@StallActivity,
                            mDataList,
                            1,
                            currencytype,
                            this@StallActivity
                        )
                        mStallRecycler!!.setAdapter(mAdapter)
                        val snackbar: Snackbar = Snackbar.make(
                            mParent!!,
                            "Stall Added Successfully",
                            Snackbar.LENGTH_SHORT
                        )
                        val view: View = snackbar.getView()
                        view.setBackgroundColor(Color.BLUE)
                        snackbar.show()
                    }
                } else {
                    val allResult = AllResult()
                    allResult.id = mStallList[mStall_position - 1].id
                    allResult.setmstallName(mStallList[mStall_position - 1].getmstallName())
                    allResult.setmstallLength(mStallList[mStall_position - 1].getmstallLength())
                    allResult.setmstallWidth(mStallList[mStall_position - 1].getmstallWidth())
                    allResult.setmstallPrice(
                        //mStallList[mStall_position - 1].getmstallPrice().toString() + ".0"
                        mStallList[mStall_position - 1].getmnonmStallprice().toString()
                    )

                    allResult.setmnonmStallprice(mStallList[mStall_position - 1].getmnonmStallprice())
                    allResult.setmnonmTotalprice(mStallList[mStall_position - 1].getmnonmTotalprice())
                    allResult.mnonmStallprice = mStallList[mStall_position - 1].mnonmStallprice
                    allResult.mnonmGstprice = mStallList[mStall_position - 1].mnonmGstprice
                    allResult.mnonmTotalprice = mStallList[mStall_position - 1].mnonmTotalprice

                    // for member
                    allResult.setmmemGstprice(mStallList[mStall_position -1].getmmemGstprice())
                    allResult.setmmemTotalprice(mStallList[mStall_position -1].getmmemTotalprice())

                    allResult.mmemStallprice = mStallList[mStall_position -1].mmemStallprice
                    allResult.mmemGstprice = mStallList[mStall_position -1].mmemGstprice
                    allResult.mmemTotalprice = mStallList[mStall_position -1].mmemTotalprice



                    allResult.setmstallCategory(mStallList[mStall_position - 1].getmstallCategory())
                    allResult.setmtotalCorner(mStallList[mStall_position - 1].getmtotalCorner())
                    Log.d(
                        TAG,
                        "onMemberClick: " + "price " + mStallList[mStall_position - 1].getmnonmStallprice() +
                                "gst " + mStallList[mStall_position - 1].getmnonmGstprice() +
                                "total " + mStallList[mStall_position - 1].getmnonmTotalprice()
                    )
                    mDataList.add(allResult)
                    mStallNameList.add(allResult.getmstallName()!!)
                    mStallRecycler!!.layoutManager = LinearLayoutManager(this@StallActivity)
                    mAdapter = StallAdapter(
                        this@StallActivity,
                        mDataList,
                        1,
                        currencytype,
                        this@StallActivity
                    )
                    mStallRecycler!!.setAdapter(mAdapter)
                    val totalcornerprice =
                        ConstApi.mcorner_price.toDouble() * allResult.getmtotalCorner()!!.toDouble()

                    val cornergstpercentage = (ConstApi.gstpercentage / 100) * totalcornerprice
                    val totalStallPrice = totalcornerprice + cornergstpercentage
                    total_stall_price =
                        total_stall_price + totalStallPrice + mStallList[mStall_position - 1].getmnonmTotalprice()!!
                            .toDouble()
                    Log.d(TAG, "onTotalClick: $total_stall_price")


                    val total_price = formatter!!.format(total_stall_price)
                    mTotalTxt!!.setText(total_stall_price.toString() + "")
                    val snackbar: Snackbar =
                        Snackbar.make(mParent!!, "Stall Added Successfully", Snackbar.LENGTH_SHORT)
                    val view: View = snackbar.getView()
                    view.setBackgroundColor(Color.BLUE)
                    snackbar.show()
                }
            }
        }
    }

    private fun setBlockData(country: String, stall_type: String) {
        mBlockList.clear()
        mBlockList.add("Select Block")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retroInterface: RetroInterface = retrofit.create(RetroInterface::class.java)
        val jsonObject = JsonObject()
        jsonObject.addProperty("methodname", "getstallcategory")
        jsonObject.addProperty("stallregtype", country)
        jsonObject.addProperty("stallseattype", stall_type)

        Log.d("userstatus", jsonObject.toString())

        HttpTask {
            ""
            if (it == null) {
                Log.d("connection error", "Some thing Went Wrong")

                return@HttpTask
            }
            try {


                val stalljsonobj = JSONObject(it.toString())
                Log.d("respond", stalljsonobj.toString())

                val status = stalljsonobj.getString("status");
                val message = stalljsonobj.getString("msg");

                if (status == "200") {

                    val jcornerobj = stalljsonobj.getJSONObject("resultstatus")

                    val corner_price = jcornerobj.getString("corner_price")

                    ConstApi.mcorner_price = corner_price.toDouble()


                    val jresultobj = stalljsonobj.getJSONArray("result")


                    if (message.contains("ok")) {
                        //  Log.d(TAG, "onResponse: " + response.body().getmResult().get(0).getmStallCategory());
                        for (i in 0 until jresultobj.length()) {
                            val stall_category: String? =
                                //   response.body().getmResult()?.get(i)!!.getmStallCategory()
                                jresultobj.getJSONObject(i).getString("stall_category")

                            if (stall_category != null) {
                                mBlockList.add(stall_category)
                            }
                        }
                        Log.d(TAG, "onArrResponse: " + mBlockList.size)

                        val arrayAdapter: ArrayAdapter<*> = object : ArrayAdapter<Any?>(
                            this@StallActivity,
                            R.layout.simple_spinner_dropdown_item,
                            mBlockList as List<Any?>
                        ) {
                            override fun isEnabled(position: Int): Boolean {
                                return if (position == 0) {
                                    false
                                } else {
                                    true
                                }
                            }

                            override fun getView(
                                position: Int, convertView: View?,
                                parent: ViewGroup
                            ): View {
                                val view: View = super.getView(position, convertView, parent)
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
                        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
                        mBlockSpinner!!.setAdapter(arrayAdapter)
                        mBlockSpinner!!.setOnItemSelectedListener(object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                // Log.d(TAG, "onModelSelect: "+ response.body().getmResult().get(position).getmStallCategory());
                                if (position > 0) {
                                    val cat_type = mBlockList[position]
                                    Log.d(TAG, "onItemSelected: $cat_type")
//                                kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                                    .setLabel("Please wait")
//                                    .setDetailsLabel("Fetching data...")
//                                    .setCancellable(true)
//                                    .setAnimationSpeed(2)
//                                    .setDimAmount(0.5f)
//                                    .show()
                                    if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
                                        setData(country, stall_type, cat_type)
                                    } else {
                                        Toast.makeText(
                                            this@StallActivity,
                                            "Enable Your Internet Connection",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        })
                    }


                } else {
                    Toast.makeText(StallActivity@ this, message, Toast.LENGTH_LONG).show()

                }


            } catch (e: JSONException) {
                println("Exception caught");
            }


        }.execute(
            "POST",
            ConstApi.LIVE_URL + "api.php?requestparm=indianseafood",
            jsonObject.toString().trim()
        )


/*//        val call: Call<StallResult>? = retroInterface.getAllData(jsonObject)
//        call!!.enqueue(object : Callback<StallResult> {
//            override fun onResponse(call: Call<StallResult>, response: Response<StallResult>) {
//              //  kProgressHUD.dismiss()
//                if(response.body()!=null)
//                {
//                Log.d(
//                    TAG,
//                    "onResponse: " + response.body().getmStatus()
//                        .toString() + "Mesaage" + response.body().getmMsg().toString()
//                )
//                val message: String = response.body().getmMsg().toString()
//                if (message.contains("ok")) {
//                    //  Log.d(TAG, "onResponse: " + response.body().getmResult().get(0).getmStallCategory());
//                    for (i in 0 until response.body().getmResult()!!.size) {
//                        val stall_category: String? =
//                            response.body().getmResult()?.get(i)!!.getmStallCategory()
//                        if (stall_category != null) {
//                            mBlockList.add(stall_category)
//                        }
//                    }
//                    Log.d(TAG, "onArrResponse: " + mBlockList.size)
//                    val arrayAdapter: ArrayAdapter<*> = object : ArrayAdapter<Any?>(
//                        this@StallActivity,
//                        R.layout.simple_spinner_dropdown_item,
//                        mBlockList as List<Any?>
//                    ) {
//                        override fun isEnabled(position: Int): Boolean {
//                            return if (position == 0) {
//                                false
//                            } else {
//                                true
//                            }
//                        }
//
//                        override fun getDropDownView(
//                            position: Int, convertView: View,
//                            parent: ViewGroup
//                        ): View {
//                            val view: View = super.getDropDownView(position, convertView, parent)
//                            val tv: TextView = view as TextView
//                            if (position == 0) {
//                                // Set the hint text color gray
//                                tv.setTextColor(Color.GRAY)
//                            } else {
//                                tv.setTextColor(Color.BLACK)
//                            }
//                            return view
//                        }
//                    }
//                    arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
//                    mBlockSpinner!!.setAdapter(arrayAdapter)
//                    mBlockSpinner!!.setOnItemSelectedListener(object :
//                        AdapterView.OnItemSelectedListener {
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View,
//                            position: Int,
//                            id: Long
//                        ) {
//                            // Log.d(TAG, "onModelSelect: "+ response.body().getmResult().get(position).getmStallCategory());
//                            if (position > 0) {
//                                val cat_type = mBlockList[position]
//                                Log.d(TAG, "onItemSelected: $cat_type")
////                                kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
////                                    .setLabel("Please wait")
////                                    .setDetailsLabel("Fetching data...")
////                                    .setCancellable(true)
////                                    .setAnimationSpeed(2)
////                                    .setDimAmount(0.5f)
////                                    .show()
//                                if (NetworkConnect.isNetworkConnected(this@StallActivity)) {
//                                    setData(country, stall_type, cat_type)
//                                } else {
//                                    Toast.makeText(
//                                        this@StallActivity,
//                                        "Enable Your Internet Connection",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//
//                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                    })
//                }
//            }
//            }
//            override fun onFailure(call: Call<StallResult>, t: Throwable) {
//              //  kProgressHUD.dismiss()
//                Log.d(TAG, "onFailure: $call")
//            }
//        })*/
    }


    private fun setData(country: String, stall_type: String, stallcattype: String) {
        //  kProgressHUD.dismiss()
        mSpinnerList.clear()
        Log.d(TAG, "setData: $stallcattype")
        mSpinnerList.add("Select Stall")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retroInterface: RetroInterface = retrofit.create(RetroInterface::class.java)
        val jsonObject = JsonObject()
        jsonObject.addProperty("methodname", "stalldetail")
        jsonObject.addProperty("stallregtype", country)
        jsonObject.addProperty("stallseattype", stall_type)
        jsonObject.addProperty("stallcattype", stallcattype)
        Log.d("stalldetails", jsonObject.toString())


        val call: Call<StallResult>? = retroInterface.getAllData(jsonObject)
        call!!.enqueue(object : Callback<StallResult> {
            override fun onResponse(call: Call<StallResult>, response: Response<StallResult>) {


                mStallList = response.body().getmResult()!!

                for (i in 0 until response.body().getmResult()!!.size) {


                        mSpinnerList.add(response.body().getmResult()!!.get(i).getmstallName()!!)
                        Log.d(
                            TAG,
                            "onNewResponse: " + response.body().getmResult()!!.get(i).toString()
                        )

                }


                val arrayAdapter: ArrayAdapter<*> = object : ArrayAdapter<Any?>(
                    this@StallActivity,
                    R.layout.simple_spinner_dropdown_item,
                    mSpinnerList as List<Any?>
                ) {
                    override fun isEnabled(position: Int): Boolean {
                        return if (position == 0) {
                            false
                        } else {
                            true
                        }
                    }

                    override fun getView(
                        position: Int, convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view: View = super.getView(position, convertView, parent)
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
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
                mStallSpinner!!.setAdapter(arrayAdapter)
                mStallSpinner!!.setOnItemSelectedListener(object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        //  Log.d(TAG, "onSel: " + mStallList.get(position).getmStallName());
                        if (position > 0) {
                            mStall_position = position
                            mStallNameSelected = mStallList[position - 1].getmstallName()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                })
            }

            override fun onFailure(call: Call<StallResult>, t: Throwable) {

                Log.d("onfailure", t.message.toString())
            }
        })
    }

    override fun deleteData(id: Int, position: Int, name: String) {

        //  Log.d("Stall_non_mem_price",mStallList.get(position).getmTotalPrice().toString());
        Log.d("Stall_non_mem_price", mDataList[position].getmnonmTotalprice().toString())
        Log.d("total_stall_price befor", total_stall_price.toString())
        if (mDataList.size == 1) {
            total_stall_price = 0.0
            mDataList.removeAt(0)
            mStallNameList.removeAt(0)
            mAdapter!!.notifyDataSetChanged()
            mSelectCard!!.setVisibility(View.GONE)
        } else {
            //total_stall_price = total_stall_price - Integer.parseInt(mStallList.get(position).getmTotalPrice());
            total_stall_price =
                total_stall_price - mDataList[position].getmnonmTotalprice()!!.toDouble()
            mDataList.removeAt(position)
            mStallNameList.removeAt(position)
            mAdapter!!.notifyDataSetChanged()
            Log.d("total_stall_price after", total_stall_price.toString())
            //mEdit.clear().commit();
            Log.d(TAG, "deleteData: $position")
        }
        mTotalTxt!!.setText(total_stall_price.toString() + "")
        val snackbar: Snackbar =
            Snackbar.make(mParent!!, "Stall Removed Successfully", Snackbar.LENGTH_SHORT)
        val view: View = snackbar.getView()
        view.setBackgroundColor(Color.BLUE)
        snackbar.show()
    }

    fun chkduplicatestall(stallname: String?): Boolean {
        var isduplicate = false
        for (ii in mDataList.indices) {
            if (mDataList[ii].getmstallName().equals(stallname, ignoreCase = true)) {
                isduplicate = true
                break
            } else {
                isduplicate = false
            }
        }
        return isduplicate
    }

    companion object {
        private val TAG = StallActivity::class.java.simpleName
    }
}


