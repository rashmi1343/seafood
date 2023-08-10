package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName
import com.app.seafoodapp.model.FinalResult

class StallModel {
    @SerializedName("status")
    private val mStatus: String? = null

    @SerializedName("msg")
    private val mMsg: String? = null
    fun getmResult(): FinalResult? {
        return mResult
    }

    @SerializedName("result")
    private val mResult: FinalResult? = null
    fun getmStatus(): String? {
        return mStatus
    }

    fun getmMsg(): String? {
        return mMsg
    }
}