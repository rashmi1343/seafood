package com.app.seafoodapp.model

class TrainingModel(
    private val mTimings: Any,
    private val mTopics: Any,
    private val mSpeaker: Any
) {
    fun getmTimings(): Any {
        return mTimings
    }

    fun getmTopics(): Any {
        return mTopics
    }

    fun getmSpeaker(): Any {
        return mSpeaker
    }
}