package com.app.seafoodapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.seafoodapp.R


class FeedbackActivity : AppCompatActivity(), View.OnClickListener {
    private var mBack: ImageView? = null
    private var mClose: ImageView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    private val mDetailText: TextView? = null
    private val mEditName: EditText? = null
    private val mEditDesignation: EditText? = null
    private val mEditEmail: EditText? = null
    private val mEditMobile: EditText? = null
    private val mEditFeedback: EditText? = null
   // private val mFeedback: AppCompatButton? = null
    private var webview: WebView? = null
    private val webView: WebView? = null
    private var feedback_progress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        init()
        mBack!!.setOnClickListener(this)
        mClose!!.setOnClickListener(this)
        //  mFeedback.setOnClickListener(this);
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        /* mRegular = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Regular.otf");
        mMedium = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Medium.otf");*/
     //   feedback_progress = findViewById(R.id.feedback_progress)
        mBack = findViewById(R.id.bacfeedback)
        mClose = findViewById(R.id.closefeedback)

        webview = findViewById(R.id.webview)

        val webSettings = webview!!.getSettings()
        webSettings.javaScriptEnabled = true

        val webViewClient = WebViewClientImpl(this)
        webview!!.webViewClient = webViewClient

        webview!!.loadUrl("https://www.indianseafoodexpo.com/app-feedback/")



        /*  mDetailText = findViewById(R.id.detail_feedback_text);
        mDetailText.setTypeface(mMedium);
        mEditName = findViewById(R.id.feedback_name_edit);
        mEditName.setTypeface(mRegular);
        mEditDesignation = findViewById(R.id.feedback_desig_edit);
        mEditDesignation.setTypeface(mRegular);
        mEditEmail = findViewById(R.id.feedback_email_edit);
        mEditEmail.setTypeface(mRegular);
        mEditMobile = findViewById(R.id.feedback_edit_mobile);
        mEditMobile.setTypeface(mRegular);
        mEditFeedback = findViewById(R.id.feedback_edit);
        mEditFeedback.setTypeface(mRegular);*/
        //    mFeedback = findViewById(R.id.feedback_button);
        //   mFeedback.setTypeface(mRegular);
    }

    override fun onClick(v: View) {
        if (v === mBack) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        } else if (v === mClose) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }

        /* else if (v==mFeedback)
        {
            String name = mEditName.getText().toString().trim();
            String designation = mEditDesignation.getText().toString().trim();
            String email = mEditEmail.getText().toString().trim();
            String mobile = mEditMobile.getText().toString().trim();
            String feedback = mEditFeedback.getText().toString().trim();

            if (name.isEmpty())
            {
              mEditName.setError("Enter Name");
            }
            else if (designation.isEmpty())
            {
                mEditDesignation.setError("Enter Designation");
            }
            else if (email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                mEditEmail.setError("Enter Email");
            }
            else if (mobile.isEmpty())
            {
                mEditMobile.setError("Enter Mobile No.");
            }
            else if (feedback.isEmpty())
            {
                mEditFeedback.setError("Enter Feedback");
            }
            else
            {

            }
        }*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
    }

    internal class WebViewClientImpl(activity: Activity?) : WebViewClient() {
        private var activity: Activity? = null
        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            if (url.indexOf("https://www.indianseafoodexpo.com/app-feedback/") > -1) return false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity!!.startActivity(intent)
            return true
        }

        init {
            this.activity = activity
        }
    }

}