package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName

class GetAllLeadModel {
    @SerializedName("id")
    var leadid: String? = null

    @SerializedName("leadname")
    var leadname: String? = null

    @SerializedName("leademail")
    var leademail: String? = null

    @SerializedName("leadmobile")
    var leadmobile: String? = null

    @SerializedName("companyname")
    var companyname: String? = null

    @SerializedName("designation")
    var designation: String? = null

    @SerializedName("leadDate")
    var leadDate: String? = null

    @SerializedName("leadnote")
    var leadnote: String? = null
}