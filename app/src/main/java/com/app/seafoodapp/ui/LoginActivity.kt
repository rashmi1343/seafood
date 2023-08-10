package com.app.seafoodapp.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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

class LoginActivity : AppCompatActivity() {
    private val et_delegatenameact: EditText? = null
    private val et_email_delegate: EditText? = null
    private val et_mobile_delegate: EditText? = null
    private val btnverify: Button? = null
    private var buttonscandelegatecode: Button? = null
    private val cdlogin: CardView? = null
    private var mClose: ImageView? = null
    private var mBack: ImageView? = null
    private val llmainlayout: LinearLayout? = null
    var navigationcode = "null"
    var strdelegatedetails: String? = null
    var objdelegatedetail: JSONObject? = null
    private var back_profile_update: ImageView? = null
    private var close_profile_update: ImageView? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_login);
        setContentView(R.layout.login_profile_activation)
        if (getIntent().getStringExtra("navigationcode") != null) {                       //  coming from lead -2  //coming from technical session -3
            navigationcode = getIntent().getStringExtra("navigationcode").toString()
        }


        /* if(getIntent().getStringExtra("delegatedetail") != null) {
            strdelegatedetails =  getIntent().getStringExtra("delegatedetail");
        }*/
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
        // llmainlayout = findViewById(R.id.llmainlayoutdelegate);
        // et_delegatenameact = findViewById(R.id.et_delegatenameact);
        // et_mobile_delegate = findViewById(R.id.et_mobile_delegate);
        // et_email_delegate = findViewById(R.id.et_email_delegate);
        //  btnverify = findViewById(R.id.btnverify);
        mClose = findViewById<ImageView>(R.id.close_training)
        mBack = findViewById<ImageView>(R.id.back_training)
        buttonscandelegatecode = findViewById<Button>(R.id.buttonscandelegatecode)
        buttonscandelegatecode!!.setOnClickListener {
            if (navigationcode.equals("2", ignoreCase = true)) {
                val myIntent = Intent(this@LoginActivity, SeafoodScannerActivity::class.java)
                myIntent.putExtra("code", "2")
                startActivity(myIntent)
            } else if (navigationcode.equals("3", ignoreCase = true)) {
                val myIntent = Intent(this@LoginActivity, SeafoodScannerActivity::class.java)
                myIntent.putExtra("code", "3")
                startActivity(myIntent)
            }
        }
        back_profile_update = findViewById<ImageView>(R.id.back_profile_update)
        back_profile_update?.setOnClickListener { val myIntent = Intent(this@LoginActivity, DashBoardActivity::class.java)
            //  myIntent.putExtra("code", "3");
            startActivity(myIntent)  }

        close_profile_update = findViewById<ImageView>(R.id.close_profile_update)
        close_profile_update?.setOnClickListener {
            val myIntent = Intent(this@LoginActivity, DashBoardActivity::class.java)
            //  myIntent.putExtra("code", "3");
            startActivity(myIntent)
        }

        /* btnverify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                // overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
                activateuseraccount(strdelegatedetails);

            }
        });*/if (navigationcode.equals("2", ignoreCase = true) || navigationcode.equals(
                "3",
                ignoreCase = true
            )
        ) {              // for coming from  lead (2)  & technical session (3)

            /* if(strdelegatedetails!=null) {
           //  processdelegateinfo(strdelegatedetails);


         }*/
            /* else {
          //   llmainlayout.setVisibility(View.GONE);
          //   btnverify.setVisibility(View.GONE);
         }*/
        }
    }

    private fun processdelegateinfo(delegatedetail: String) {
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
            et_delegatenameact!!.setText(activatedelegate!!.delegate_name)
            et_mobile_delegate!!.setText(activatedelegate!!.dele_mobile)
            et_email_delegate!!.setText(activatedelegate!!.delegate_email)
            llmainlayout!!.setVisibility(View.VISIBLE)
            btnverify!!.visibility = View.VISIBLE
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
            jactivateobj.addProperty("user_id", objdelegatedetail.delegate_id)
            jactivateobj.addProperty("dele_name", et_delegatenameact!!.getText().toString())
            jactivateobj.addProperty("dele_email", et_email_delegate!!.getText().toString())
            jactivateobj.addProperty("dele_mobile", et_mobile_delegate!!.getText().toString())
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
                            if (response.body().getmResultStatus().equals("1",ignoreCase = true)

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
                                            this@LoginActivity
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

                                                        /* if (navigationcode.equalsIgnoreCase("2")) {

                                                    } else if (navigationcode.equalsIgnoreCase("3")) {                          // coming from Technical Session module
                                                        Intent myIntent = new Intent(LoginActivity.this, TrainingActivity.class);
                                                        myIntent.putExtra("navigationcode", "1");
                                                        startActivity(myIntent);
                                                    }*/
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
                                            this@LoginActivity
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

                                                        /* if (navigationcode.equalsIgnoreCase("2")) {                          // coming from lead module
                                                        Intent myIntent = new Intent(LoginActivity.this, LeadAquistionActivity.class);
                                                        myIntent.putExtra("navigationcode", "1");
                                                        startActivity(myIntent);
                                                    } else if (navigationcode.equalsIgnoreCase("3")) {                          // coming from Technical Session module

                                                    }*/
                                                        /* Intent myIntent = new Intent(LoginActivity.this, TrainingActivity.class);
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
                                    }
                                } catch (je: JSONException) {
                                    Log.d("jsonexception", je.toString())
                                }
                            } else if (response.body().getmResultStatus().equals("2",ignoreCase = true)

                            ) {   //  Already Existed
                            } else if (response.body().getmResultStatus().equals("0",ignoreCase = true)

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
                            this@LoginActivity,
                            "Connection Timeout Please Retry",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (t is IOException) {
                        // "Timeout";
                        Toast.makeText(
                            this@LoginActivity,
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
                                this@LoginActivity,
                                "Call was cancelled forcefully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            //Generic error handling
                            Toast.makeText(
                                this@LoginActivity,
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
    }
}