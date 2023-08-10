package com.app.seafoodapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.seafoodapp.BarcodeAnalyzer
import com.app.seafoodapp.Const.ConstApi

import com.app.seafoodapp.Interface.ActivateInterface
import com.app.seafoodapp.Interface.LeadInterface
import com.app.seafoodapp.Network.HttpTask
import com.app.seafoodapp.R
import com.app.seafoodapp.databinding.SeafoodScannerActivityBinding
import com.app.seafoodapp.model.*
import com.google.common.util.concurrent.ListenableFuture
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

private const val CAMERA_PERMISSION_REQUEST_CODE = 1

typealias BarcodeListener = (barcode: String) -> Unit
class SeafoodScannerActivity : AppCompatActivity()
// ZXingScannerView.ResultHandler
{
    // private var mScannerView: ZXingScannerView? = null
    var code = "null"
    var mainIntent: Intent? = null

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>


    private var processingBarcode = AtomicBoolean(false)
    private lateinit var cameraExecutor: ExecutorService
    //  private lateinit var scanBarcodeViewModel: ScanBarcodeViewModel


    private lateinit var binding: SeafoodScannerActivityBinding


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.seafood_scanner_activity)
        binding = SeafoodScannerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainIntent = getIntent()
        code = getIntent().getStringExtra("code").toString()


        cameraProviderFuture = ProcessCameraProvider.getInstance(this)


        cameraExecutor = Executors.newSingleThreadExecutor()

        if (hasCameraPermission()) bindCameraUseCases()
        else requestPermission()


        val androidId = Settings.Secure.getString(
            getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
        // String deviceOs = android.os.Build.VERSION;
        Log.d("DeviceId", androidId)
        //OG.D
        val devtype: String = Build.MODEL
        Log.d("devicetype", devtype)
//        mScannerView = ZXingScannerView(this)
//        setContentView(mScannerView)
        val currentapiVersion: Int = Build.VERSION.SDK_INT
        if (currentapiVersion >= Build.VERSION_CODES.M) {

//            if (checkPermission()) {
//                Toast.makeText(
//                    getApplicationContext(),
//                    "Permission already granted",
//                    Toast.LENGTH_LONG
//                ).show()
//            } else {
//                requestPermission()
//            }
        }
    }

    private fun requestPermission() {
        // opening up dialog to ask for camera permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun bindCameraUseCases() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // setting up the preview use case
            val previewUseCase = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }

            val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_CODE_39,
                Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_PDF417
            ).build()

            // getClient() creates a new instance of the MLKit barcode scanner with the specified options
            val scanner = BarcodeScanning.getClient(options)

            // setting up the analysis use case
            val analysisUseCase = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { barcode ->
                        if (processingBarcode.compareAndSet(false, true)) {
                           // searchBarcode(barcode)
                            if (code.equals("1", ignoreCase = true)) {    // for lead scanning
                                 sendbarcodebookingnew(barcode)

                            } else if (code.equals("2", ignoreCase = true) || code.equals(
                                    "3",
                                    ignoreCase = true
                                )
                            ) {               // for account activation from lead module  // for account activation from technical session

                                     activateuseraccountnew(barcode)

                            }

                        }
                    })
                }
            // define the actual functionality of our analysis use case
           /* analysisUseCase.setAnalyzer(
                Executors.newSingleThreadExecutor(),
                { imageProxy ->
                    processImageProxy(scanner, imageProxy)
                }
            )*/

            // configure to use the back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Unbind any bound use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to lifecycleOwner
                cameraProvider.bindToLifecycle(this, cameraSelector, previewUseCase, analysisUseCase)
            } catch (e: Exception) {
                Log.e("PreviewUseCase", "Binding failed! :(", e)
            }
        }, ContextCompat.getMainExecutor(this@SeafoodScannerActivity))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {

        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)

                    // `rawValue` is the decoded value of the barcode
                    // barcode?.rawValue?.let { value ->
                    //    binding.bottomText.text =
                    //       getString(R.string.barcode_value, value)
                    //}
                    if (code.equals("1", ignoreCase = true)) {    // for lead scanning

                        //
                        barcode?.rawValue?.let { value ->
                            //    binding.bottomText.text =
                            //       getString(R.string.barcode_value, value)
                            sendbarcodebookingnew(value)
                        }
                    } else if (code.equals("2", ignoreCase = true) || code.equals(
                            "3",
                            ignoreCase = true
                        )
                    ) {               // for account activation from lead module  // for account activation from technical session

                        //activateuseraccount(result);
                        // activateuseraccount(qrcodetosend)
                        barcode?.rawValue?.let { value ->
                            //    binding.bottomText.text =
                            //       getString(R.string.barcode_value, value)
                            activateuseraccountnew(value)
                        }
                    }
                }
                .addOnFailureListener {
                    // This failure will happen if the barcode scanning model
                    // fails to download from Google Play Services

                    Log.e(TAG, it.message.orEmpty())
                }.addOnCompleteListener {
                    // When the image is from CameraX analysis use case, must
                    // call image.close() on received images when finished
                    // using them. Otherwise, new images may not be received
                    // or the camera may stall.

                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }



    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // user granted permissions - we can set up our scanner
            Toast.makeText(
                applicationContext,
                "Permission Granted, Now you can Scan QR & Bar codes",
                Toast.LENGTH_LONG
            ).show()
            bindCameraUseCases()
        } else {
            // user did not grant permissions - we can't use the camera
//            Toast.makeText(
//                this,
//                "Camera permission required",
//                Toast.LENGTH_LONG
//            ).show()
            Toast.makeText(
                applicationContext,
                "Permission Denied, You cannot Scan QR & Bar codes",
                Toast.LENGTH_LONG
            ).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showMessageOKCancel("You need to allow access to both the permissions",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                        arrayOf(Manifest.permission.CAMERA),
                                        CAMERA_PERMISSION_REQUEST_CODE
                                    )
                                }
                            }
                        })
                    return
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            PERMISSION_CAMERA_REQUEST -> if (grantResults.isNotEmpty()) {
//                val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
//                if (cameraAccepted) {
//                    Toast.makeText(
//                        getApplicationContext(),
//                        "Permission Granted, Now you can Scan QR & Bar codes",
//                        Toast.LENGTH_LONG
//                    ).show()
//                } else {
//                    Toast.makeText(
//                        getApplicationContext(),
//                        "Permission Denied, You cannot Scan QR & Bar codes",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                            showMessageOKCancel("You need to allow access to both the permissions",
//                                object : DialogInterface.OnClickListener {
//                                    override fun onClick(dialog: DialogInterface, which: Int) {
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                            requestPermissions(
//                                                arrayOf<String>(Manifest.permission.CAMERA),
//                                                PERMISSION_CAMERA_REQUEST
//                                            )
//                                        }
//                                    }
//                                })
//                            return
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@SeafoodScannerActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
//
//    override fun onResume() {
//        super.onResume()
//        val currentapiVersion: Int = Build.VERSION.SDK_INT
//        if (currentapiVersion >= Build.VERSION_CODES.M) {
//            if (checkPermission()) {
//                if (mScannerView == null) {
//                    mScannerView = ZXingScannerView(this)
//                    setContentView(mScannerView)
//                }
//                mScannerView.setResultHandler(this)
//                mScannerView.startCamera(camId)
//            } else {
//                requestPermission()
//            }
//        }
//    }

//    protected override fun onStop() {
//        super.onStop()
//        mScannerView.stopCamera()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mScannerView.stopCamera()
//        mScannerView = null
//    }

//    fun handleResult(rawResult: Result) {
//        if (rawResult.getText().equalsIgnoreCase("")) {
//            Log.d("TAG", "handleResultempty: ")
//            finish()
//            Toast.makeText(this@SeafoodScannerActivity, "Sorry Empty Bar Code", Toast.LENGTH_SHORT)
//                .show()
//        } else {
//            if (rawResult.getText().toString().length() > 0) {
//                val result: String = rawResult.getText()
//                Log.e("QRCodeScanner", rawResult.getText())
//                Log.e("QRCodeScanner", rawResult.getBarcodeFormat().toString())
//                val barcodetosend: String = rawResult.getText().toString()
//                val splittedcode = barcodetosend.split(";").toTypedArray()
//                val qrcodetosend = splittedcode[0]
//                val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
//                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
//                if (code.equals("1", ignoreCase = true)) {    // for lead scanning
//                    // sendbarcodebooking(result);
//                    sendbarcodebooking(qrcodetosend)
//                } else if (code.equals("2", ignoreCase = true) || code.equals(
//                        "3",
//                        ignoreCase = true
//                    )
//                ) {               // for account activation from lead module  // for account activation from technical session
//
//                    //activateuseraccount(result);
//                    activateuseraccount(qrcodetosend)
//                }
//
//                /* else if(code.equalsIgnoreCase("3")) {
//            activateuseraccount(result);
//        }*/
//
//
//                //   mScannerView.resumeCameraPreview(SeafoodScannerActivity.this);
//
//
//                /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Scan Result");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
//                        startActivity(browserIntent);
//                    }
//                });
//                builder.setMessage(rawResult.getText());
//                AlertDialog alert1 = builder.create();
//                alert1.show();*/
//            }
//        }
//    }

    private fun activateuseraccountnew(barcodetoactivated: String) {
        val androidId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        // String deviceOs = android.os.Build.VERSION;
        Log.d("DeviceId", androidId)
        //OG.D
        val devtype: String = Build.DEVICE
        Log.d("devicetype", devtype)
        val jactivateobj = JsonObject()
        jactivateobj.addProperty("methodname", "appActivationLog")
        jactivateobj.addProperty("bookingbarcode", barcodetoactivated)
        jactivateobj.addProperty("device_id", androidId)
        jactivateobj.addProperty("device_type", "Android$devtype")
        Log.d("acttoserver", jactivateobj.toString())

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
                    if (!eventjsonobj!!.getString("result").isEmpty()) {
                        val jresult = eventjsonobj!!.getString("result")
                        val jresultobj = JSONArray(jresult)
                        val jresultstatus = eventjsonobj!!.getString("resultstatus")
                        val objdelegatedetail: DelegateDetails? = null
                        Log.d("TAG", "onResponse: " + jresultobj)
                        try {
                            if (jresultstatus.equals(
                                    "1",
                                    ignoreCase = true
                                )
                            ) {             // Succesful Activated

                                // try {
                                //  JSONArray jarraylead = new JSONArray(response.body().getResult());
                                /* for (int i = 0; i < jarraylead.length(); i++) {
                                   JSONObject jobjlead = jarraylead.getJSONObject(i);
                                   Gson gsonactivatedelegate = new Gson();
                                   objdelegatedetail = gsonactivatedelegate.fromJson(jobjlead.toString(), DelegateDetails.class);

                                  // userid = objdelegatedetail.getDelegate_id();



                                   Log.d("TAG", "activateddelegate" + objdelegatedetail.toString());
                               }*/


                                //   Intent myIntent = new Intent(SeafoodScannerActivity.this, LoginActivity.class);              // 1 for first time activation for leads & technical session       // 2 after scanning for leads       //3 after scaning for technical session    //
                                val myIntent = Intent(
                                    this@SeafoodScannerActivity,
                                    UpdateProfileActivity::class.java
                                ) // 1 for first time activation for leads & technical session       // 2 after scanning for leads       //3 after scaning for technical session    //
                                myIntent.putExtra("navigationcode", code)
                                myIntent.putExtra(
                                    "delegatedetail",
                                    jresultobj.toString()
                                )
                                startActivity(myIntent)


                                // }catch(JSONException je) {

                                // }
                            } else if (jresultstatus.equals(
                                    "2",
                                    ignoreCase = true
                                )
                            ) {   //  Already Existed
                                val alertDialogBuilder = AlertDialog.Builder(
                                    this@SeafoodScannerActivity
                                )

                                // set title
                                alertDialogBuilder.setTitle("Account Already Activated")
                                // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                                // set dialog message
                                alertDialogBuilder
                                    .setMessage("Click Yes to Proceed Further")
                                    .setCancelable(false)
                                    .setPositiveButton(
                                        "Yes",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(dialog: DialogInterface, id: Int) {
                                                // if this button is clicked, close
                                                // current activity
                                                // MainActivity.this.finish();
                                                dialog.cancel()
                                                //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
                                            }
                                        })

                                // create alert dialog
                                val alertDialog = alertDialogBuilder.create()

                                // show it
                                alertDialog.show()
                            } else if (jresultstatus.equals(
                                    "0",
                                    ignoreCase = true
                                )
                            ) {         // barcode not found
                                val alertDialogBuilder = AlertDialog.Builder(
                                    this@SeafoodScannerActivity
                                )

                                // set title
                                alertDialogBuilder.setTitle("BarCode Not Found")
                                // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                                // set dialog message
                                alertDialogBuilder
                                    .setMessage("Click Yes to Proceed Further")
                                    .setCancelable(false)
                                    .setPositiveButton(
                                        "Yes",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(dialog: DialogInterface, id: Int) {
                                                // if this button is clicked, close
                                                // current activity
                                                // MainActivity.this.finish();
                                                dialog.cancel()
                                                //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
                                            }
                                        })

                                // create alert dialog
                                val alertDialog = alertDialogBuilder.create()

                                // show it
                                alertDialog.show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    } else {
                        Toast.makeText(SeafoodScannerActivity@ this, message, Toast.LENGTH_LONG)
                            .show()
                        finish()
                        //  LoadingScreen.hideLoading()
                        //  callotplatest(edtmobno.text.toString())
                    }
                }



            } catch (e: JSONException) {
                println("Exception caught");
            }


        }.execute(
            "POST",
            ConstApi.LIVE_URL + "api.php?requestparm=user",
            jactivateobj.toString().trim()
        )
    }


    @SuppressLint("HardwareIds")
    private fun activateuseraccount(barcodetoactivated: String) {
        val androidId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        // String deviceOs = android.os.Build.VERSION;
        Log.d("DeviceId", androidId)
        //OG.D
        val devtype: String = Build.DEVICE
        Log.d("devicetype", devtype)
        val jactivateobj = JsonObject()
        jactivateobj.addProperty("methodname", "appActivationLog")
        jactivateobj.addProperty("bookingbarcode", barcodetoactivated)
        jactivateobj.addProperty("device_id", androidId)
        jactivateobj.addProperty("device_type", "Android$devtype")
        Log.d("acttoserver", jactivateobj.toString())
        val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.LIVE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        //Log.d();
        val mactivateinterface: ActivateInterface = mRetrofit.create(ActivateInterface::class.java)
        val activatecalled: Call<Commonresult> = mactivateinterface.Activatedelegate(jactivateobj)
        activatecalled.enqueue(object : Callback<Commonresult> {
            override fun onResponse(call: Call<Commonresult>, response: Response<Commonresult>) {
                if (response.isSuccessful()) {
                    val objdelegatedetail: DelegateDetails? = null
                    Log.d("TAG", "onResponse: " + response.body().toString())
                    try {
                        if (response.body().getmResultStatus().equals("1", ignoreCase = true)

                        ) {             // Succesful Activated

                            // try {
                            //  JSONArray jarraylead = new JSONArray(response.body().getResult());
                            /* for (int i = 0; i < jarraylead.length(); i++) {
                                   JSONObject jobjlead = jarraylead.getJSONObject(i);
                                   Gson gsonactivatedelegate = new Gson();
                                   objdelegatedetail = gsonactivatedelegate.fromJson(jobjlead.toString(), DelegateDetails.class);

                                  // userid = objdelegatedetail.getDelegate_id();



                                   Log.d("TAG", "activateddelegate" + objdelegatedetail.toString());
                               }*/


                            //   Intent myIntent = new Intent(SeafoodScannerActivity.this, LoginActivity.class);              // 1 for first time activation for leads & technical session       // 2 after scanning for leads       //3 after scaning for technical session    //
                            val myIntent = Intent(
                                this@SeafoodScannerActivity,
                                UpdateProfileActivity::class.java
                            ) // 1 for first time activation for leads & technical session       // 2 after scanning for leads       //3 after scaning for technical session    //
                            myIntent.putExtra("navigationcode", code)
                            myIntent.putExtra(
                                "delegatedetail",
                                response.body().result.toString()
                            )
                            startActivity(myIntent)


                            // }catch(JSONException je) {

                            // }
                        } else if (response.body().getmResultStatus().equals("2", ignoreCase = true)

                        ) {   //  Already Existed
                            val alertDialogBuilder = AlertDialog.Builder(
                                this@SeafoodScannerActivity
                            )

                            // set title
                            alertDialogBuilder.setTitle("Account Already Activated")
                            // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                            // set dialog message
                            alertDialogBuilder
                                .setMessage("Click Yes to Proceed Further")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        // if this button is clicked, close
                                        // current activity
                                        // MainActivity.this.finish();
                                        dialog.cancel()
                                        //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
                                    }
                                })

                            // create alert dialog
                            val alertDialog = alertDialogBuilder.create()

                            // show it
                            alertDialog.show()
                        } else if (response.body().getmResultStatus().equals("0", ignoreCase = true)

                        ) {         // barcode not found
                            val alertDialogBuilder = AlertDialog.Builder(
                                this@SeafoodScannerActivity
                            )

                            // set title
                            alertDialogBuilder.setTitle("BarCode Not Found")
                            // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                            // set dialog message
                            alertDialogBuilder
                                .setMessage("Click Yes to Proceed Further")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        // if this button is clicked, close
                                        // current activity
                                        // MainActivity.this.finish();
                                        dialog.cancel()
                                        //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
                                    }
                                })

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
                        this@SeafoodScannerActivity,
                        "Connection Timeout Please Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (t is IOException) {
                    // "Timeout";
                    Toast.makeText(
                        this@SeafoodScannerActivity,
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
                            this@SeafoodScannerActivity,
                            "Call was cancelled forcefully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //Generic error handling
                        Toast.makeText(
                            this@SeafoodScannerActivity,
                            "Some Network Issue",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun sendbarcodebookingnew(bookingbarcode: String) {
        val jbookingbarcode = JsonObject()
        jbookingbarcode.addProperty("methodname", "CreateLead")
        jbookingbarcode.addProperty("bookingbarcode", bookingbarcode)
        jbookingbarcode.addProperty("user_id", "100")
        jbookingbarcode.addProperty("user_type", "1")
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
                    if (!eventjsonobj!!.getString("result").isEmpty()) {



                        val jresult = eventjsonobj!!.getString("result")
                        val jresultobj = JSONArray(jresult)
                        val jresultstatus = eventjsonobj!!.getString("resultstatus")

                        var objleadgson: LeadModel? = null
                        Log.d("TAG", "onResponse: " + jresultobj.toString())

                        try {
                            if (jresultstatus.equals("1", ignoreCase = true))  {
                                //  val jarraylead = JSONArray(response.body().leadModel)
                                for (i in 0 until jresultobj.length()) {
                                    val jobjlead: JSONObject = jresultobj.getJSONObject(i)
                                    val gsonlead = Gson()
                                    objleadgson =
                                        gsonlead.fromJson(jobjlead.toString(), LeadModel::class.java)
                                    Log.d("TAG", "leadmodelgson" + objleadgson.toString())

                                    // objleadgson.
                                }
                                openalertdialogfornotes(
                                    objleadgson!!.leadid.toString(),
                                    objleadgson.leadname,
                                    objleadgson.leademail,
                                    objleadgson.leadmobile
                                )
                            } else if (jresultstatus.equals("0", ignoreCase = true)
                            ) {
                                val alertDialogBuilder = AlertDialog.Builder(
                                    this@SeafoodScannerActivity
                                )

                                // set title
                                alertDialogBuilder.setTitle("Invalid Barcode Scanned")
                                // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                                // set dialog message
                                alertDialogBuilder
                                    .setMessage("Click Yes to Scan Again!")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            // if this button is clicked, close
                                            // current activity
                                            // MainActivity.this.finish();
                                            dialog.cancel()
                                            //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
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



                    } else {

                        val alertDialogBuilder = AlertDialog.Builder(
                            this@SeafoodScannerActivity
                        )

                        // set title
                        alertDialogBuilder.setTitle("BarCode Not Found")
                        // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                        // set dialog message
                        alertDialogBuilder
                            .setMessage("Click Yes to Proceed Further")
                            .setCancelable(false)
                            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, id: Int) {
                                    // if this button is clicked, close
                                    // current activity
                                    // MainActivity.this.finish();

                                    dialog.cancel()
                                finish()
                                //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
                                }
                            })

                        // create alert dialog
                        val alertDialog = alertDialogBuilder.create()

                        // show it
                        alertDialog.show()
                       // finish()
                        //  LoadingScreen.hideLoading()
                        //  callotplatest(edtmobno.text.toString())
                    }
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


    private fun sendbarcodebooking(bookingbarcode: String) {
        val jbookingbarcode = JsonObject()
        jbookingbarcode.addProperty("methodname", "CreateLead")
        jbookingbarcode.addProperty("bookingbarcode", bookingbarcode)
        jbookingbarcode.addProperty("user_id", "100")
        jbookingbarcode.addProperty("user_type", "1")

        val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ConstApi.LIVE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mlead: LeadInterface = mRetrofit.create(LeadInterface::class.java)
        val mcalllead: Call<LeadResult>? = mlead.sendMethod(jbookingbarcode)
        mcalllead!!.enqueue(object : Callback<LeadResult> {
            override fun onResponse(call: Call<LeadResult>, response: Response<LeadResult>) {
                if (response.isSuccessful()) {
                    var objleadgson: LeadModel? = null
                    Log.d("TAG", "onResponse: " + response.body().toString())
                    Log.d("TAG", "ResultLead" + response.body().leadModel)
                    try {
                        if (response.body().getmResultStatus().equals("1", ignoreCase = true)) {
                            val jarraylead = JSONArray(response.body().leadModel)
                            for (i in 0 until jarraylead.length()) {
                                val jobjlead: JSONObject = jarraylead.getJSONObject(i)
                                val gsonlead = Gson()
                                objleadgson =
                                    gsonlead.fromJson(jobjlead.toString(), LeadModel::class.java)
                                Log.d("TAG", "leadmodelgson" + objleadgson.toString())

                                // objleadgson.
                            }
                            openalertdialogfornotes(
                                objleadgson!!.leadid.toString(),
                                objleadgson.leadname,
                                objleadgson.leademail,
                                objleadgson.leadmobile
                            )
                        } else if (response.body().getmResultStatus()
                                .equals("0", ignoreCase = true)
                        ) {
                            val alertDialogBuilder = AlertDialog.Builder(
                                this@SeafoodScannerActivity
                            )

                            // set title
                            alertDialogBuilder.setTitle("Invalid Barcode Scanned")
                            // alertDialogBuilder.setMessage("Invalid Barcode Scanned");
                            // set dialog message
                            alertDialogBuilder
                                .setMessage("Click Yes to Scan Again!")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        // if this button is clicked, close
                                        // current activity
                                        // MainActivity.this.finish();
                                        dialog.cancel()
                                        //mScannerView.resumeCameraPreview(this@SeafoodScannerActivity)
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

            override fun onFailure(call: Call<LeadResult>, t: Throwable) {}
        })
    }

    fun openalertdialogfornotes(
        leadid: String,
        leadname: String?,
        leademail: String?,
        leadmobile: String?
    ) {
        val inflater: LayoutInflater = getLayoutInflater()
        val alertLayout: View = inflater.inflate(R.layout.add_note_lead, null)
        val etUsername: EditText = alertLayout.findViewById<EditText>(R.id.et_leadname)
        val etEmail: EditText = alertLayout.findViewById<EditText>(R.id.et_email)
        val et_mobile: EditText = alertLayout.findViewById<EditText>(R.id.et_mobile)
        val et_note: EditText = alertLayout.findViewById<EditText>(R.id.et_note)
        val tvlead: TextView = alertLayout.findViewById<TextView>(R.id.tvlead)
        val tvemail: TextView = alertLayout.findViewById<TextView>(R.id.tvemail)
        val tvmobile: TextView = alertLayout.findViewById<TextView>(R.id.tvmobile)
        tvlead.setVisibility(View.GONE)
        tvemail.setVisibility(View.GONE)
        tvmobile.setVisibility(View.GONE)
        etUsername.setText(leadname)
        etEmail.setText(leademail)
        et_mobile.setText(leadmobile)
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Add Notes")
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout)
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false)
        alert.setNegativeButton("Submit", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                //   Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                val user: String = etUsername.getText().toString()
                val pass: String = etEmail.getText().toString()
                sendnotetoserver(leadid, et_note.getText().toString())
            }
        })
        alert.setPositiveButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {

                //  Toast.makeText(getBaseContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();
            }
        })
        val dialog = alert.create()
        dialog.show()
    }

    private fun sendnotetoserver(leadid: String, notes: String) {
        try {
            val jbookingbarcode = JsonObject()
            jbookingbarcode.addProperty("methodname", "addNote")
            jbookingbarcode.addProperty("lead_id", leadid)
            jbookingbarcode.addProperty("note_description", notes)


            val mRetrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ConstApi.LIVE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val mlead: LeadInterface = mRetrofit.create(LeadInterface::class.java)
            val mcalllead: Call<Commonresult>? = mlead.sendnotes(jbookingbarcode)
            mcalllead!!.enqueue(object : Callback<Commonresult> {
                override fun onResponse(
                    call: Call<Commonresult>,
                    response: Response<Commonresult>
                ) {
                    if (response.isSuccessful()) {
                        val objallleadgson: GetAllLeadModel? = null
                        Log.d("TAG", "onResponse: " + response.body().toString())
                        // Log.d("TAG", "ResultLead" + response.body());
                        try {
                            //   JSONArray jarraylead = new JSONArray(response.body().getResult());
                            /* for (int i = 0; i < jarraylead.length(); i++) {
                               JSONObject jobjlead = jarraylead.getJSONObject(i);
                               Gson gsonlead = new Gson();
                               objallleadgson = gsonlead.fromJson(jobjlead.toString(), GetAllLeadModel.class);

                               Log.d("TAG", "leadmodelgson" + objallleadgson.toString());

                               // objleadgson.



                           }*/
                            val myIntent = Intent(
                                this@SeafoodScannerActivity,
                                LeadAquistionActivity::class.java
                            )
                            myIntent.putExtra("navigationcode", "2")
                            myIntent.putExtra("leadlist", response.body().result)
                            startActivity(myIntent)
                            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)


                            //openalertdialogfornotes(objleadgson.getLeadid(), objleadgson.getLeadname(), objleadgson.getLeademail(), objleadgson.getLeadmobile());
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<Commonresult>, t: Throwable) {}
            })
        } catch (e: Exception) {
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 1
        private const val camId = Camera.CameraInfo.CAMERA_FACING_BACK
        private val TAG = SeafoodScannerActivity::class.java.simpleName
        private const val PERMISSION_CAMERA_REQUEST = 1
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10

    }
}