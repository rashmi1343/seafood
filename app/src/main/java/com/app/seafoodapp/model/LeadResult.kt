package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class LeadResult {
    @SerializedName("status")
    var mleadStatus: String? = null

    @SerializedName("msg")
    var mleadMsg: String? = null

    @SerializedName("result")
    var leadModel: String? = null

    @SerializedName("resultstatus")
    private var mResultStatus: String? = null
    fun getmResultStatus(): String? {
        return mResultStatus
    }

    fun setmResultStatus(mResultStatus: String?) {
        this.mResultStatus = mResultStatus
    }
}