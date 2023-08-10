package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class Commonresult {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("result")
    var result: String? = null

    @SerializedName("resultstatus")
    private var mResultStatus: String? = null
    fun getmResultStatus(): String? {
        return mResultStatus
    }

    fun setmResultStatus(mResultStatus: String?) {
        this.mResultStatus = mResultStatus
    }
}