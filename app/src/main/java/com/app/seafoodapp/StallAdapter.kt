package com.app.seafoodapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Const.ConstApi
import com.app.seafoodapp.Interface.StallInterface

import com.model.AllResult

import java.text.DecimalFormat

class StallAdapter(
    private val mContext: Context,
    mStallList: List<AllResult>,
    value: Int,
    CurrencyType: String,
    stallInterface: StallInterface
) : RecyclerView.Adapter<StallAdapter.StallHolder?>() {
    private val mStallList: List<AllResult>
    private val value: Int
    private val stallInterface: StallInterface
    private var currencytype = " "
    var mRegular: Typeface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.stall_recycler_item, parent, false)
        return StallHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StallHolder, position: Int) {
        val allResult: AllResult = mStallList[position]
        val formatter = DecimalFormat("#,###,###")
        holder.stall_name.setText(allResult.getmstallName())
        holder.stall_length.setText(allResult.getmstallLength())
        holder.stall_width.setText(allResult.getmstallWidth())

        // holder.total_price.setText(allResult.getmnonmTotalprice().toString() + "")
        holder.delete_img.setOnClickListener {
            stallInterface.deleteData(
                allResult.id!!.toInt(),
                position,
                allResult.getmstallName()
            )
            notifyDataSetChanged()
        }
        holder.stall_name_member.setText(allResult.getmstallName())
        holder.stall_length_member.setText(allResult.getmstallLength())
        holder.stall_width_member.setText(allResult.getmstallWidth())




        if (allResult.getmtotalCorner().isNullOrEmpty()) {
            allResult.mtotalCorner = "0"
        }

        if (allResult.getmtotalCorner()?.toInt()!! > 0) {
            val totalcornerprice =
                ConstApi.mcorner_price.toDouble() * allResult.getmtotalCorner()!!.toDouble()

            val cornergstpercentage = (ConstApi.gstpercentage / 100) * totalcornerprice

            holder.cornerprice.setText(totalcornerprice.toString())

            holder.gstcornerprice.setText(cornergstpercentage.toString())


            if (ConstApi.exuser == 1) {
                val totalStallPrice =
                    totalcornerprice + cornergstpercentage + allResult.getmmemStallprice()!!
                        .toDouble() + allResult.getmmemGstprice()!!.toDouble()
                holder.stall_price_member.setText(allResult.getmmemStallprice())

                holder.gst_member.setText(allResult.getmmemGstprice().toString())
                holder.stall_price.setText(allResult.getmmemStallprice().toString() + "")
                holder.gst.setText(allResult.getmmemGstprice().toString() + "")

                holder.total_price_member.setText(allResult.getmmemTotalprice())
                holder.total_price.setText(totalStallPrice.toString())
            } else {
                val totalStallPrice =
                    totalcornerprice + cornergstpercentage + allResult.getmnonmStallprice()!!
                        .toDouble() + allResult.getmnonmGstprice()!!.toDouble()
                holder.stall_price_member.setText(allResult.getmnonmStallprice())

                holder.gst_member.setText(allResult.getmnonmGstprice().toString())
                holder.stall_price.setText(allResult.getmnonmStallprice().toString() + "")
                holder.gst.setText(allResult.getmnonmGstprice().toString() + "")
                holder.total_price_member.setText(allResult.getmnonmTotalprice())
                holder.total_price.setText(totalStallPrice.toString())
            }

            /*val totalStallPrice =
                totalcornerprice + cornergstpercentage + allResult.getmnonmStallprice()!!
                    .toDouble() + allResult.getmnonmGstprice()!!.toDouble()
            holder.total_price.setText(totalStallPrice.toString())
  */


        } else {

            holder.cornerprice.setText("0.0")
            holder.gstcornerprice.setText("0.0")
        }

    }

    override fun getItemCount(): Int {
        return mStallList.size
    }

    inner class StallHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stall_name: TextView
        var stall_price: TextView
        var gst: TextView
        var total_price: TextView
        var stall_length: TextView
        var stall_width: TextView
        var stall_name_member: TextView
        var stall_price_member: TextView
        var gst_member: TextView
        var total_price_member: TextView
        var stall_length_member: TextView
        var stall_width_member: TextView
        var hstall_name: TextView
        var hstall_price: TextView
        var hstallsize: TextView
        var cornerprice: TextView
        var gstcornerprice: TextView
        var hgst: TextView
        var htotal: TextView
        var delete_img: ImageView
        var delete_img_member: ImageView
        var imgcurrency: ImageView
        var imgcurrencytwo: ImageView
        var imgcurrencythree: ImageView
        var imgcurrencyfour: ImageView
        var first: LinearLayout
        var member_first: LinearLayout
        var second: LinearLayout
        var member_second: LinearLayout
        var first_view: View
        var last_view: View

        init {
            hstall_name = itemView.findViewById<TextView>(R.id.name_stall_txt)
            hstall_price = itemView.findViewById<TextView>(R.id.price_stall_txt)
            cornerprice = itemView.findViewById<TextView>(R.id.txt_cornor_price)
            gstcornerprice = itemView.findViewById<TextView>(R.id.txt_cornor_gst_price)
            hstallsize = itemView.findViewById<TextView>(R.id.size_stall_txt)
            hgst = itemView.findViewById<TextView>(R.id.gst_stall_txt)
            htotal = itemView.findViewById<TextView>(R.id.stall_total_txt)
            hstall_name.setTypeface(mRegular)
            hstall_price.setTypeface(mRegular)
            cornerprice.setTypeface(mRegular)
            gstcornerprice.setTypeface(mRegular)
            hstallsize.setTypeface(mRegular)
            hgst.setTypeface(mRegular)
            htotal.setTypeface(mRegular)
            first = itemView.findViewById<LinearLayout>(R.id.first_linear)
            second = itemView.findViewById<LinearLayout>(R.id.second_linear)
            member_first = itemView.findViewById<LinearLayout>(R.id.first_member_layout)
            member_second = itemView.findViewById<LinearLayout>(R.id.second_member_layout)
            first_view = itemView.findViewById(R.id.first_view)
            last_view = itemView.findViewById(R.id.last_view)
            stall_name_member = itemView.findViewById<TextView>(R.id.txt_stall_name_member)
            stall_price_member = itemView.findViewById<TextView>(R.id.txt_stall_price_member)
            gst_member = itemView.findViewById<TextView>(R.id.txt_gst_price_member)
            total_price_member = itemView.findViewById<TextView>(R.id.txt_total_price_member)
            stall_length_member = itemView.findViewById<TextView>(R.id.txt_stall_length_member)
            stall_width_member = itemView.findViewById<TextView>(R.id.txt_stall_width_member)
            stall_name = itemView.findViewById<TextView>(R.id.txt_stall_name)
            stall_length = itemView.findViewById<TextView>(R.id.txt_stall_length)
            stall_width = itemView.findViewById<TextView>(R.id.txt_stall_width)
            stall_price = itemView.findViewById<TextView>(R.id.txt_stall_price)
            gst = itemView.findViewById<TextView>(R.id.txt_gst_price)
            total_price = itemView.findViewById<TextView>(R.id.txt_total_price)
            delete_img = itemView.findViewById(R.id.delete_img)
            delete_img_member = itemView.findViewById(R.id.delete_img_member)
            imgcurrency = itemView.findViewById(R.id.imgcurrency)
            imgcurrencytwo = itemView.findViewById(R.id.imgcurrencytwo)
            imgcurrencythree = itemView.findViewById(R.id.imgcurrencythree)
            imgcurrencyfour = itemView.findViewById(R.id.imgcurrencyfour)
            stall_name.setTypeface(mRegular)
            stall_price.setTypeface(mRegular)
            gst.setTypeface(mRegular)
            total_price.setTypeface(mRegular)
            stall_width.setTypeface(mRegular)
            stall_length.setTypeface(mRegular)
            stall_name_member.setTypeface(mRegular)
            stall_price_member.setTypeface(mRegular)
            gst_member.setTypeface(mRegular)
            total_price_member.setTypeface(mRegular)
            stall_length_member.setTypeface(mRegular)
            stall_width_member.setTypeface(mRegular)
            if (currencytype.equals("$", ignoreCase = true)) {
                val uri = "@drawable/dollar"
                val imgRes = mContext.resources.getIdentifier(uri, null, mContext.packageName)
                val resource: Drawable = mContext.resources.getDrawable(imgRes)
                imgcurrency.setImageDrawable(resource)
                imgcurrencytwo.setImageDrawable(resource)
                imgcurrencythree.setImageDrawable(resource)
                imgcurrencyfour.setImageDrawable(resource)
            } else if (currencytype.equals("inr", ignoreCase = true)) {
                val uri = "@drawable/rupee_indian"
                val imgRes = mContext.resources.getIdentifier(uri, null, mContext.packageName)
                val resource: Drawable = mContext.resources.getDrawable(imgRes)
                imgcurrency.setImageDrawable(resource)
                imgcurrencytwo.setImageDrawable(resource)
                imgcurrencythree.setImageDrawable(resource)
                imgcurrencyfour.setImageDrawable(resource)
            }
            if (value == 2) {
                delete_img.visibility = View.GONE
            } else if (value == 3) {
                first.setVisibility(View.GONE)
                second.setVisibility(View.GONE)
                first_view.visibility = View.GONE
                member_first.setVisibility(View.VISIBLE)
                member_second.setVisibility(View.VISIBLE)
                last_view.visibility = View.VISIBLE
                delete_img_member.visibility = View.GONE
            } else {
                delete_img.visibility = View.VISIBLE
            }
        }
    }

    init {
        this.mStallList = mStallList
        this.value = value
        currencytype = CurrencyType
        this.stallInterface = stallInterface
        mRegular = Typeface.createFromAsset(mContext.assets, "montserrat/Montserrat-Regular.otf")
    }
}