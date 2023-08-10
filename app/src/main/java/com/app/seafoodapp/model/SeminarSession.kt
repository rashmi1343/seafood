package com.app.seafoodapp.model

import com.app.seafoodapp.model.ResultSeminar
import com.app.seafoodapp.Interface.ParentListItem

class SeminarSession(
    var seminarSessionName: String,
    var seminarParticularSessionItem: List<ResultSeminar>
) : ParentListItem {
    var seminarSessionId: String? = null
    var seminarSessionTitle: String? = null
    override fun getChildItemList(): List<*>? {
        return null
    }

    override fun isInitiallyExpanded(): Boolean {
        return false
    }
}