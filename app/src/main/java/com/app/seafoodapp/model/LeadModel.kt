package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class LeadModel {
    @SerializedName("leadid")
    var leadid: String? = null

    @SerializedName("leadname")
    var leadname: String? = null

    @SerializedName("leademail")
    var leademail: String? = null

    @SerializedName("leadmobile")
    var leadmobile: String? = null
}