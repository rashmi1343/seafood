package com.app.seafoodapp

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Interface.delegateInterface
import com.app.seafoodapp.model.Adddelegatemodel

import java.lang.Exception
import java.util.ArrayList

class DelegateAdapter(
    private val mContext: Context,
    mdelegateList: ArrayList<Adddelegatemodel>,
    dellInterface: delegateInterface
) : RecyclerView.Adapter<DelegateAdapter.DelegateHolder?>() {
    private val mdelegateList: ArrayList<Adddelegatemodel>

    // private int value;
    private val dellInterface: delegateInterface
    var mRegular: Typeface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.stall_detail_items, parent, false)
        return DelegateHolder(view)
    }

    override fun onBindViewHolder(holder: DelegateHolder, position: Int) {
        val delegateDetailModel: Adddelegatemodel = mdelegateList[position]
        holder.delegate_name.setText(delegateDetailModel.delegatename)
        holder.designation.setText(delegateDetailModel.delegatedesignation)
        holder.phone.setText(delegateDetailModel.delegatemobile)
        holder.delete.setOnClickListener {
            try {
                dellInterface.deletedelgateitem(position, delegateDetailModel.delegatename)
                Log.d("delegatelist", mdelegateList.size.toString())
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
        }
    }



    inner class DelegateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val delegate_name: TextView
        val designation: TextView
        val phone: TextView
        val delete: ImageView

        init {
            delegate_name = itemView.findViewById<TextView>(R.id.dlegate_name_txt)
            delegate_name.setTypeface(mRegular)
            designation = itemView.findViewById<TextView>(R.id.designation_name_txt)
            designation.setTypeface(mRegular)
            phone = itemView.findViewById<TextView>(R.id.mobile_txt)
            phone.setTypeface(mRegular)
            delete = itemView.findViewById(R.id.delete_delegate)
        }
    }

    init {
        this.mdelegateList = mdelegateList
        //this.value = value;
        this.dellInterface = dellInterface
        mRegular = Typeface.createFromAsset(mContext.assets, "montserrat/Montserrat-Regular.otf")
    }

    override fun getItemCount(): Int {
       return  mdelegateList.size
    }
}