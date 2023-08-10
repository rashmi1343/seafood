package com.app.seafoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import com.app.seafoodapp.R

class TermsConditionActivity : AppCompatActivity() {
    private var tvtermsandconditions: TextView? = null
    private var tvconditionone: TextView? = null
    private var tvconditiontwo: TextView? = null
    private var tvconditionthree: TextView? = null
    private var tvconditionfour: TextView? = null
    private var tvconditionfive: TextView? = null
    private var tvconditionsix: TextView? = null
    private var tvconditionseven: TextView? = null
    private var tvconditioneight: TextView? = null
    private var tvconditionnine: TextView? = null
    private var tvconditionten: TextView? = null

    private var tvcondition11: TextView? = null
    private var tvcondition12: TextView? = null
    private var tvcondition13: TextView? = null
    private var tvcondition14: TextView? = null
    private var tvcondition15: TextView? = null
    private var tvcondition16: TextView? = null
    private var tvcondition17: TextView? = null
    private var tvcondition18: TextView? = null
    private var tvcondition19: TextView? = null
    private var tvcondition20: TextView? = null
    private var tvcondition21: TextView? = null
    private var tvcondition22: TextView? = null
    private var tvcondition23: TextView? = null
    private var tvcondition24: TextView? = null
    private var tvcondition25: TextView? = null

    private var closeicon: ImageView? = null
    private var backicon: ImageView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    var mSemiBold: Typeface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_condition)
        backicon = findViewById(R.id.backicon)
        closeicon = findViewById(R.id.closeicon)
        backicon!!.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        closeicon!!.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        mRegular = Typeface.createFromAsset(assets, "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(assets, "montserrat/Montserrat-Medium.otf")
        mSemiBold = Typeface.createFromAsset(assets, "montserrat/Montserrat-SemiBold.otf")

        tvtermsandconditions = findViewById(R.id.tvtermsandconditions)
        tvtermsandconditions!!.typeface = mMedium
        tvconditionone = findViewById(R.id.tvconditionone)
        tvconditionone!!.typeface = mRegular
        tvconditiontwo = findViewById(R.id.tvconditiontwo)
        tvconditiontwo!!.typeface = mRegular
        tvconditionthree = findViewById(R.id.tvconditionthree)
        tvconditionthree!!.typeface = mRegular
        tvconditionfour = findViewById(R.id.tvconditionfour)
        tvconditionfour!!.typeface = mRegular
        tvconditionfive = findViewById(R.id.tvconditionfive)
        tvconditionfive!!.typeface = mRegular
        tvconditionsix = findViewById(R.id.tvconditionsix)
        tvconditionsix!!.typeface = mRegular
        tvconditionseven = findViewById(R.id.tvconditionseven)
        tvconditionseven!!.typeface = mRegular
        tvconditioneight = findViewById(R.id.tvconditioneight)
        tvconditioneight!!.typeface = mRegular
        tvconditionnine = findViewById(R.id.tvconditionnine)
        tvconditionnine!!.typeface = mRegular
        tvconditionten = findViewById(R.id.tvconditionten)
        tvconditionten!!.typeface = mRegular

        tvcondition11 = findViewById(R.id.tvcondition11)
        tvcondition11!!.typeface = mRegular
        tvcondition12 = findViewById(R.id.tvcondition12)
        tvcondition12!!.typeface = mRegular
        tvcondition13 = findViewById(R.id.tvcondition13)
        tvcondition13!!.typeface = mRegular
        tvcondition14 = findViewById(R.id.tvcondition14)
        tvcondition14!!.typeface = mRegular
        tvcondition15 = findViewById(R.id.tvcondition15)
        tvcondition15!!.typeface = mRegular
        tvcondition16 = findViewById(R.id.tvcondition16)
        tvcondition16!!.typeface = mRegular
        tvcondition17 = findViewById(R.id.tvcondition17)
        tvcondition17!!.typeface = mRegular
        tvcondition18 = findViewById(R.id.tvcondition18)
        tvcondition18!!.typeface = mRegular
        tvcondition19 = findViewById(R.id.tvcondition19)
        tvcondition19!!.typeface = mRegular
        tvcondition20 = findViewById(R.id.tvcondition20)
        tvcondition20!!.typeface = mRegular

        tvcondition21 = findViewById(R.id.tvcondition21)
        tvcondition21!!.typeface = mRegular
        tvcondition22 = findViewById(R.id.tvcondition22)
        tvcondition22!!.typeface = mRegular
        tvcondition23 = findViewById(R.id.tvcondition23)
        tvcondition23!!.typeface = mRegular
        tvcondition24 = findViewById(R.id.tvcondition24)
        tvcondition24!!.typeface = mRegular
        tvcondition25 = findViewById(R.id.tvcondition25)
        tvcondition25!!.typeface = mRegular
    }
}
