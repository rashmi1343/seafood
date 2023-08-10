package com.app.seafoodapp.lib.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.app.seafoodapp.Const.ConstApi;
import com.app.seafoodapp.R;

public class usdPaymentGatewayActivity  extends AppCompatActivity {

    Intent mainIntent;
    WebView webview;
   // String url = "https://www.indianseafoodexpo.com/beta2023/Registration2/overseas_delegate_register.php";
    String url = ConstApi.Live_Web_url + "overseas_delegate_register.php";
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
   //
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
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
    }
}
