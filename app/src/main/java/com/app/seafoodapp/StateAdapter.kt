package com.app.seafoodapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.app.seafoodapp.Interface.StateInterface
import com.app.seafoodapp.model.StateModel

 class StateAdapter(
    private val mContext: Context,
    mStateList: List<StateModel>,
    mInterface: StateInterface
) : RecyclerView.Adapter<StateAdapter.StateHolder?>() {
    private var mStateList: List<StateModel>
    private val mInterface: StateInterface
    private var lastSelectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.state_list_item, parent, false)
        return StateHolder(view)
    }

    override fun onBindViewHolder(holder: StateHolder, @SuppressLint("RecyclerView") position: Int) {
        val stateModel: StateModel = mStateList[position]
        holder.mStateRadio.setChecked(lastSelectedPosition == position)
        holder.mStateText.setOnClickListener(View.OnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            mInterface.clicktogetState(stateModel.state_id, stateModel.state)
        })
        holder.mStateRadio.setOnClickListener(View.OnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            mInterface.clicktogetState(stateModel.state_id, stateModel.state)
        })
        holder.mStateText.setText(stateModel.state)
    }

    override fun getItemCount(): Int {
        return mStateList.size
    }

    inner class StateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mStateRadio: RadioButton
        val mStateText: TextView

        init {
            mStateRadio = itemView.findViewById<RadioButton>(R.id.radio_state)
            mStateText = itemView.findViewById<TextView>(R.id.state_txt)
        }
    }

    fun filterList(filterName: List<StateModel>) {
        mStateList = filterName
        notifyDataSetChanged()
    }

    init {
        this.mStateList = mStateList
        this.mInterface = mInterface
    }
}