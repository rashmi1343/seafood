package com.app.seafoodapp.lib.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.seafoodapp.R
import com.app.seafoodapp.lib.utility.AvenuesParams
import com.app.seafoodapp.lib.utility.ServiceUtility


class InitialScreenActivity : AppCompatActivity() {
    private var accessCode: EditText? = null
    private var merchantId: EditText? = null
    private var currency: EditText? = null
    private var amount: EditText? = null
    private var orderId: EditText? = null
    private var rsaKeyUrl: EditText? = null
    private var redirectUrl: EditText? = null
    private var cancelUrl: EditText? = null
    private fun init() {
        accessCode = findViewById<View>(R.id.accessCode) as EditText?
        merchantId = findViewById<View>(R.id.merchantId) as EditText?
        orderId = findViewById<View>(R.id.orderId) as EditText?
        currency = findViewById<View>(R.id.currency) as EditText?
        amount = findViewById<View>(R.id.amount) as EditText?
        rsaKeyUrl = findViewById<View>(R.id.rsaUrl) as EditText?
        redirectUrl = findViewById<View>(R.id.redirectUrl) as EditText?
        cancelUrl = findViewById<View>(R.id.cancelUrl) as EditText?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_screen)
        init()
    }

    fun onClick(view: View?) {
        //Mandatory parameters. Other parameters can be added if required.
        val vAccessCode: String = ServiceUtility.chkNull(accessCode!!.getText()).toString().trim()
        val vMerchantId: String = ServiceUtility.chkNull(merchantId!!.getText()).toString().trim()
        val vCurrency: String = ServiceUtility.chkNull(currency!!.getText()).toString().trim()
        val vAmount: String = ServiceUtility.chkNull(amount!!.getText()).toString().trim()
        if (vAccessCode != "" && vMerchantId != "" && vCurrency != "" && vAmount != "") {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(
                AvenuesParams.ACCESS_CODE,
                ServiceUtility.chkNull(accessCode!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.MERCHANT_ID,
                ServiceUtility.chkNull(merchantId!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.ORDER_ID,
                ServiceUtility.chkNull(orderId!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.CURRENCY,
                ServiceUtility.chkNull(currency!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.AMOUNT,
                ServiceUtility.chkNull(amount!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.REDIRECT_URL,
                ServiceUtility.chkNull(redirectUrl!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.CANCEL_URL,
                ServiceUtility.chkNull(cancelUrl!!.getText()).toString().trim()
            )
            intent.putExtra(
                AvenuesParams.RSA_KEY_URL,
                ServiceUtility.chkNull(rsaKeyUrl!!.getText()).toString().trim()
            )
            startActivity(intent)
        } else {
            showToast("All parameters are mandatory.")
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, "Toast: $msg", Toast.LENGTH_LONG).show()
    }

    protected override fun onStart() {
        super.onStart()
        //generating new order number for every transaction
        val randomNum: Int = ServiceUtility.randInt(0, 9999999)
        orderId?.setText(randomNum.toString())
    }
}