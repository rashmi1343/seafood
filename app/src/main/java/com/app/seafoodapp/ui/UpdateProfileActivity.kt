package com.app.seafoodapp.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.app.seafoodapp.Const.ConstApi

import com.app.seafoodapp.Interface.ActivateInterface
import com.app.seafoodapp.R
import com.app.seafoodapp.model.Commonresult
import com.app.seafoodapp.model.DelegateDetails
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

class UpdateProfileActivity : AppCompatActivity() {
    var navigationcode = "null"
    var strdelegatedetails: String? = null
    var objdelegatedetail: JSONObject? = null
    var edt_delegate: EditText? = null
    var edt_email: EditText? = null
    private var mClose: ImageView? = null
    private var mBack: ImageView? = null
    var edt_mobile: EditText? = null
    var btnverify: AppCompatButton? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_update_profile)
        if (getIntent().getStringExtra("navigationcode") != null) {                       //  coming from lead -2  //coming from technical session -3
            navigationcode = getIntent().getStringExtra("navigationcode").toString()
        }
        if (getIntent().getStringExtra("delegatedetail") != null) {
            strdelegatedetails = getIntent().getStringExtra("delegatedetail")
        }
        init()
        mBack!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })
        mClose!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })

    }

    private fun init() {
        edt_delegate = findViewById<EditText>(R.id.edt_delegate)
        edt_email = findViewById<EditText>(R.id.edt_email)
        edt_mobile = findViewById<EditText>(R.id.edt_mobile)
        btnverify = findViewById<AppCompatButton>(R.id.btnverify)
        mClose = findViewById<ImageView>(R.id.close_profile_update)
        mBack = findViewById<ImageView>(R.id.back_profile_update)
        btnverify!!.setOnClickListener(View.OnClickListener {

            strdelegatedetails?.let { it1 -> activateuseraccount(it1) }
        })
        processdelegateinfo(strdelegatedetails)
    }

    private fun processdelegateinfo(delegatedetail: String?) {
        try {
            //Gson gsonactivatedelegate = new Gson();
            var activatedelegate: DelegateDetails? = null
            /* JSONObject jdelegatedetail = new JSONObject(delegatedetail);


            DelegateDetails objdelegatedetail = gsonactivatedelegate.fromJson(jdelegatedetail.toString(), DelegateDetails.class);*/
            val jarraylead = JSONArray(delegatedetail)
            for (i in 0 until jarraylead.length()) {
                val jobjlead: JSONObject = jarraylead.getJSONObject(i)
                val gsonactivatedelegate = Gson()
                activatedelegate =
                    gsonactivatedelegate.fromJson(jobjlead.toString(), DelegateDetails::class.java)
                Log.d("TAG", "activateddelegate" + activatedelegate.toString())
            }
            edt_delegate!!.setText(activatedelegate!!.delegate_name)
            //  edt_email!!.setText(activatedelegate!!.dele_mobile)
            // edt_mobile!!.setText(activatedelegate!!.delegate_email)
            edt_email!!.setText(activatedelegate!!.delegate_email)
            edt_mobile!!.setText(activatedelegate!!.dele_mobile)

            //  llmainlayout.setVisibility(View.VISIBLE);
            //  btnverify.setVisibility(View.VISIBLE);
        } catch (je: JSONException) {
            Log.d("exception", je.toString())
        }
    }

    private fun activateuseraccount(strdelegatedata: String) {
        var objdelegatedetail: DelegateDetails? = null
        try {
            val jarraylead = JSONArray(strdelegatedata)
            for (i in 0 until jarraylead.length()) {
                val jobjlead: JSONObject = jarraylead.getJSONObject(i)
                val gsonactivatedelegate = Gson()
                objdelegatedetail =
                    gsonactivatedelegate.fromJson(jobjlead.toString(), DelegateDetails::class.java)
                Log.d("TAG", "activateddelegate" + objdelegatedetail.toString())
            }
            val jactivateobj = JsonObject()
            jactivateobj.addProperty("methodname", "updateAndActivateDelegate")
            jactivateobj.addProperty("act_id", objdelegatedetail!!.act_id)
            jactivateobj.addProperty("user_id", objdelegatedetail!!.delegate_id)
            jactivateobj.addProperty("dele_name", edt_delegate!!.getText().toString())
            jactivateobj.addProperty("dele_email", edt_email!!.getText().toString())
            jactivateobj.addProperty("dele_mobile", edt_mobile!!.getText().toString())
            val mRetrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ConstApi.LIVE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val mactivateinterface: ActivateInterface =
                mRetrofit.create(ActivateInterface::class.java)
            val activatecalled: Call<Commonresult>? =
                mactivateinterface.Updteactivatedelegate(jactivateobj)
            activatecalled!!.enqueue(object : Callback<Commonresult> {
                override fun onResponse(
                    call: Call<Commonresult>,
                    response: Response<Commonresult>
                ) {
                    if (response.isSuccessful()) {
                        val objdelegatedetail: DelegateDetails? = null
                        Log.d("TAG", "onResponse: " + response.body().toString())
                        try {
                            if (response.body().getmResultStatus().equals("1", ignoreCase = true)

                            ) {             // Succesful Activated
                                try {
                                    val jarraylead = JSONArray(response.body().result)
                                    for (i in 0 until jarraylead.length()) {
                                        val jobjuserinfo: JSONObject = jarraylead.getJSONObject(i)
                                        //Gson gsonactivatedelegate = new Gson();
                                        //objdelegatedetail = gsonactivatedelegate.fromJson(jobjlead.toString(), DelegateDetails.class);
                                        com.app.seafoodapp.ui.util.userid =
                                            jobjuserinfo.getString("user_id")


                                        //  Log.d("TAG", "activateddelegate" + objdelegatedetail.toString());
                                    }


                                    /*   Intent myIntent = new Intent(SeafoodScannerActivity.this, LoginActivity.class);
                                myIntent.putExtra("navigationcode", "1");
                                myIntent.putExtra("delegatedetail",objdelegatedetail.toString());
                                startActivity(myIntent);*/if (navigationcode.equals(
                                            "2",
                                            ignoreCase = true
                                        )
                                    ) {                          // coming from lead module
                                        val alertDialogBuilder = AlertDialog.Builder(
                                            this@UpdateProfileActivity
                                        )

                                        // set title
                                        alertDialogBuilder.setTitle("Account Activated Successfully")
                                        // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                                        // set dialog message
                                        alertDialogBuilder
                                            .setMessage("Click Yes to Proceed to Dashboard")
                                            .setCancelable(false)
                                            .setPositiveButton(
                                                "Yes",
                                                object : DialogInterface.OnClickListener {
                                                    override fun onClick(
                                                        dialog: DialogInterface,
                                                        id: Int
                                                    ) {
                                                        // if this button is clicked, close
                                                        // current activity
                                                        // MainActivity.this.finish();

                                                        if (navigationcode.equals("2")) {
                                                            val myIntent = Intent(
                                                                this@UpdateProfileActivity,
                                                                LeadAquistionActivity::class.java
                                                            );
                                                            myIntent.putExtra(
                                                                "navigationcode",
                                                                "1"
                                                            );
                                                            startActivity(myIntent);

                                                        } else if (navigationcode.equals("3")) {                          // coming from Technical Session module
                                                            val myIntent = Intent(
                                                                this@UpdateProfileActivity,
                                                                TrainingActivity::class.java
                                                            )
                                                            myIntent.putExtra("navigationcode", "1")
                                                            startActivity(myIntent)
                                                            overridePendingTransition(
                                                                R.anim.slidein_right,
                                                                R.anim.slide_out_left
                                                            )
                                                        }
                                                        // coming from lead module
                                                        /* Intent myIntent = new Intent(LoginActivity.this, LeadAquistionActivity.class);
                                                    myIntent.putExtra("navigationcode", "1");
                                                    startActivity(myIntent);*/
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
                                    } else if (navigationcode.equals(
                                            "3",
                                            ignoreCase = true
                                        )
                                    ) {                          // coming from Technical Session module
                                        val alertDialogBuilder = AlertDialog.Builder(
                                            this@UpdateProfileActivity
                                        )

                                        // set title
                                        alertDialogBuilder.setTitle("Account Activated Successfully")
                                        // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                                        // set dialog message
                                        alertDialogBuilder
                                            .setMessage("Click Yes to Proceed Dashboard")
                                            .setCancelable(false)
                                            .setPositiveButton(
                                                "Yes",
                                                object : DialogInterface.OnClickListener {
                                                    override fun onClick(
                                                        dialog: DialogInterface,
                                                        id: Int
                                                    ) {
                                                        // if this button is clicked, close
                                                        // current activity
                                                        // MainActivity.this.finish();

                                                        if (navigationcode.equals("2")) {
                                                            val myIntent = Intent(
                                                                this@UpdateProfileActivity,
                                                                LeadAquistionActivity::class.java
                                                            );
                                                            myIntent.putExtra(
                                                                "navigationcode",
                                                                "1"
                                                            );
                                                            startActivity(myIntent);

                                                        } else if (navigationcode.equals("3")) {                          // coming from Technical Session module
                                                            val myIntent = Intent(
                                                                this@UpdateProfileActivity,
                                                                TrainingActivity::class.java
                                                            )
                                                            myIntent.putExtra("navigationcode", "1")
                                                            startActivity(myIntent)
                                                            overridePendingTransition(
                                                                R.anim.slidein_right,
                                                                R.anim.slide_out_left
                                                            )
                                                        }
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
                                } catch (je: JSONException) {
                                    Log.d("jsonexception", je.toString())
                                }
                            } else if (response.body().getmResultStatus()
                                    .equals("2", ignoreCase = true)

                            ) {   //  Already Existed
                            } else if (response.body().getmResultStatus()
                                    .equals("0", ignoreCase = true)

                            ) {         // barcode found
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<Commonresult>, t: Throwable) {
                    if (t is SocketTimeoutException) {
                        // "Connection Timeout";
                        //mRetryLayout.setVisibility(View.VISIBLE);
                        /* mRetryButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            getallleads();
                        }
                    });*/
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Connection Timeout Please Retry",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (t is IOException) {
                        // "Timeout";
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Timeout Please Retry",
                            Toast.LENGTH_SHORT
                        ).show()
                        //mRetryLayout.setVisibility(View.VISIBLE);
                        // mRetryButton.setOnClickListener(new View.OnClickListener()
                        /* {
                        @Override
                        public void onClick(View v)
                        {
                            getallleads();
                        }
                    });*/
                    } else {
                        //Call was cancelled by user
                        if (call.isCanceled()) {
                            Toast.makeText(
                                this@UpdateProfileActivity,
                                "Call was cancelled forcefully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            //Generic error handling
                            Toast.makeText(
                                this@UpdateProfileActivity,
                                "Some Network Issue",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        } catch (je: JSONException) {
        }
    }
}