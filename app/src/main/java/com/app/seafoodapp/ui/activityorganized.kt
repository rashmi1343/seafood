package com.app.seafoodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.app.seafoodapp.R


class activityorganized : AppCompatActivity() {
    private var closeicon: ImageView? = null
    private var backicon: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activityorganized)
        backicon = findViewById(R.id.backicon)
        closeicon = findViewById(R.id.closeicon)
        backicon!!.setOnClickListener(View.OnClickListener { // startActivity(new Intent(MainActivity.this,DashboardActivity.class));
            val intent = Intent(this@activityorganized, DashBoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        closeicon!!.setOnClickListener(View.OnClickListener { // startActivity(new Intent(MainActivity.this,DashboardActivity.class));
            val intent = Intent(this@activityorganized, DashBoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }
}