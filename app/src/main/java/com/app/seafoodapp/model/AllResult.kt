package com.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AllResult : Serializable {
    //    @SerializedName("id")
//    var id: String? = null
//
//    @SerializedName("stall_name")
//    private var mStallName: String? = null
//
//    @SerializedName("stall_category")
//    private var mStallCategory: String? = null
//
//    @SerializedName("stall_length")
//    private var mLength: String? = null
//
//    @SerializedName("stall_width")
//    private var mWidth: String? = null
//
//    @SerializedName("nonm_stallprice")
//    private var mStallPrice: String? = null
//
//    @SerializedName("nonm_gstprice")
//    private var mStallGst = 0.0
//
//    @SerializedName("nonm_totalprice")
//    private var mTotalPrice: String? = null
//
//    @SerializedName("mem_stallprice")
//    var member_price: String? = null
//
//    @SerializedName("mem_gstprice")
//    var member_gst: String? = null
//
//    @SerializedName("mem_totalprice")
//    var member_total_price: String? = null
    @SerializedName("id")
    var id: String? = null
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
    var mblockedBy: String? = null
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
    @SerializedName("paid_amount")
    var mpaidAmount: String? = null
    @SerializedName("pmode")
    var mpmode: String? = null
    @SerializedName("transid")
    var mtransid: String? = null
    @SerializedName("payment_date")
    var mpaymentDate: String? = null
    @SerializedName("currency")
    var mcurrency: String? = null
    @SerializedName("payment_success_flag")
    var mpaymentSuccessFlag: String? = null
    @SerializedName("nonm_stallprice")
    var mnonmStallprice: String? = null
    @SerializedName("nonm_gstprice")
    var mnonmGstprice: Double? = null
    @SerializedName("nonm_totalprice")
    var mnonmTotalprice: String? = null
    @SerializedName("mem_stallprice")
    var mmemStallprice: String? = null
    @SerializedName("mem_gstprice")
    var mmemGstprice: Int? = null
    @SerializedName("mem_totalprice")
    var mmemTotalprice: String? = null
    @SerializedName("mem_currency")
    var mmemCurrency: String? = null

    fun getmstallPoly(): String? {
        return mstallPoly
    }

    fun setmstallPoly(mstallPoly: String?) {
        this.mstallPoly = mstallPoly
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

    fun setmblockedBy(mblockedBy: String?) {
        this.mblockedBy = mblockedBy
    }

    fun getmblockedBy(): String? {
        return mblockedBy
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

    fun setmpaidAmount(mpaidAmount: String?) {
        this.mpaidAmount = mpaidAmount
    }

    fun getmpaidAmount(): String? {
        return mpaidAmount
    }

    fun setmpmode(mpmode: String?) {
        this.mpmode = mpmode
    }

    fun getmpmode(): String? {
        return mpmode
    }

    fun setmtransid(mtransid: String?) {
        this.mtransid = mtransid
    }

    fun getmtransid(): String? {
        return mtransid
    }

    fun setmpaymentDate(mpaymentDate: String?) {
        this.mpaymentDate = mpaymentDate
    }

    fun getmpaymentDate(): String? {
        return mpaymentDate
    }

    fun setmcurrency(mcurrency: String?) {
        this.mcurrency = mcurrency
    }

    fun getmcurrency(): String? {
        return mcurrency
    }

    fun setmpaymentSuccessFlag(mpaymentSuccessFlag: String?) {
        this.mpaymentSuccessFlag = mpaymentSuccessFlag
    }

    fun getmpaymentSuccessFlag(): String? {
        return mpaymentSuccessFlag
    }

    fun setmnonmStallprice(mnonmStallprice: String?) {
        this.mnonmStallprice = mnonmStallprice
    }

    fun getmnonmStallprice(): String? {
        return mnonmStallprice
    }

    fun setmnonmGstprice(mnonmGstprice: Double?) {
        this.mnonmGstprice = mnonmGstprice
    }

    fun getmnonmGstprice(): Double? {
        return mnonmGstprice
    }

    fun setmnonmTotalprice(mnonmTotalprice: String?) {
        this.mnonmTotalprice = mnonmTotalprice
    }

    fun getmnonmTotalprice(): String? {
        return mnonmTotalprice
    }

    fun setmmemStallprice(mmemStallprice: String?) {
        this.mmemStallprice = mmemStallprice
    }

    fun getmmemStallprice(): String? {
        return mmemStallprice
    }

    fun setmmemGstprice(mmemGstprice: Int?) {
        this.mmemGstprice = mmemGstprice
    }

    fun getmmemGstprice(): Int? {
        return mmemGstprice
    }

    fun setmmemTotalprice(mmemTotalprice: String?) {
        this.mmemTotalprice = mmemTotalprice
    }

    fun getmmemTotalprice(): String? {
        return mmemTotalprice
    }

    fun setmmemCurrency(mmemCurrency: String?) {
        this.mmemCurrency = mmemCurrency
    }

    fun getmmemCurrency(): String? {
        return mmemCurrency
    }


}