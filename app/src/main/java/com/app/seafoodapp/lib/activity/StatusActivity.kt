package com.app.seafoodapp.lib.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.seafoodapp.R
import com.app.seafoodapp.ui.DashBoardActivity

import java.lang.StringBuilder

class   StatusActivity : AppCompatActivity() {
    private var closeicon: ImageView? = null
    private var backicon: ImageView? = null
    var value = ""
    var htmldata: StringBuilder? = null
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_status)
        val mainIntent: Intent = getIntent()
        val tvtranstatus: TextView = findViewById<View>(R.id.tvtransstatus) as TextView

        //TextView tvresponse = (TextView) findViewById(R.id.tvresponse);
        val tvresponse: WebView = findViewById<View>(R.id.tvresponse) as WebView


        /*htmldata = new StringBuilder();
		htmldata = htmldata.append("<!DOCTYPE html><head> <meta http-equiv=Content-Type");
		htmldata = htmldata.append("content=text/html; charset=utf-8><HTML>");
		htmldata = htmldata.append("<head><head></head>");
		htmldata = htmldata.append("<body><table align='center' width='800' border='0' cellspacing='0' cellpadding='12'>");
		htmldata = htmldata.append("<tbody><tr> <td>");
		htmldata = htmldata.append("<p style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#111111; font-weight:normal;>");

		htmldata = htmldata.append("Dear Test</p>");

		htmldata = htmldata.append("<p style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#111111; font-weight:normal;>");
		htmldata = htmldata.append("Thank you for registering in INDIA INTERNATIONAL SEAFOOD SHOW 2020 </p>");
		htmldata = htmldata.append("<p style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#111111; font-weight:normal;>");
		htmldata = htmldata.append("We are pleased to inform you that your payment is approved and your registration as an exhibitor is confirmed. The details of your registration are as below:</p></td> </tr> </tbody></table>");
		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");


		htmldata = htmldata.append( "<table align=center width=800 border=1 cellspacing=0 cellpadding=12><tbody><tr>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style='font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Name</span>");
		htmldata = htmldata.append("</th>");
		htmldata = htmldata.append("<th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>");
		htmldata = htmldata.append("Stall No</span>");
		htmldata = htmldata.append("</th><th align=left style=background-color:#1a82c1;>");
		htmldata = htmldata.append("<span style=font-family:'Arial', 'Helvetica'; font-size:14px; color:#ffffff; font-weight:normal;>Exhibitor Passes</span></th></tr><tr><td><span style=\"font-family:'Arial', 'Helvetica'; font-size:13px;\">SR-2019-10</span></td></tr></tbody></table>");
		*/htmldata = StringBuilder()
        htmldata = htmldata!!.append("<!DOCTYPE html><head> <meta http-equiv=Content-Type")
        htmldata = htmldata!!.append("content=text/html; charset=utf-8><HTML>")
        htmldata = htmldata!!.append(mainIntent.getStringExtra("responsedata"))
        htmldata = htmldata!!.append("</html>")
        /*htmldata = htmldata.append("</HTML>");*/tvresponse.getSettings()
            .setJavaScriptEnabled(true)
        tvresponse.getSettings().setLoadWithOverviewMode(true)
        tvresponse.getSettings().setUseWideViewPort(true)
        tvresponse.getSettings().setSupportZoom(true)
        tvresponse.getSettings().setBuiltInZoomControls(true)
        tvresponse.getSettings().setDisplayZoomControls(false)
        tvresponse.loadDataWithBaseURL(
            null,
            htmldata.toString(),
            "text/html; charset=utf-8",
            "UTF-8",
            ""
        )


        //tvresponse.setText(Html.fromHtml(mainIntent.getStringExtra("responsedata")));
        //	mainIntent = getIntent();
        value = getIntent().getStringExtra("value").toString() // 1 for stall 2 for delegate
        if (value.equals("1", ignoreCase = true)) {   // FOR STALL hiding the status
            tvtranstatus.setVisibility(View.GONE)
        } else {                                            // for delegate showing transaction status
            tvtranstatus.setText(mainIntent.getStringExtra("transStatus"))
        }
        backicon = findViewById<ImageView>(R.id.backicon)
        closeicon = findViewById<ImageView>(R.id.closeicon)
        backicon!!.setOnClickListener { // startActivity(new Intent(MainActivity.this,DashboardActivity.class));
            val intent = Intent(this@StatusActivity, DashBoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        closeicon!!.setOnClickListener { // startActivity(new Intent(MainActivity.this,DashboardActivity.class));
            val intent = Intent(this@StatusActivity, DashBoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        val btnreturntodashboard = findViewById<View>(R.id.btnreturntodashboard) as Button
        btnreturntodashboard.setOnClickListener {
            startActivity(
                Intent(
                    this@StatusActivity,
                    DashBoardActivity::class.java
                )
            )
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, "Toast: $msg", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {

        /*  if (value=="1")
           {
               startActivity(new Intent(StatusActivity.this, StallActivity.class));
           }
           else
           {
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
           }*/
        val intent = Intent(getApplicationContext(), DashBoardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}