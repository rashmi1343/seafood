package com.app.seafoodapp

import com.app.seafoodapp.model.AllDetails
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.CountryAdapter.CountryHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import com.app.seafoodapp.Interface.CountryInterface

 class CountryAdapter(
    private val mContext: Context,
    private var mCountryList: List<AllDetails>,
    value: String,
    mCountryInterface: CountryInterface
) : RecyclerView.Adapter<CountryHolder>() {
    private val mCountryInterface: CountryInterface
    private val value: String
    private var lastSelectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.country_recycler_item, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(
        holder: CountryHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val allDetails = mCountryList[position]
        holder.mCountry_text.text = allDetails.country_name
        holder.mRadio.isChecked = lastSelectedPosition == position
        holder.mCountry_text.setOnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            mCountryInterface.onSelectCountry(
                position,
                allDetails.country_id!!.toInt(),
                allDetails.country_name
            )
        }
        holder.mRadio.setOnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            mCountryInterface.onSelectCountry(
                position,
                allDetails.country_id!!.toInt(),
                allDetails.country_name
            )
        }
    }

    override fun getItemCount(): Int {
        return mCountryList.size
    }

    inner class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mRadio: RadioButton
        val mCountry_text: TextView

        init {
            mRadio = itemView.findViewById(R.id.radio_country)
            mCountry_text = itemView.findViewById(R.id.country_txt)
        }
    }

    fun filterList(filterName: List<AllDetails>) {
        mCountryList = filterName
        notifyDataSetChanged()
    }

    init {
        this.mCountryInterface = mCountryInterface
        this.value = value
    }
}