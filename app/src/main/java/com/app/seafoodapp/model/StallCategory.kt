package com.app.seafoodapp.model

import com.google.gson.annotations.SerializedName


class Result {
    @SerializedName("id")
    var mid: String? = null

    @SerializedName("stall_poly")
    var mstallPoly: String? = null

    @SerializedName("stall_name")
    var mstallName: String? = null

    @SerializedName("stall_category")
    var mstallCategory: String? = null

    @SerializedName("stall_categoryblock")
    var mstallCategoryblock: String? = null

    @SerializedName("stall_year")
    var mstallYear: String? = null

    @SerializedName("total_corner")
    var mtotalCorner: String? = null

    @SerializedName("stall_length")
    var mstallLength: String? = null

    @SerializedName("stall_width")
    var mstallWidth: String? = null

    @SerializedName("stall_size_unit")
    var mstallSizeUnit: String? = null

    @SerializedName("stall_price")
    var mstallPrice: String? = null

    @SerializedName("blocked_by")
    var blockedBy: String? = null

    @SerializedName("blocked_on")
    var mblockedOn: String? = null

    @SerializedName("allocated_to")
    var mallocatedTo: String? = null

    @SerializedName("allocated_on")
    var mallocatedOn: String? = null

    @SerializedName("approved")
    var mapproved: String? = null

    @SerializedName("approved_by")
    var mapprovedBy: String? = null

    @SerializedName("approved_on")
    var mapprovedOn: String? = null

    @SerializedName("stall_price_usd")
    var mstallPriceUsd: String? = null

    @SerializedName("allotted_user")
    var mallottedUser: String? = null

    @SerializedName("blocked_user")
    var mblockedUser: String? = null


    fun setmid(mid: String?) {
        this.mid = mid
    }

    fun getmid(): String? {
        return mid
    }

    fun setmstallPoly(mstallPoly: String?) {
        this.mstallPoly = mstallPoly
    }

    fun getmstallPoly(): String? {
        return mstallPoly
    }

    fun setmstallName(mstallName: String?) {
        this.mstallName = mstallName
    }

    fun getmstallName(): String? {
        return mstallName
    }

    fun setmstallCategory(mstallCategory: String?) {
        this.mstallCategory = mstallCategory
    }

    fun getmstallCategory(): String? {
        return mstallCategory
    }

    fun setmstallCategoryblock(mstallCategoryblock: String?) {
        this.mstallCategoryblock = mstallCategoryblock
    }

    fun getmstallCategoryblock(): String? {
        return mstallCategoryblock
    }

    fun setmstallYear(mstallYear: String?) {
        this.mstallYear = mstallYear
    }

    fun getmstallYear(): String? {
        return mstallYear
    }

    fun setmtotalCorner(mtotalCorner: String?) {
        this.mtotalCorner = mtotalCorner
    }

    fun getmtotalCorner(): String? {
        return mtotalCorner
    }

    fun setmstallLength(mstallLength: String?) {
        this.mstallLength = mstallLength
    }

    fun getmstallLength(): String? {
        return mstallLength
    }

    fun setmstallWidth(mstallWidth: String?) {
        this.mstallWidth = mstallWidth
    }

    fun getmstallWidth(): String? {
        return mstallWidth
    }

    fun setmstallSizeUnit(mstallSizeUnit: String?) {
        this.mstallSizeUnit = mstallSizeUnit
    }

    fun getmstallSizeUnit(): String? {
        return mstallSizeUnit
    }

    fun setmstallPrice(mstallPrice: String?) {
        this.mstallPrice = mstallPrice
    }

    fun getmstallPrice(): String? {
        return mstallPrice
    }

    fun setmblockedOn(mblockedOn: String?) {
        this.mblockedOn = mblockedOn
    }

    fun getmblockedOn(): String? {
        return mblockedOn
    }

    fun setmallocatedTo(mallocatedTo: String?) {
        this.mallocatedTo = mallocatedTo
    }

    fun getmallocatedTo(): String? {
        return mallocatedTo
    }
    fun setmallocatedOn(mallocatedOn: String?) {
        this.mallocatedOn = mallocatedOn
    }

    fun getmallocatedOn(): String? {
        return mallocatedOn
    }
    fun setmapproved(mapproved: String?) {
        this.mapproved = mapproved
    }

    fun getmapproved(): String? {
        return mapproved
    }
    fun setmapprovedBy(mapprovedBy: String?) {
        this.mapprovedBy = mapprovedBy
    }

    fun getmapprovedBy(): String? {
        return mapprovedBy
    }
    fun setmapprovedOn(mapprovedOn: String?) {
        this.mapprovedOn = mapprovedOn
    }

    fun getmapprovedOn(): String? {
        return mapprovedOn
    }
    fun setmstallPriceUsd(mstallPriceUsd: String?) {
        this.mstallPriceUsd = mstallPriceUsd
    }

    fun getmstallPriceUsd(): String? {
        return mstallPriceUsd
    }
    fun setmallottedUser(mallottedUser: String?) {
        this.mallottedUser = mallottedUser
    }

    fun getmallottedUser(): String? {
        return mallottedUser
    }
    fun setmblockedUser(mblockedUser: String?) {
        this.mblockedUser = mblockedUser
    }

    fun getmblockedUser(): String? {
        return mblockedUser
    }


}




