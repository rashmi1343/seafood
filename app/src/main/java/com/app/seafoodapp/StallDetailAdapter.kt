package com.app.seafoodapp

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Interface.RemoveStall
import com.app.seafoodapp.model.StallDetailModel


 class StallDetailAdapter(
    private val mContext: Context,
    mStallList: List<StallDetailModel>,
    mRemoveStall: RemoveStall
) : RecyclerView.Adapter<StallDetailAdapter.StallDetailHolder?>() {
    private val mStallList: List<StallDetailModel>
    private val mRemoveStall: RemoveStall
    var mRegular: Typeface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallDetailHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.stall_detail_items, parent, false)
        return StallDetailHolder(view)
    }

    override fun onBindViewHolder(holder: StallDetailHolder, position: Int) {
        val stallDetailModel: StallDetailModel = mStallList[position]
        holder.delegate_name.setText(stallDetailModel.delegate_name)
        holder.designation.setText(stallDetailModel.designation)
        holder.phone.setText(stallDetailModel.mobile)
        holder.stallname_txt.setText(stallDetailModel.selected_stall)
        holder.delete.setOnClickListener { //   mRemoveStall.removeElement(position,stallDetailModel.getDelegate_name());
            mRemoveStall.removeElement(position, stallDetailModel.selected_stall)
        }
    }

    override fun getItemCount(): Int {
        return mStallList.size
    }

    inner class StallDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val delegate_name: TextView
        val designation: TextView
        val phone: TextView
        val stallname_txt: TextView
        val delete: ImageView

        init {
            delegate_name = itemView.findViewById<TextView>(R.id.dlegate_name_txt)
            delegate_name.setTypeface(mRegular)
            designation = itemView.findViewById<TextView>(R.id.designation_name_txt)
            designation.setTypeface(mRegular)
            phone = itemView.findViewById<TextView>(R.id.mobile_txt)
            phone.setTypeface(mRegular)
            stallname_txt = itemView.findViewById<TextView>(R.id.stallname_txt)
            stallname_txt.setTypeface(mRegular)
            delete = itemView.findViewById(R.id.delete_delegate)
        }
    }

    init {
        this.mStallList = mStallList
        this.mRemoveStall = mRemoveStall
        mRegular = Typeface.createFromAsset(mContext.assets, "montserrat/Montserrat-Regular.otf")
    }
}