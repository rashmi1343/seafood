package com.app.seafoodapp.Network;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkConnect
{

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
