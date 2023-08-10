package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class FinalResult {
    @SerializedName("status")
    val resultStatus: String? = null

    @SerializedName("error_no")
    val errorno: String? = null
    @SerializedName("error")
    val error: String? = null


    @SerializedName("txn_id")
    val transaction_id: String? = null

    @SerializedName("regID")
    val registratio_id: String? = null

    @SerializedName("amount")
    val final_amount: String? = null

    @SerializedName("currency")
    val currency: String? = null

    @SerializedName("invoiceId")
    val invoice_id: String? = null

    @SerializedName("payID")
    val payment_id: String? = null

    @SerializedName("stallRegisterID")
    val order_id: String? = null
}