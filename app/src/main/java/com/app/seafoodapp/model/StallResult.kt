package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

import com.model.AllResult

class StallResult {
    @SerializedName("status")
    private val mStatus: String? = null

    @SerializedName("msg")
    private val mMsg: String? = null
    fun getmResult(): List<AllResult>? {
        return mResult
    }

    @SerializedName("result")
    private val mResult: List<AllResult>? = null
    fun getmStatus(): String? {
        return mStatus
    }

    fun getmMsg(): String? {
        return mMsg
    }

    @SerializedName("resultstatus")
    private val mResultStatus: Resultstatus?=null
}

class Resultstatus {

    @SerializedName("corner_price")
    var cornerPrice: Int? = null

    fun getmCornerPrice(): Int? {
        return cornerPrice
    }


    fun setmCornerPrice(mcornerPrice: Int) {
        this.cornerPrice = mcornerPrice
    }

    @SerializedName("resultstatus")
    var resultstatus: String? = null

    fun getmResultStatus(): String? {
        return resultstatus
    }


}