package com.app.seafoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import com.app.seafoodapp.R

class ActivityContactus : AppCompatActivity() {
    private var mHeadOffice: TextView? = null
    private var mRegionalDevisions: TextView? = null
    private var mSubHeadingText: TextView? = null
    private var mRegionalSub1: TextView? = null
    private var mRegionalSub2: TextView? = null
    private var mRegionalSub3: TextView? = null
    private var mRegionalSub4: TextView? = null
    private var mRegionalSub5: TextView? = null
    private var mRegionalSub6: TextView? = null
    private var mRegionalSub7: TextView? = null
    private var mRegionalSub8: TextView? = null

    private var mSubRegionalDevisions: TextView? = null
    private var mSubSubHeadingText: TextView? = null
    private var mSubRegionalSub1: TextView? = null
    private var mSubRegionalSub2: TextView? = null
    private var mSubRegionalSub3: TextView? = null
    private var mSubRegionalSub4: TextView? = null
    private var mSubRegionalSub5: TextView? = null
    private var mSubRegionalSub6: TextView? = null
    private var mSubRegionalSub7: TextView? = null
    private var mSubRegionalSub8: TextView? = null
    private var mSubRegionalSub9: TextView? = null

    private var mControlLaboratories: TextView? = null
    private var mcontrollabone: TextView? = null
    private var mcontrollabtwo: TextView? = null
    private var mcontrollabthree: TextView? = null
    private var mcontrollabfour: TextView? = null
    private var mcontrollabfive: TextView? = null


    private var mPromotionOffices: TextView? = null
    private var mtradeofficeone: TextView? = null
    private var mtradeofficetwo: TextView? = null
    private var mtradeofficethree: TextView? = null

    private var mMpediaSocities: TextView? = null
    private var msocitiesone: TextView? = null
    private var msocitiestwo: TextView? = null
    private var msocitiesthree: TextView? = null
    private var msocitiesfour: TextView? = null

    private var closeicon: ImageView? = null
    private var backicon: ImageView? = null
    var mRegular: Typeface? = null
    var mMedium: Typeface? = null
    var mSemiBold: Typeface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactus)
        init()
        backicon!!.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        closeicon!!.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
    }

    private fun init() {
        mRegular = Typeface.createFromAsset(assets, "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(assets, "montserrat/Montserrat-Medium.otf")
        mSemiBold = Typeface.createFromAsset(assets, "montserrat/Montserrat-SemiBold.otf")
        backicon = findViewById(R.id.backicon)
        closeicon = findViewById(R.id.closeicon)
        mHeadOffice = findViewById(R.id.head_office_txt)
        mHeadOffice!!.typeface = mMedium
        mRegionalDevisions = findViewById(R.id.regional_divisons_txt)
        mRegionalDevisions!!.typeface = mMedium
        mSubHeadingText = findViewById(R.id.tvcontactdetail)
        mSubHeadingText!!.typeface = mRegular
        mRegionalSub1 = findViewById(R.id.tvcontactdetailtwo)
        mRegionalSub1!!.typeface = mRegular
        mRegionalSub2 = findViewById(R.id.regional_two)
        mRegionalSub2!!.typeface = mRegular
        mRegionalSub3 = findViewById(R.id.regional_three)
        mRegionalSub3!!.typeface = mRegular
        mRegionalSub4 = findViewById(R.id.regional_4)
        mRegionalSub4!!.typeface = mRegular
        mRegionalSub5 = findViewById(R.id.regional_5)
        mRegionalSub5!!.typeface = mRegular
        mRegionalSub6 = findViewById(R.id.regional_6)
        mRegionalSub6!!.typeface = mRegular
        mRegionalSub7 = findViewById(R.id.regional_7)
        mRegionalSub7!!.typeface = mRegular
        mRegionalSub8 = findViewById(R.id.regional_8)
        mRegionalSub8!!.typeface = mRegular

        mSubRegionalDevisions = findViewById(R.id.sub_regional_divisions_txt)
        mSubRegionalDevisions!!.typeface = mMedium
        mSubSubHeadingText = findViewById(R.id.tvcontactdetailthree)
        mSubSubHeadingText!!.typeface = mRegular
        mSubRegionalSub1 = findViewById(R.id.sub_regional_three)
        mSubRegionalSub1!!.typeface = mRegular
        mSubRegionalSub2 = findViewById(R.id.sub_regional_four)
        mSubRegionalSub2!!.typeface = mRegular
        mSubRegionalSub3 = findViewById(R.id.sub_regional_five)
        mSubRegionalSub3!!.typeface = mRegular
        mSubRegionalSub4 = findViewById(R.id.sub_regional_six)
        mSubRegionalSub4!!.typeface = mRegular
        mSubRegionalSub5 = findViewById(R.id.sub_regional_seven)
        mSubRegionalSub5!!.typeface = mRegular
        mSubRegionalSub6 = findViewById(R.id.sub_regional_eight)
        mSubRegionalSub6!!.typeface = mRegular
        mSubRegionalSub7 = findViewById(R.id.sub_regional_nine)
        mSubRegionalSub7!!.typeface = mRegular
        mSubRegionalSub8 = findViewById(R.id.sub_regional_ten)
        mSubRegionalSub8!!.typeface = mRegular
        mSubRegionalSub9 = findViewById(R.id.sub_regional_eleven)
        mSubRegionalSub9!!.typeface = mRegular

        mControlLaboratories = findViewById(R.id.contactofficefour)
        mControlLaboratories!!.typeface = mRegular
        mcontrollabone = findViewById(R.id.controllabone)
        mcontrollabone!!.typeface = mRegular
        mcontrollabtwo = findViewById(R.id.controllabtwo)
        mcontrollabtwo!!.typeface = mRegular
        mcontrollabthree = findViewById(R.id.controllabthree)
        mcontrollabthree!!.typeface = mRegular
        mcontrollabfour = findViewById(R.id.controllabfour)
        mcontrollabfour!!.typeface = mRegular
        mcontrollabfive = findViewById(R.id.controllabfive)
        mcontrollabfive!!.typeface = mRegular


        mPromotionOffices = findViewById(R.id.contactofficefive)
        mPromotionOffices!!.typeface = mRegular
        mtradeofficeone = findViewById(R.id.tradeofficeone)
        mtradeofficeone!!.typeface = mRegular
        mtradeofficetwo = findViewById(R.id.tradeofficetwo)
        mtradeofficetwo!!.typeface = mRegular
        mtradeofficethree = findViewById(R.id.tradeofficethree)
        mtradeofficethree!!.typeface = mRegular


        mMpediaSocities = findViewById(R.id.contactofficeSix)
        mMpediaSocities!!.typeface = mRegular
        msocitiesone = findViewById(R.id.socitiesone)
        msocitiesone!!.typeface = mRegular
        msocitiestwo = findViewById(R.id.socitiestwo)
        msocitiestwo!!.typeface = mRegular
        msocitiesthree = findViewById(R.id.socitiesthree)
        msocitiesthree!!.typeface = mRegular
        msocitiesfour = findViewById(R.id.socitiesfour)
        msocitiesfour!!.typeface = mRegular


    }
}