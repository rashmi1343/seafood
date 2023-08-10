package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class UserProfile {
    @SerializedName("userId")
    private var mUserId: String? = null

    @SerializedName("userDisplayName")
    private var mDisplayUser: String? = null

    @SerializedName("userName")
    private var mUserName: String? = null

    @SerializedName("apiToken")
    private var mToken: String? = null
    fun getmUserId(): String? {
        return mUserId
    }

    fun setmUserId(mUserId: String?) {
        this.mUserId = mUserId
    }

    fun getmDisplayUser(): String? {
        return mDisplayUser
    }

    fun setmDisplayUser(mDisplayUser: String?) {
        this.mDisplayUser = mDisplayUser
    }

    fun getmUserName(): String? {
        return mUserName
    }

    fun setmUserName(mUserName: String?) {
        this.mUserName = mUserName
    }

    fun getmToken(): String? {
        return mToken
    }

    fun setmToken(mToken: String?) {
        this.mToken = mToken
    }
}