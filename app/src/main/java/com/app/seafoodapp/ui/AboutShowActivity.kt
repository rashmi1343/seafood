package com.app.seafoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.app.seafoodapp.R

class AboutShowActivity : AppCompatActivity() {
    private var closeicon: ImageView? = null
    private var backicon: ImageView? = null
    private var mHeadingText: TextView? = null
    private var mContentText: TextView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    var mSemiBold: Typeface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityaboutshow)
        backicon = findViewById(R.id.backicon)
        closeicon = findViewById(R.id.closeicon)
        mHeadingText = findViewById(R.id.text_about)
        mContentText = findViewById(R.id.content_text)
        mRegular = Typeface.createFromAsset(assets, "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(assets, "montserrat/Montserrat-Medium.otf")
        mSemiBold = Typeface.createFromAsset(assets, "montserrat/Montserrat-SemiBold.otf")
        mHeadingText!!.setTypeface(mMedium)
        mContentText!!.setTypeface(mRegular)
        backicon!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })
        closeicon!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })
    }
}