package com.app.seafoodapp.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Const.ConstApi
import com.app.seafoodapp.Interface.LeadInterface
import com.app.seafoodapp.R
import com.app.seafoodapp.LeadAdapter
import com.app.seafoodapp.Network.HttpTask
import com.app.seafoodapp.model.*
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
import java.util.*


class LeadAquistionActivity() : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mBack: ImageView? = null
    private var mClose: ImageView? = null
    private var cdleadaquistion: CardView? = null

    // private val mScannerView: ZXingScannerView? = null
    var navigationcode = "null"

    /// Intent mainIntent;
    var strlead: String? = null
    var fontawesomtypface: Typeface? = null
    private var arrleadlist: ArrayList<GetAllLeadModel> = ArrayList<GetAllLeadModel>()
    private var qrlogo: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private val mNolayout: RelativeLayout? = null
    private var netInfo: NetworkInfo? = null
    private var cm: ConnectivityManager? = null
    private val mRetryLayout: RelativeLayout? = null
    private val mRetryButton: AppCompatButton? = null

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lead_aquistion)


        // mainIntent = getIntent();
        if (getIntent().getStringExtra("navigationcode") != null) {
            navigationcode = getIntent().getStringExtra("navigationcode").toString()
        }
        if (getIntent().getStringExtra("leadlist") != null) {
            strlead = getIntent().getStringExtra("leadlist")
        }
        fontawesomtypface = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf")
        val edtsearchview: EditText = findViewById<View>(R.id.edtsearchview) as EditText
        mProgressBar = findViewById<ProgressBar>(R.id.lead_progress)


        /*ClipDrawable clipDrawable = new ClipDrawable
                (getResources().getDrawable(R.drawable.edit_text_drawable),
                        Gravity.BOTTOM, ClipDrawable.VERTICAL);
        clipDrawable.setLevel(2000);

        editText.setBackground(clipDrawable);*/mBack = findViewById<ImageView>(R.id.backicon)
        mClose = findViewById<ImageView>(R.id.closeicon)
        mBack!!.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        })
        mClose!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                /*   finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right);*/
                val myIntent =
                    Intent(this@LeadAquistionActivity, SeafoodScannerActivity::class.java)
                myIntent.putExtra("code", "1")
                startActivity(myIntent)
            }
        })
        cdleadaquistion = findViewById<View>(R.id.cdleadaquistion) as CardView?
        cdleadaquistion!!.setVisibility(View.GONE)
        qrlogo = findViewById<View>(R.id.qrlogo) as TextView?
        qrlogo!!.setTypeface(fontawesomtypface)
        qrlogo!!.setText("\uf029")
        qrlogo!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val myIntent =
                    Intent(this@LeadAquistionActivity, SeafoodScannerActivity::class.java)
                myIntent.putExtra("code", "1")
                startActivity(myIntent)
            }
        })


        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
        if (navigationcode.equals("2", ignoreCase = true)) {
            processdata(strlead)
        } else {
            getallleadslatest()
        }
        /* else {
            edtsearchview.setVisibility(View.GONE);
        }*/edtsearchview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                Log.d("search string", editable.toString())
                filter(editable.toString())
            }
        })
        if (com.app.seafoodapp.ui.util.isOnline(this@LeadAquistionActivity)) {
            run {
                cm =
                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                netInfo = cm?.getActiveNetworkInfo()
                /*if (netInfo?.getType() ?:  == ConnectivityManager.TYPE_MOBILE || netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                   // getallleads()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            val nc: NetworkCapabilities =
                                cm!!.getNetworkCapabilities(cm!!.getActiveNetwork())!!
                            val down_speed: Int = nc.getLinkDownstreamBandwidthKbps()
                            val up_speed: Int = nc.getLinkUpstreamBandwidthKbps()
                            Log.d(TAG, "DownloadSpeed: " + down_speed + "Upload Speed " + up_speed)
                        }
                    }
                }*/
            }
        } else {
            edtsearchview.setVisibility(View.GONE)
            mProgressBar!!.setVisibility(View.GONE)
            mNolayout!!.setVisibility(View.VISIBLE)
        }
    }

    protected override fun onResume() {
        super.onResume()
        /* ((LeadAdapter) mAdapter).setOnItemClickListener(new LeadAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
*/
    }

    override fun onPause() {
        super.onPause()
    }

    private val dataSet: ArrayList<Any>
        private get() {
            val results: ArrayList<*> = ArrayList<DataObject>()
            for (index in 0..19) {
                val obj = DataObject(
                    "Some Primary Text $index",
                    "Secondary $index"
                )
                results.add(index, obj as Nothing)
            }
            return results as ArrayList<Any>
        }

    private fun showAlert() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("App needs to access the Camera.")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                    finish()
                }
            })
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "ALLOW",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                    ActivityCompat.requestPermissions(
                        this@LeadAquistionActivity, arrayOf(Manifest.permission.CAMERA),
                        CAMERA_PERMISSION_CODE
                    )
                }
            })
        alertDialog.show()
    }

    private fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("App needs to access the Camera.")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                    //finish();
                }
            })
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "SETTINGS",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                    startInstalledAppDetailsActivity(this@LeadAquistionActivity)
                }
            })
        alertDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                var i = 0
                val len = permissions.size
                while (i < len) {
                    val permission = permissions[i]
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        val showRationale: Boolean =
                            ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                        if (showRationale) {
                            showAlert()
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(this@LeadAquistionActivity, ALLOW_KEY, true)
                        }
                    }
                    i++
                }
            }
        }
    }

    private fun openScanner() {
        //  Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //  startActivity(intent);

        //  mScannerView = new  ZXingScannerView(LeadAquistionActivity.this);
        //   setContentView(mScannerView);
    }

    override fun onBackPressed() {
        val myIntent = Intent(this@LeadAquistionActivity, DashBoardActivity::class.java)
        //  myIntent.putExtra("navigationcode", "1");
        startActivity(myIntent)
    }

    /*
    private void sendbarcodebooking(String bookingbarcode) {

        JsonObject jbookingbarcode = new JsonObject();
        jbookingbarcode.addProperty("methodname", "CreateLead");
        jbookingbarcode.addProperty("delegatebarcode", bookingbarcode);
        jbookingbarcode.addProperty("user_Id", "1");
        jbookingbarcode.addProperty("user_type", "100");

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(ConstApi.LIVE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        LeadInterface mlead = mRetrofit.create(LeadInterface.class);
            Call<LeadModel> mcalllead = mlead.sendMethod(jbookingbarcode);
            mcalllead.enqueue(new Callback<LeadModel>() {
                @Override
                public void onResponse(Call<LeadModel> call, Response<LeadModel> response) {

                    if(response.isSuccessful())
                    {
                        Log.d(TAG, "onResponse: " + response.body().toString());
                    }

                }

                @Override
                public void onFailure(Call<LeadModel> call, Throwable t) {

                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });



    }*/
    private fun processdata(leaddata: String?) {
        try {
            val jarraylead = JSONArray(leaddata)
            for (i in 0 until jarraylead.length()) {
                val jobjlead: JSONObject = jarraylead.getJSONObject(i)
                val gsonlead = Gson()
                val objallleadgson: GetAllLeadModel =
                    gsonlead.fromJson(jobjlead.toString(), GetAllLeadModel::class.java)
                Log.d("TAG", "leadmodelgson" + objallleadgson.toString())
                arrleadlist.add(objallleadgson)
            }
            mRecyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView?
            mRecyclerView!!.setHasFixedSize(true)
            mLayoutManager = LinearLayoutManager(this)
            mRecyclerView!!.setLayoutManager(mLayoutManager)
            mAdapter = LeadAdapter(this, arrleadlist)
            mRecyclerView!!.setAdapter(mAdapter)

            // mRecyclerView.setVisibility(View.GONE);
        } catch (je: JSONException) {
            Log.d("Exception", je.toString())
        }
    }

    fun sendnotetoserver(leadid: String?, notes: String?) {
        try {
            val jbookingbarcode = JsonObject()
            jbookingbarcode.addProperty("methodname", "addNote")
            jbookingbarcode.addProperty("lead_id", leadid)
            jbookingbarcode.addProperty("note_description", notes)

            HttpTask {
                ""
                if (it == null) {
                    Log.d("connection error", "Some thing Went Wrong")

                    return@HttpTask
                }
                try {


                    val eventjsonobj = JSONObject(it.toString())
                    Log.d("respond", eventjsonobj.toString())

                    val status = eventjsonobj.getString("status");
                    val message = eventjsonobj.getString("msg");




                    if (status == "200") {

                        val jresult = eventjsonobj!!.getString("result")
                        val jresultobj = JSONArray(jresult)

                     //  val jresultobj = eventjsonobj!!.getJSONArray("result")
                        var objallleadgson: GetAllLeadModel? = null

                        try {
                         //   val jarraylead = JSONArray(jresultobj)
                            arrleadlist = ArrayList<GetAllLeadModel>()
                            for (i in 0 until jresultobj.length()) {
                                val jobjlead: JSONObject = jresultobj.getJSONObject(i)
                                val gsonlead = Gson()
                                objallleadgson = gsonlead.fromJson(
                                    jobjlead.toString(),
                                    GetAllLeadModel::class.java
                                )
                                Log.d("TAG", "leadmodelgson" + objallleadgson.toString())

                                // objleadgson.
                                arrleadlist.add(objallleadgson)
                            }
                            mRecyclerView =
                                findViewById<View>(R.id.my_recycler_view) as RecyclerView?
                            mRecyclerView?.setHasFixedSize(true)
                            mLayoutManager = LinearLayoutManager(this@LeadAquistionActivity)
                            mRecyclerView?.setLayoutManager(mLayoutManager)
                            if (mAdapter != null) {
                                mAdapter = LeadAdapter(this@LeadAquistionActivity, arrleadlist)
                                mRecyclerView?.setAdapter(mAdapter)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(LeadAquistionActivity@ this, message, Toast.LENGTH_LONG)
                            .show()

                    }


                } catch (e: JSONException) {
                    println("Exception caught");
                }


            }.execute(
                "POST",
                ConstApi.LIVE_URL + "api.php?requestparm=user",
                jbookingbarcode.toString().trim()
            )
//            val mRetrofit: Retrofit = Retrofit.Builder()
//                .baseUrl(ConstApi.LIVE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            val mlead: LeadInterface = mRetrofit.create(LeadInterface::class.java)
//            val mcalllead: Call<com.app.seafoodapp.model.Commonresult>? = mlead.sendnotes(jbookingbarcode)
//            if (mcalllead != null) {
//                mcalllead.enqueue(object : Callback<Commonresult> {
//                    override fun onResponse(
//                        call: Call<Commonresult>,
//                        response: Response<Commonresult>
//                    ) {
//                        if (response.isSuccessful()) {
//                            var objallleadgson: GetAllLeadModel? = null
//                            Log.d("TAG", "onResponse: " + response.body().toString())
//                            // Log.d("TAG", "ResultLead" + response.body());
//                            try {
//                                val jarraylead = JSONArray(response.body().result)
//                                arrleadlist = ArrayList<GetAllLeadModel>()
//                                for (i in 0 until jarraylead.length()) {
//                                    val jobjlead: JSONObject = jarraylead.getJSONObject(i)
//                                    val gsonlead = Gson()
//                                    objallleadgson = gsonlead.fromJson(
//                                        jobjlead.toString(),
//                                        GetAllLeadModel::class.java
//                                    )
//                                    Log.d("TAG", "leadmodelgson" + objallleadgson.toString())
//
//                                    // objleadgson.
//                                    arrleadlist.add(objallleadgson)
//                                }
//                                mRecyclerView =
//                                    findViewById<View>(R.id.my_recycler_view) as RecyclerView?
//                                mRecyclerView?.setHasFixedSize(true)
//                                mLayoutManager = LinearLayoutManager(this@LeadAquistionActivity)
//                                mRecyclerView?.setLayoutManager(mLayoutManager)
//                                if (mAdapter != null) {
//                                    mAdapter = LeadAdapter(this@LeadAquistionActivity, arrleadlist)
//                                    mRecyclerView?.setAdapter(mAdapter)
//                                }
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Commonresult>, t: Throwable) {
//                        if (t is SocketTimeoutException) {
//                            // "Connection Timeout";
//                            mRetryLayout?.setVisibility(View.VISIBLE)
//                            mRetryButton?.setOnClickListener(object : View.OnClickListener {
//                                override fun onClick(v: View) {
//                                   // getallleads()
//                                    getallleadslatest()
//                                }
//                            })
//                            Toast.makeText(
//                                this@LeadAquistionActivity,
//                                "Connection Timeout Please Retry",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else if (t is IOException) {
//                            // "Timeout";
//                            Toast.makeText(
//                                this@LeadAquistionActivity,
//                                "Timeout Please Retry",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            mRetryLayout?.setVisibility(View.VISIBLE)
//                            mRetryButton?.setOnClickListener(object : View.OnClickListener {
//                                override fun onClick(v: View) {
//                                 //   getallleads()
//                                    getallleadslatest()
//                                }
//                            })
//                        } else {
//                            //Call was cancelled by user
//                            if (call.isCanceled()) {
//                                Toast.makeText(
//                                    this@LeadAquistionActivity,
//                                    "Call was cancelled forcefully",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else {
//                                //Generic error handling
//                                Toast.makeText(
//                                    this@LeadAquistionActivity,
//                                    "Some Network Issue",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    }
//                })
//            }
        } catch (e: Exception) {
        }
    }

    private fun filter(leadname: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<GetAllLeadModel> = ArrayList<GetAllLeadModel>()

        //looping through existing elements
        for (GetAllLeadModel in arrleadlist) {
            //if the existing elements contains the search input
            if (GetAllLeadModel!!.leadname!!.toLowerCase()
                    .contains(leadname.lowercase(Locale.getDefault()))
            ) {
                //adding the element to filtered list
                filterdNames.add(GetAllLeadModel)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        // mAdapter.filterList(filterdNames);
        //  mAdapter.filterList(filterdNames);
        if (mAdapter != null) {
            mAdapter = LeadAdapter(this, filterdNames)
            mRecyclerView!!.setAdapter(mAdapter)
        }
    }


    private fun getallleadslatest() {
        val jbookingbarcode = JsonObject()
        jbookingbarcode.addProperty("methodname", "leadList")
        // jbookingbarcode.addProperty("bookingbarcode", bookingbarcode);
        jbookingbarcode.addProperty("user_id", com.app.seafoodapp.ui.util.userid)

        Log.d("allleadparam", jbookingbarcode.toString())

        HttpTask {
            ""
            if (it == null) {
                Log.d("connection error", "Some thing Went Wrong")

                return@HttpTask
            }
            try {


                val eventjsonobj = JSONObject(it.toString())
                Log.d("respond", eventjsonobj.toString())

                val status = eventjsonobj.getString("status");
                val message = eventjsonobj.getString("msg");

                if (status == "200") {
                    val jresultstatus = eventjsonobj!!.getString("resultstatus")

                    if (jresultstatus.equals("1", ignoreCase = true)) {
                   if(!eventjsonobj.getString("result").isNullOrEmpty())  {
                       val jresult = eventjsonobj!!.getString("result")
                       val jresultobj = JSONArray(jresult)




                           // val jarraylead = JSONArray(response.body().leadModel)
                           var objallleadgson: GetAllLeadModel? = null
                           for (i in 0 until jresultobj.length()) {
                               val jobjlead: JSONObject = jresultobj.getJSONObject(i)
                               val gsonlead = Gson()
                               objallleadgson = gsonlead.fromJson(
                                   jobjlead.toString(),
                                   GetAllLeadModel::class.java
                               )
                               Log.d("TAG", "leadmodelgson" + objallleadgson.toString())

                               // objleadgson.
                               arrleadlist.add(objallleadgson)
                           }
                           mRecyclerView =
                               findViewById<View>(R.id.my_recycler_view) as RecyclerView?
                           mRecyclerView?.setHasFixedSize(true)
                           mLayoutManager = LinearLayoutManager(this@LeadAquistionActivity)
                           mRecyclerView!!.setLayoutManager(mLayoutManager)
                           mAdapter = LeadAdapter(this@LeadAquistionActivity, arrleadlist)
                           mRecyclerView!!.setAdapter(mAdapter)
                           mProgressBar!!.setVisibility(View.GONE)

                           //  openalertdialogfornotes(objleadgson.getLeadid(), objleadgson.getLeadname(), objleadgson.getLeademail(), objleadgson.getLeadmobile());
                       } else if (jresultstatus.equals("0", ignoreCase = true)) {
                           mProgressBar!!.setVisibility(View.GONE)
                           val alertDialogBuilder = AlertDialog.Builder(
                               this@LeadAquistionActivity
                           )

                           // set title
                           alertDialogBuilder.setTitle("No Leads Found")
                           // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                           // set dialog message
                           alertDialogBuilder
                               .setMessage("Return to Dashboard")
                               .setCancelable(false)
                               .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                   override fun onClick(dialog: DialogInterface, id: Int) {
                                       // if this button is clicked, close
                                       // current activity
                                       // MainActivity.this.finish();
                                       // Intent myIntent = new Intent(LeadAquistionActivity.this, SeafoodScannerActivity.class);
                                       val myIntent = Intent(
                                           this@LeadAquistionActivity,
                                           DashBoardActivity::class.java
                                       )
                                       myIntent.putExtra("code", "1")
                                       startActivity(myIntent)
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

                   }
                   else if (jresultstatus.equals("0", ignoreCase = true)) {
                       mProgressBar!!.setVisibility(View.GONE)
                       val alertDialogBuilder = AlertDialog.Builder(
                           this@LeadAquistionActivity
                       )

                       // set title
                       alertDialogBuilder.setTitle("No Leads Found")
                       // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                       // set dialog message
                       alertDialogBuilder
                           .setMessage("Return to Dashboard")
                           .setCancelable(false)
                           .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                               override fun onClick(dialog: DialogInterface, id: Int) {
                                   // if this button is clicked, close
                                   // current activity
                                   // MainActivity.this.finish();
                                   // Intent myIntent = new Intent(LeadAquistionActivity.this, SeafoodScannerActivity.class);
                                   val myIntent = Intent(
                                       this@LeadAquistionActivity,
                                       DashBoardActivity::class.java
                                   )
                                   myIntent.putExtra("code", "1")
                                   startActivity(myIntent)
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
                } else {
                    Toast.makeText(LeadAquistionActivity@ this, message, Toast.LENGTH_LONG).show()
                    //  LoadingScreen.hideLoading()
                    //  callotplatest(edtmobno.text.toString())
                }


            } catch (e: JSONException) {
                println("Exception caught");
            }


        }.execute(
            "POST",
            ConstApi.LIVE_URL + "api.php?requestparm=user",
            jbookingbarcode.toString().trim()
        )
    }


    private fun getallleads() {
        val jbookingbarcode = JsonObject()
        jbookingbarcode.addProperty("methodname", "leadList")
        // jbookingbarcode.addProperty("bookingbarcode", bookingbarcode);
        jbookingbarcode.addProperty("user_id", com.app.seafoodapp.ui.util.userid)
        //jbookingbarcode.addProperty("user_type", "1");
        val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.LIVE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mlead: LeadInterface = mRetrofit.create(LeadInterface::class.java)
        val mcalllead: Call<LeadResult>? = mlead.sendMethod(jbookingbarcode)
        mcalllead!!.enqueue(object : Callback<LeadResult> {
            override fun onResponse(call: Call<LeadResult>, response: Response<LeadResult>) {
                if (response.isSuccessful()) {
                    var objallleadgson: GetAllLeadModel? = null
                    Log.d("TAG", "onResponse: " + response.body().toString())
                    Log.d("TAG", "ResultLead" + response.body().leadModel)
                    try {
                        if (response.body().getmResultStatus().equals("1", ignoreCase = true)) {
                            val jarraylead = JSONArray(response.body().leadModel)
                            for (i in 0 until jarraylead.length()) {
                                val jobjlead: JSONObject = jarraylead.getJSONObject(i)
                                val gsonlead = Gson()
                                objallleadgson = gsonlead.fromJson(
                                    jobjlead.toString(),
                                    GetAllLeadModel::class.java
                                )
                                Log.d("TAG", "leadmodelgson" + objallleadgson.toString())

                                // objleadgson.
                                arrleadlist.add(objallleadgson)
                            }
                            mRecyclerView =
                                findViewById<View>(R.id.my_recycler_view) as RecyclerView?
                            mRecyclerView?.setHasFixedSize(true)
                            mLayoutManager = LinearLayoutManager(this@LeadAquistionActivity)
                            mRecyclerView!!.setLayoutManager(mLayoutManager)
                            mAdapter = LeadAdapter(this@LeadAquistionActivity, arrleadlist)
                            mRecyclerView!!.setAdapter(mAdapter)
                            mProgressBar!!.setVisibility(View.GONE)

                            //  openalertdialogfornotes(objleadgson.getLeadid(), objleadgson.getLeadname(), objleadgson.getLeademail(), objleadgson.getLeadmobile());
                        } else if (response.body().getmResultStatus()
                                .equals("0", ignoreCase = true)
                        ) {
                            mProgressBar!!.setVisibility(View.GONE)
                            val alertDialogBuilder = AlertDialog.Builder(
                                this@LeadAquistionActivity
                            )

                            // set title
                            alertDialogBuilder.setTitle("No Leads Found")
                            // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                            // set dialog message
                            alertDialogBuilder
                                .setMessage("Return to Dashboard")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        // if this button is clicked, close
                                        // current activity
                                        // MainActivity.this.finish();
                                        // Intent myIntent = new Intent(LeadAquistionActivity.this, SeafoodScannerActivity.class);
                                        val myIntent = Intent(
                                            this@LeadAquistionActivity,
                                            DashBoardActivity::class.java
                                        )
                                        myIntent.putExtra("code", "1")
                                        startActivity(myIntent)
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
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<LeadResult>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    // "Connection Timeout";
                    mRetryLayout!!.setVisibility(View.VISIBLE)
                    mRetryButton!!.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {
                            // getallleads()
                            getallleadslatest()
                        }
                    })
                    Toast.makeText(
                        this@LeadAquistionActivity,
                        "Connection Timeout Please Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (t is IOException) {
                    // "Timeout";
                    Toast.makeText(
                        this@LeadAquistionActivity,
                        "Timeout Please Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                    mRetryLayout!!.setVisibility(View.VISIBLE)
                    mRetryButton!!.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {
                            //  getallleads()
                            getallleadslatest()
                        }
                    })
                } else {
                    //Call was cancelled by user
                    if (call.isCanceled()) {
                        Toast.makeText(
                            this@LeadAquistionActivity,
                            "Call was cancelled forcefully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //Generic error handling
                        Toast.makeText(
                            this@LeadAquistionActivity,
                            "Some Network Issue",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    companion object {
        private val LOG_TAG = "CardViewActivity"
        private val CAMERA_PERMISSION_CODE = 100
        val ALLOW_KEY = "ALLOWED"
        val CAMERA_PREF = "camera_pref"
        private val TAG = LeadAquistionActivity::class.java.simpleName
        fun saveToPreferences(
            context: Context, key: String?,
            allowed: Boolean?
        ) {
            val myPrefs: SharedPreferences =
                context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE)
            val prefsEditor: SharedPreferences.Editor = myPrefs.edit()
            if (allowed != null) {
                prefsEditor.putBoolean(key, allowed)
            }
            prefsEditor.commit()
        }

        fun getFromPref(context: Context, key: String?): Boolean {
            val myPrefs: SharedPreferences =
                context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE)
            return (myPrefs.getBoolean(key, false))
        }

        fun startInstalledAppDetailsActivity(context: Activity?) {
            if (context == null) {
                return
            }
            val i = Intent()
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            i.addCategory(Intent.CATEGORY_DEFAULT)
            i.setData(Uri.parse("package:" + context.getPackageName()))
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            context.startActivity(i)
        }
    }
}