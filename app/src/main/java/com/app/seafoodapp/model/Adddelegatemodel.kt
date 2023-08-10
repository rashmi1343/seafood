package com.app.seafoodapp.model

import android.util.Log
import org.json.JSONObject
import org.json.JSONException

class Adddelegatemodel     // this.delegateemailid = delegateemailid;
    (var delegatename: String, var delegatedesignation: String, var delegatemobile: String) {
    val jSONObject: JSONObject
        get() {
            val obj = JSONObject()
            try {
                obj.put("DelegateName", delegatename)
                obj.put("DelegateDesignation", delegatedesignation)
                obj.put("DelegateMobile", delegatemobile)
            } catch (e: JSONException) {
                Log.d("JSONException", e.message!!)
            }
            return obj
        }
}