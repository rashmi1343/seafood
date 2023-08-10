package com.app.seafoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatButton
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.app.seafoodapp.R

class DashBoardActivity : AppCompatActivity() {
    private var mDelicate: CardView? = null
    private var cdaboutshow: CardView? = null
    private var cdorganiser: CardView? = null
    private var mStall: CardView? = null
    private var cdlocateus: CardView? = null
    private var cdcontactus: CardView? = null
    private var mTraining: CardView? = null
    private var mFeedback: CardView? = null
    private var cdleads: CardView? = null
    private var mProfile: CardView? = null
    private var mDeligateTxt: TextView? = null
    private val mDregistration: TextView? = null
    private var mStallTxt: TextView? = null
    private val mSRegistration: TextView? = null
    private var mAbt_txt: TextView? = null
    private var mOrganiserTxt: TextView? = null
    private val mAbtSow_Text: TextView? = null
    private var mVenue_txt: TextView? = null
    private var mContactTxt: TextView? = null
    private var mMeetingTxt: TextView? = null
    private var mTechnicalTxt: TextView? = null
    private var mFeedBackTxt: TextView? = null
    private val technical_txt: TextView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    var mSemiBold: Typeface? = null
    var mMuliBold: Typeface? = null
    private var mChangeProfile: AppCompatButton? = null
    private var imguserprofile: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        mRegular = Typeface.createFromAsset(assets, "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(assets, "montserrat/Montserrat-Medium.otf")
        mSemiBold = Typeface.createFromAsset(assets, "montserrat/Montserrat-SemiBold.otf")
        mMuliBold = Typeface.createFromAsset(assets, "montserrat/Montserrat-Bold.otf")
        change_txt()
        mProfile!!.setOnClickListener {
            val myIntent = Intent(this@DashBoardActivity, LoginActivity::class.java)
            myIntent.putExtra("navigationcode", "2")
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        }
        mDelicate = findViewById(R.id.delecate_card)
        mDelicate!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mDelicate!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@DashBoardActivity, MainActivity::class.java))
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        cdaboutshow = findViewById(R.id.cdaboutshow)
        cdaboutshow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        cdaboutshow!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@DashBoardActivity, AboutShowActivity::class.java))
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        cdorganiser = findViewById(R.id.cdorganiser)
        cdorganiser!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        cdorganiser!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@DashBoardActivity, activityorganized::class.java))
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        mStall = findViewById(R.id.card_stall)
        mStall!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mStall!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@DashBoardActivity, StallActivity::class.java))
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })

        //
        cdlocateus = findViewById(R.id.cdlocateus)
        cdlocateus!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        cdlocateus!!.setOnClickListener(View.OnClickListener {
            //   startActivity(new Intent(DashboardActivity.this,StallActivity.class));
            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/d/viewer?ie=UTF8&hl=en&msa=0&ll=9.987342000000002%2C76.26468699999998&spn=34.668997%2C78.662109&z=17&mid=10Dg861xJNy18dTvB6dHk9fp8dRQ")); startActivity(intent);    val intent = Intent(
            val intent = Intent(
                Intent.ACTION_VIEW,
                // Uri.parse("google.navigation:q=Grand Hyatt Kochi Bolgatty")           // for 2020
                Uri.parse("google.navigation:q=Biswa Bangla Mela Prangan")
                // for 2023
            )
            startActivity(intent)

            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            //         Uri.parse("https://maps.google.com/maps?daddr=20.5666,45.345&#8221"));    //  startActivity(intent);
        })
