package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class ResultSeminar {
    @SerializedName("seminarSessionId")
    private var mSessionId: String? = null

    @SerializedName("seminarSessionName")
    private var mSessionName: String? = null

    @SerializedName("seminarSessionTitle")
    private var mSessionTitle: String? = null
    fun getmSessionId(): String? {
        return mSessionId
    }

    fun setmSessionId(mSessionId: String?) {
        this.mSessionId = mSessionId
    }

    fun getmSessionName(): String? {
        return mSessionName
    }

    fun setmSessionName(mSessionName: String?) {
        this.mSessionName = mSessionName
    }

    fun getmSessionTitle(): String? {
        return mSessionTitle
    }

    fun setmSessionTitle(mSessionTitle: String?) {
        this.mSessionTitle = mSessionTitle
    }

    @SerializedName("seminarId")
    private var mSeminarId: String? = null

    @SerializedName("seminarOwner")
    private var mSeminarOwner: String? = null

    @SerializedName("seminarTitle")
    private var mSeminarTitle: String? = null

    @SerializedName("seminarDate")
    private var mSeminarDate: String? = null

    @SerializedName("seminarStartTime")
    private var mSeminarStart: String? = null

    @SerializedName("seminarEndTime")
    private var mSeminarEnd: String? = null
    fun setmSeminarId(mSeminarId: String?) {
        this.mSeminarId = mSeminarId
    }

    fun setmSeminarOwner(mSeminarOwner: String?) {
        this.mSeminarOwner = mSeminarOwner
    }

    fun setmSeminarTitle(mSeminarTitle: String?) {
        this.mSeminarTitle = mSeminarTitle
    }

    fun setmSeminarDate(mSeminarDate: String?) {
        this.mSeminarDate = mSeminarDate
    }

    fun setmSeminarStart(mSeminarStart: String?) {
        this.mSeminarStart = mSeminarStart
    }

    fun setmSeminarEnd(mSeminarEnd: String?) {
        this.mSeminarEnd = mSeminarEnd
    }

    fun getmSeminarId(): String? {
        return mSeminarId
    }

    fun getmSeminarOwner(): String? {
        return mSeminarOwner
    }

    fun getmSeminarTitle(): String? {
        return mSeminarTitle
    }

    fun getmSeminarDate(): String? {
        return mSeminarDate
    }

    fun getmSeminarStart(): String? {
        return mSeminarStart
    }

    fun getmSeminarEnd(): String? {
        return mSeminarEnd
    }
}