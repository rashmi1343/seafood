package com.app.seafoodapp.ui

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.app.seafoodapp.Const.ConstApi

import com.app.seafoodapp.Interface.ProfileInterface
import com.app.seafoodapp.R
import com.app.seafoodapp.model.SeminarModel
import com.app.seafoodapp.model.UserProfile
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private val mBack: ImageView? = null
    private val mClose: ImageView? = null
    private var mDisplayUser: TextView? = null
    private var mUserName: TextView? = null
    private var mDesignationText: TextView? = null
    private var mLogoutText: TextView? = null
    private var tvimgleads: TextView? = null
    private val user_img: TextView? = null
    private var cdleads: CardView? = null
    private var mLogoutCard: CardView? = null
    private var cdseminar: CardView? = null
    private var cdfeedback: CardView? = null
    private var mRegular: Typeface? = null
    private var mMedium: Typeface? = null
    private var ftfontawesome: Typeface? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        //  mBack.setOnClickListener(this);
        //  mClose.setOnClickListener(this);
        getUserResponse(this)
    }

    private fun init() {
        //  mBack = findViewById(R.id.backicon_profile);
        //  mClose = findViewById(R.id.closeicon_profile);
        mRegular = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Medium.otf")
        ftfontawesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf")
        tvimgleads = findViewById<TextView>(R.id.tvimgleads)
        tvimgleads!!.setTypeface(ftfontawesome)
        tvimgleads!!.setText("\uf0c0")
        mDisplayUser = findViewById<TextView>(R.id.display_user_txt)
        mDisplayUser!!.setTypeface(mMedium)
        mUserName = findViewById<TextView>(R.id.username_txt)
        mUserName!!.setTypeface(mRegular)
        mDesignationText = findViewById<TextView>(R.id.desig_text)
        mDesignationText!!.setTypeface(mMedium)
        mLogoutText = findViewById<TextView>(R.id.logout_txt)
        mLogoutText!!.setTypeface(mMedium)
        cdleads = findViewById<CardView>(R.id.cdleads)
        cdleads!!.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@ProfileActivity, LeadAquistionActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        mLogoutCard = findViewById<CardView>(R.id.logout_card)
        mLogoutCard!!.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@ProfileActivity, DashBoardActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        cdseminar = findViewById<CardView>(R.id.cdseminar)
        cdseminar!!.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@ProfileActivity, TrainingActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        cdfeedback = findViewById<CardView>(R.id.cdfeedback)
        cdfeedback!!.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@ProfileActivity, FeedbackActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
    }

    override fun onClick(v: View) {
        /*if (v==mBack)
      {
          finish();
          overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right);
      }
      else if (v==mClose)
      {
          finish();
          overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right);
      }*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
    }

    private fun getUserResponse(context: Context) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("methodname", "staffLogin")
        jsonObject.addProperty("username", "vikas")
        jsonObject.addProperty("password", "test")
        val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.LIVE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mProInterface: ProfileInterface = mRetrofit.create(ProfileInterface::class.java)
        val modelCall: Call<SeminarModel>? = mProInterface.sendMethod(jsonObject)!!
        modelCall!!.enqueue(object : Callback<SeminarModel> {
            override fun onResponse(call: Call<SeminarModel>, response: Response<SeminarModel>) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().getmSeminarMsg())
                    val message: String? = response.body().getmSeminarMsg()
                    if (message!!.contains("OK")) {
                        Log.d(TAG, "ResultResponse: " + response.body().getmResultSeminar())
                        val get_result: String? = response.body().getmResultSeminar()
                        try {
                            val jsonArray = JSONArray(get_result)
                            for (i in 0 until jsonArray.length()) {
                                val `object`: JSONObject = jsonArray.getJSONObject(i)
                                val gson = Gson()
                                val userProfile: UserProfile =
                                    gson.fromJson(`object`.toString(), UserProfile::class.java)
                                val display_user: String? = userProfile.getmDisplayUser()
                                val user_name: String? = userProfile.getmUserName()
                                mDisplayUser!!.setText(display_user)
                                mUserName!!.setText(user_name)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "onErrorResponse: " + response.errorBody())
                    }
                }
            }

            override fun onFailure(call: Call<SeminarModel>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }
        })
    }

    companion object {
        private val TAG = ProfileActivity::class.java.simpleName
    }
}