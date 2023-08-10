package com.app.seafoodapp

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Interface.OnSelectSession
import com.app.seafoodapp.model.ResultSeminar
import com.app.seafoodapp.ui.TrainingQueryActivity


 class TrainingAdapter(
    mContext: Activity,
    mTrainingList: List<ResultSeminar>,
    selectSession: OnSelectSession
) : RecyclerView.Adapter<TrainingAdapter.TrainingHolder?>() {
    private val mContext: Activity
    private val mTrainingList: List<ResultSeminar>
    private val session: OnSelectSession
    var mRegular: Typeface
    var mMedium: Typeface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.training_session_layout, parent, false)
        return TrainingHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {
        val seminarModel: ResultSeminar = mTrainingList[position]
        holder.mSeminarName.setTypeface(mMedium)
        holder.mSeminarName.setText(seminarModel.getmSessionName())
        holder.mSeminarTitle.setTypeface(mMedium)
        holder.mSeminarTitle.setText(seminarModel.getmSeminarTitle())
        holder.mSeminarDateTxt.setTypeface(mMedium)
        holder.mSeminarDate.setTypeface(mRegular)
        holder.mSeminarDate.setText(seminarModel.getmSeminarDate())
        holder.mSeminarStartTxt.setTypeface(mMedium)
        holder.mSeminarStart.setTypeface(mRegular)
        holder.mSeminarStart.setText(seminarModel.getmSeminarStart())
        holder.mSeminarEndTxt.setTypeface(mMedium)
        holder.mSeminarEnd.setTypeface(mRegular)
        holder.mSeminarEnd.setText(seminarModel.getmSeminarEnd())
        holder.mSeminarOwnerTxt.setTypeface(mMedium)
        holder.mSeminarOwner.setTypeface(mRegular)
        holder.mSeminarOwner.setText(seminarModel.getmSeminarOwner())
        holder.mTrainingLayout.setOnClickListener(View.OnClickListener {
            mContext.startActivity(
                Intent(mContext, TrainingQueryActivity::class.java)
                    .putExtra("seminar_name", mTrainingList[position].getmSessionName())
                    .putExtra("seminar_title", mTrainingList[position].getmSessionTitle())
                    .putExtra("seminar_owner", mTrainingList[position].getmSeminarOwner())
            )
            mContext.overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        })
    }

    override fun getItemCount(): Int {
        return mTrainingList.size
    }

    inner class TrainingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mSeminarName: TextView
        val mSeminarTitle: TextView
        val mSeminarOwner: TextView
        val mSeminarDate: TextView
        val mSeminarStart: TextView
        val mSeminarEnd: TextView
        val mSeminarOwnerTxt: TextView
        val mSeminarDateTxt: TextView
        val mSeminarStartTxt: TextView
        val mSeminarEndTxt: TextView
        val mTrainingLayout: LinearLayout

        init {
            mSeminarName = itemView.findViewById<TextView>(R.id.seminar_name)
            mSeminarTitle = itemView.findViewById<TextView>(R.id.seminar_title)
            mSeminarOwnerTxt = itemView.findViewById<TextView>(R.id.seminar_ownertxt)
            mSeminarOwner = itemView.findViewById<TextView>(R.id.seminar_owner)
            mSeminarDateTxt = itemView.findViewById<TextView>(R.id.seminar_datetxt)
            mSeminarDate = itemView.findViewById<TextView>(R.id.seminar_date)
            mSeminarStartTxt = itemView.findViewById<TextView>(R.id.seminar_starttxt)
            mSeminarStart = itemView.findViewById<TextView>(R.id.seminar_start)
            mSeminarEndTxt = itemView.findViewById<TextView>(R.id.seminar_endtxt)
            mSeminarEnd = itemView.findViewById<TextView>(R.id.seminar_end)
            mTrainingLayout = itemView.findViewById<LinearLayout>(R.id.training_parent_root)
        }
    }

    companion object {
        private val TAG = TrainingAdapter::class.java.simpleName
    }

    init {
        this.mContext = mContext
        this.mTrainingList = mTrainingList
        session = selectSession
        mRegular =
            Typeface.createFromAsset(mContext.getAssets(), "montserrat/Montserrat-Regular.otf")
        mMedium = Typeface.createFromAsset(mContext.getAssets(), "montserrat/Montserrat-Medium.otf")
    }
}