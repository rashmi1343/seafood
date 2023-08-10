package com.app.seafoodapp.util;

import android.util.Log;
import android.widget.Toast;

public class util {


    public static double calculategstprice(double amount, double gstpercentage) {

        double res = 0.0;

        try {
          if (!Double.valueOf(amount).equals("")) {
              res = (amount / 100.0f) * gstpercentage;

          }
      }catch(Exception e) {
          Log.d("Exception_gst",e.toString());
      }

        return res;
    }

}
