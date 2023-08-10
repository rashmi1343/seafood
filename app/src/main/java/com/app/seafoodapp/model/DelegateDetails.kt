package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class DelegateDetails {
    @SerializedName("dele_id")
    var delegate_id: String? = null

    @SerializedName("act_id")
    var act_id: String? = null

    @SerializedName("dele_name")
    var delegate_name: String? = null

    @SerializedName("dele_email")
    var delegate_email: String? = null

    @SerializedName("dele_mobile")
    var dele_mobile: String? = null
}