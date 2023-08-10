package com.app.seafoodapp.lib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.app.seafoodapp.Const.ConstApi;
import com.app.seafoodapp.R;

public class usdStallPaymentActivity  extends AppCompatActivity {

    Intent mainIntent;
    WebView webview;
    //String url = "https://www.indianseafoodexpo.com/beta2023/Registration2/overseas_stall_register.php";
    String url = "";
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        //

        if (getIntent().getStringExtra("stallurl") != null) {
            url = getIntent().getStringExtra("stallurl");
        }
        webview.setWebChromeClient(new WebChromeClient() {

        });
        //myWebView.loadUrl("file:///android_asset/HTML/index.html"););
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        webview.loadUrl(url);
    }
}
