package com.app.seafoodapp.model

class DataObject(private var mText1: String, private var mText2: String) {
    fun getmText1(): String {
        return mText1
    }

    fun setmText1(mText1: String) {
        this.mText1 = mText1
    }

    fun getmText2(): String {
        return mText2
    }

    fun setmText2(mText2: String) {
        this.mText2 = mText2
    }
}