//        cdlocateus!!.setOnClickListener(View.OnClickListener {
//            //   startActivity(new Intent(DashboardActivity.this,StallActivity.class));
//            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/d/viewer?ie=UTF8&hl=en&msa=0&ll=9.987342000000002%2C76.26468699999998&spn=34.668997%2C78.662109&z=17&mid=10Dg861xJNy18dTvB6dHk9fp8dRQ")); startActivity(intent);
//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                //  Uri.parse("google.navigation:q=Grand Hyatt Kochi Bolgatty")
//            )
//            startActivity(intent)
//
//            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//            //         Uri.parse("https://maps.google.com/maps?daddr=20.5666,45.345&#8221"));
//            //  startActivity(intent);
//        })
        cdcontactus = findViewById(R.id.cdcontactus)
        cdcontactus!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        cdcontactus!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@DashBoardActivity, ActivityContactus::class.java))
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        mFeedback = findViewById(R.id.feedback_card)
        mFeedback!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mFeedback!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@DashBoardActivity, FeedbackActivity::class.java))
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        mTraining = findViewById(R.id.training_card)
        mTraining!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mTraining!!.setOnClickListener(View.OnClickListener { // startActivity(new Intent(DashBoardActivity.this, TrainingActivity.class));
            // overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
            if (util.userid.length == 0) {
                val myIntent = Intent(this@DashBoardActivity, LoginActivity::class.java)
                myIntent.putExtra("navigationcode", "3")
                startActivity(myIntent)
                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
            } else {
                val myIntent = Intent(this@DashBoardActivity, TrainingActivity::class.java)
                myIntent.putExtra("navigationcode", "1")
                startActivity(myIntent)
                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
            }
//            val myIntent = Intent(this@DashBoardActivity, TrainingActivity::class.java)
//            myIntent.putExtra("navigationcode", "1")
//            startActivity(myIntent)
        })
        imguserprofile = findViewById(R.id.imguserprofile)
        imguserprofile!!.setOnClickListener(View.OnClickListener {
            //  startActivity(Intent(this@DashBoardActivity, LoginActivity::class.java))
            //  overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
            val myIntent = Intent(this@DashBoardActivity, LoginActivity::class.java)
            myIntent.putExtra("navigationcode", "2")
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
        cdleads = findViewById(R.id.cdleads)
        cdleads!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        cdleads!!.setOnClickListener(View.OnClickListener { // startActivity(new Intent(DashBoardActivity.this, FeedbackActivity.class));
            //  overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);

            // Second time to LeadAquisition
            /*   */

            // first time to activiate account then gos to
            if (util.userid.length == 0) {
                val myIntent = Intent(this@DashBoardActivity, LoginActivity::class.java)
                myIntent.putExtra("navigationcode", "2")
                startActivity(myIntent)
                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
            } else {
                val myIntent = Intent(this@DashBoardActivity, LeadAquistionActivity::class.java)
                myIntent.putExtra("navigationcode", "1")
                startActivity(myIntent)
                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
            }
//            val myIntent = Intent(this@DashBoardActivity, LeadAquistionActivity::class.java)
//            myIntent.putExtra("navigationcode", "1")
//            startActivity(myIntent)

        })
    }

    private fun change_txt() {
        mChangeProfile = findViewById(R.id.chng_prof_bttn)
        mChangeProfile!!.setTypeface(mMuliBold)
        mProfile = findViewById(R.id.profile_card)
        mDeligateTxt = findViewById(R.id.delegate_txt)
        mDeligateTxt!!.typeface = mMuliBold
        // mDregistration = findViewById(R.id.delegate_txt);
//        mDregistration.setTypeface(mMuliBold);
        mStallTxt = findViewById(R.id.stall_text)
        mStallTxt!!.typeface = mMuliBold
        //   mSRegistration = findViewById(R.id.stall_reg_text);
        //   mSRegistration.setTypeface(mMuliBold);
        //  mAbtSow_Text = findViewById(R.id.show_text);
        //   mAbtSow_Text.setTypeface(mMuliBold);
        mAbt_txt = findViewById(R.id.about_text)
        mAbt_txt!!.typeface = mMuliBold
        mOrganiserTxt = findViewById(R.id.organiser_txt)
        mOrganiserTxt!!.typeface = mMuliBold
        mVenue_txt = findViewById(R.id.show_venue_txt)
        mVenue_txt!!.typeface = mMuliBold
        mContactTxt = findViewById(R.id.contact_us_txt)
        mContactTxt!!.typeface = mMuliBold
        mMeetingTxt = findViewById(R.id.leads_txt)
        mMeetingTxt!!.typeface = mMuliBold
        mTechnicalTxt = findViewById(R.id.tech_text)
        mTechnicalTxt!!.typeface = mMuliBold
        mFeedBackTxt = findViewById(R.id.feedback_txt)
        mFeedBackTxt!!.typeface = mMuliBold
        // technical_txt = findViewById(R.id.session_text);
        //  technical_txt.setTypeface(mMuliBold);
    }
}