package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class SeminarModel {
    @SerializedName("status")
    private val mSeminarStatus: String? = null

    @SerializedName("msg")
    private val mSeminarMsg: String? = null

    @SerializedName("result")
    private val mResultSeminar: String? = null

    @SerializedName("resultstatus")
    private val mResultStatus: String? = null
    fun getmResultSeminar(): String? {
        return mResultSeminar
    }

    fun getmResultStatus(): String? {
        return mResultStatus
    }

    fun getmSeminarStatus(): String? {
        return mSeminarStatus
    }

    fun getmSeminarMsg(): String? {
        return mSeminarMsg
    }
}