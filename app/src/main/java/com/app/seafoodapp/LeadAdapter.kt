package com.app.seafoodapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.LeadAdapter.DataObjectHolder
import com.app.seafoodapp.model.GetAllLeadModel
import com.app.seafoodapp.ui.LeadAquistionActivity

import java.util.ArrayList

class LeadAdapter(context: Activity, myDataset: ArrayList<GetAllLeadModel>) :
    RecyclerView.Adapter<DataObjectHolder?>() {
    private lateinit var myClickListener: MyClickListener
    private val mDataset: ArrayList<GetAllLeadModel>
    private val ctx: Context
    private var pst = 0
    private val mMuliBold: Typeface

    // private LeadFilterInterface mldfilterinterface;
    class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leadnameval: TextView
        var mAddDesignation: TextView
        var mCompany: TextView
        var mEmail: TextView
        var mMobile: TextView
        var leaddesignationval: TextView? = null
        var mAddNoteText: TextView
        var mAddNotes: RelativeLayout

        init {
            leadnameval = itemView.findViewById<View>(R.id.tvleadnameval) as TextView
            mAddNotes = itemView.findViewById<RelativeLayout>(R.id.add_note_layout)
            mAddNoteText = itemView.findViewById<TextView>(R.id.add_note_txt)
            mAddDesignation = itemView.findViewById<TextView>(R.id.tvleaddesignationval)
            mCompany = itemView.findViewById<TextView>(R.id.tvcompanyval)
            mEmail = itemView.findViewById<TextView>(R.id.tvemailval)
            mMobile = itemView.findViewById<TextView>(R.id.tvphoneval)
            Log.i(LOG_TAG, "Adding Listener")
            // itemView.setOnClickListener(this);
        } /* @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }*/
    }

    fun setOnItemClickListener(myClickListener: MyClickListener) {
        this.myClickListener = myClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataObjectHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.card_view_row, parent, false)
        return DataObjectHolder(view)
    }

//    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {
//        val getAllLeadModel: GetAllLeadModel = mDataset[position]
//        holder.leadnameval.setTypeface(mMuliBold)
//        holder.mAddDesignation.setTypeface(mMuliBold)
//        holder.mAddNoteText.setTypeface(mMuliBold)
//        holder.leadnameval.setText(mDataset[position].getLeadname())
//        holder.mAddDesignation.setText(mDataset[position].getDesignation())
//        holder.mCompany.setTypeface(mMuliBold)
//        holder.mCompany.setText(getAllLeadModel.getCompanyname())
//        holder.mEmail.setTypeface(mMuliBold)
//        holder.mEmail.setText(getAllLeadModel.getLeademail())
//        holder.mMobile.setTypeface(mMuliBold)
//        holder.mMobile.setText(getAllLeadModel.getLeadmobile())
//        pst = position
//        holder.mAddNotes.setOnClickListener(View.OnClickListener {
//            Log.d("click pst", pst.toString())
//            showdialogfornotes(
//                mDataset[pst].getLeadname(),
//                mDataset[pst].getLeademail(),
//                mDataset[pst].getLeadmobile(),
//                mDataset[pst].getLeadnote(),
//                mDataset[pst].getLeadid()
//            )
//        })
//    }

    fun addItem(dataObj: GetAllLeadModel, index: Int) {
        mDataset.add(index, dataObj)
        notifyItemInserted(index)
    }

    fun deleteItem(index: Int) {
        mDataset.removeAt(index)
        notifyItemRemoved(index)
    }


    interface MyClickListener {
        fun onItemClick(position: Int, v: View?)
    }

    fun showdialogfornotes(
        leadname: String?,
        leademail: String?,
        leadmobile: String?,
        leadnotes: String?,
        leadid: String?
    ) {
        //LayoutInflater inflater = ctx.getLayoutInflater();

        //  LayoutInflater inflater = ((Activity)this.ctx)).getLayoutInflater();
        val inflater: LayoutInflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val alertLayout: View = inflater.inflate(R.layout.add_note_lead, null)
        val etUsername: EditText = alertLayout.findViewById<EditText>(R.id.et_leadname)
        val etEmail: EditText = alertLayout.findViewById<EditText>(R.id.et_email)
        val et_mobile: EditText = alertLayout.findViewById<EditText>(R.id.et_mobile)
        val et_note: EditText = alertLayout.findViewById<EditText>(R.id.et_note)
        etUsername.setText(leadname)
        etEmail.setText(leademail)
        et_mobile.setText(leadmobile)
        etUsername.setVisibility(View.GONE)
        etEmail.setVisibility(View.GONE)
        et_mobile.setVisibility(View.GONE)
        et_note.setText(leadnotes)
        val alert = AlertDialog.Builder(ctx)
        alert.setTitle("Add Notes")
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout)
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false)
        alert.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                //Toast.makeText(this.ctx, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        })
        alert.setPositiveButton("Submit", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                val user: String = etUsername.getText().toString()
                val pass: String = etEmail.getText().toString()
                if (ctx is LeadAquistionActivity) {
                    (ctx as LeadAquistionActivity).sendnotetoserver(leadid, leadnotes)
                }

                // Toast.makeText(getBaseContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();
            }
        })
        val dialog = alert.create()
        dialog.show()
    }

    companion object {
        private const val LOG_TAG = "MyRecyclerViewAdapter"
        private val myClickListener: MyClickListener? = null
    }

    init {
        mDataset = myDataset
        ctx = context
        mMuliBold = Typeface.createFromAsset(context.getAssets(), "montserrat/Montserrat-Bold.otf")
        // this.mldfilterinterface = ldfiterinteface;
    }

    override fun getItemCount(): Int {
      return  mDataset.size
    }

    override fun onBindViewHolder(holder: DataObjectHolder, @SuppressLint("RecyclerView") position: Int) {
                val getAllLeadModel: GetAllLeadModel = mDataset[position]
        holder.leadnameval.setTypeface(mMuliBold)
        holder.mAddDesignation.setTypeface(mMuliBold)
        holder.mAddNoteText.setTypeface(mMuliBold)
        holder.leadnameval.setText(mDataset[position].leadname)
        holder.mAddDesignation.setText(mDataset[position].designation)
        holder.mCompany.setTypeface(mMuliBold)
        holder.mCompany.setText(getAllLeadModel.companyname)
        holder.mEmail.setTypeface(mMuliBold)
        holder.mEmail.setText(getAllLeadModel.leademail)
        holder.mMobile.setTypeface(mMuliBold)
        holder.mMobile.setText(getAllLeadModel.leadmobile)
        pst = position
        holder.mAddNotes.setOnClickListener(View.OnClickListener {
            Log.d("click pst", pst.toString())
            showdialogfornotes(
                mDataset[pst].leadname,
                mDataset[pst].leademail,
                mDataset[pst].leadmobile,
                mDataset[pst].leadnote,
                mDataset[pst].leadid
            )
        })
    }
}