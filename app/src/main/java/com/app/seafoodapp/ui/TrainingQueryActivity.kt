package com.app.seafoodapp.ui

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.seafoodapp.Const.ConstApi

import com.app.seafoodapp.Interface.SeminarInterface
import com.app.seafoodapp.R
import com.app.seafoodapp.model.Commonresult
import com.app.seafoodapp.model.GetAllLeadModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class TrainingQueryActivity : AppCompatActivity() {
    private var mSeminarName: TextView? = null
    private var mSeminarTitle: TextView? = null
    private var mSeminarOwnerText: TextView? = null
    private var mSeminarOwner: TextView? = null
    private var mSemName: String? = null
    private var mSemTitle: String? = null
    private var mSemOwner: String? = null
    private var mSemId: String? = null
    var mMedium: Typeface? = null
    private var edttitle: EditText? = null
    private var edtquery: EditText? = null
    private var query_button: Button? = null
    private var backicon: ImageView? = null
    private var closeicon: ImageView? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_query)
        mMedium = Typeface.createFromAsset(getAssets(), "montserrat/Montserrat-Medium.otf")
        if (getIntent().getStringExtra("seminar_name") != null) {
            mSemName = getIntent().getStringExtra("seminar_name")
        }
        if (getIntent().getStringExtra("seminar_title") != null) {
            mSemTitle = getIntent().getStringExtra("seminar_title")
        }
        if (getIntent().getStringExtra("seminar_owner") != null) {
            mSemOwner = getIntent().getStringExtra("seminar_owner")
        }
        if (getIntent().getStringExtra("seminar_owner") != null) {
            mSemId = getIntent().getStringExtra("seminar_id")
        }
        initText()
    }

    private fun initText() {
        mSeminarName = findViewById<TextView>(R.id.query_name)
        mSeminarName!!.setTypeface(mMedium)
        mSeminarTitle = findViewById<TextView>(R.id.query_title)
        mSeminarTitle!!.setTypeface(mMedium)
        mSeminarOwner = findViewById<TextView>(R.id.query_owner)
        mSeminarOwner!!.setTypeface(mMedium)
        mSeminarOwnerText = findViewById<TextView>(R.id.query_ownertxt)
        mSeminarOwnerText!!.setTypeface(mMedium)
        edttitle = findViewById<EditText>(R.id.edttitle)
        edtquery = findViewById<EditText>(R.id.edtquery)
        backicon = findViewById<ImageView>(R.id.backicon)
        backicon!!.setOnClickListener {
            val myIntent = Intent(this@TrainingQueryActivity, DashBoardActivity::class.java)
            // myIntent.putExtra("navigationcode", "1");
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        }
        closeicon = findViewById<ImageView>(R.id.closeicon)
        closeicon!!.setOnClickListener {
            val myIntent = Intent(this@TrainingQueryActivity, DashBoardActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
        }


        //   mSeminarName = findViewById(R.id.query_name);
        /*   if (mSemName!=null || mSemName!="")
        {
            mSeminarName.setText(mSemName);
        }
        else if (mSemTitle!=null || mSemTitle!="")
        {
            mSeminarTitle.setText(mSemTitle);
        }
        else if (mSemOwner!=null || mSemOwner!="")
        {
            mSeminarOwner.setText(mSemOwner);
        }*/if (mSemName != null || mSemName !== "") {
            mSeminarName!!.setText(mSemName)
        }
        if (mSemTitle != null || mSemTitle !== "") {
            mSeminarTitle!!.setText(mSemTitle)
        }
        if (mSemOwner != null || mSemOwner !== "") {
            mSeminarOwner!!.setText(mSemOwner)
        }
        query_button = findViewById<Button>(R.id.query_button)
        query_button!!.setOnClickListener { sendquerytoserver() }
    }

    private fun sendquerytoserver() {
        if (edttitle!!.getText().length == 0) {
            edttitle!!.setError("Please Enter Query title")
        } else if (edtquery!!.getText().length == 0) {
            edtquery!!.setError("Please Enter Query Description")
        } else {
            submitquery()
        }
    }

    private fun submitquery() {
        val jqueryobj = JsonObject()
        jqueryobj.addProperty("methodname", "addSeminar_query")
        jqueryobj.addProperty("seminar_id", mSemId)
        jqueryobj.addProperty("query_title", edttitle!!.getText().toString())
        jqueryobj.addProperty("query_description", edtquery!!.getText().toString())
        // jbookingbarcode.addProperty("bookingbarcode", bookingbarcode);
        // jbookingbarcode.addProperty("user_id", "100");
        //jbookingbarcode.addProperty("user_type", "1");
        val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.LIVE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mSeminar: SeminarInterface = mRetrofit.create(SeminarInterface::class.java)
        val mcalllead: Call<Commonresult>? = mSeminar.sendquery(jqueryobj)
        mcalllead!!.enqueue(object : Callback<Commonresult> {
            override fun onResponse(call: Call<Commonresult>, response: Response<Commonresult>) {
                if (response.isSuccessful()) {
                    val objallleadgson: GetAllLeadModel? = null
                    Log.d("TAG", "onResponse: " + response.body().toString())
                    // Log.d("TAG","ResultLead"+ response.body().getLeadModel());
                    try {
                        if (response.body().getmResultStatus().equals("1",ignoreCase = true)) {
                            val alertDialogBuilder = AlertDialog.Builder(
                                this@TrainingQueryActivity
                            )

                            // set title
                            alertDialogBuilder.setTitle("Query Submitted")
                            // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                            // set dialog message
                            alertDialogBuilder
                                .setMessage("Redirecting to Technical Session")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        // if this button is clicked, close
                                        // current activity
                                        // MainActivity.this.finish();
                                        val myIntent = Intent(
                                            this@TrainingQueryActivity,
                                            TrainingActivity::class.java
                                        )
                                        // myIntent.putExtra("navigationcode", "1");
                                        startActivity(myIntent)
                                        overridePendingTransition(
                                            R.anim.slidein_right,
                                            R.anim.slide_out_left
                                        )
                                        dialog.cancel()
                                    }
                                })


                            // create alert dialog
                            val alertDialog = alertDialogBuilder.create()

                            // show it
                            alertDialog.show()
                        } else if (response.body().getmResultStatus().equals("0",ignoreCase = true)) {
                            val alertDialogBuilder = AlertDialog.Builder(
                                this@TrainingQueryActivity
                            )

                            // set title
                            alertDialogBuilder.setTitle("Some thing Went Wrong, Please Try Again")
                            // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                            // set dialog message
                            alertDialogBuilder
                                .setMessage("Click yes to Send Query Again")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        // if this button is clicked, close
                                        // current activity
                                        // MainActivity.this.finish();
                                        dialog.cancel()
                                        // mScannerView.resumeCameraPreview(SeafoodScannerActivity.this);
                                    }
                                })
                            /* .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog,int id) {
                                           // if this button is clicked, just close
                                           // the dialog box and do nothing
                                           dialog.cancel();
                                       }
                                   });*/

                            // create alert dialog
                            val alertDialog = alertDialogBuilder.create()

                            // show it
                            alertDialog.show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<Commonresult>, t: Throwable) {
                Log.d("TAG", "onFailure: " + t.message.toString())
            }
        })
    }
}