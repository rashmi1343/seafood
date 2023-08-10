package com.app.seafoodapp.Interface;

import com.app.seafoodapp.model.StallModel;
import com.app.seafoodapp.model.StallResult;
import com.google.gson.JsonObject;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetroInterface
{
    @Headers({
            "Content-Type:application/json",
            "authorizationkey:20A0751C-9FEE-47F8-A6A9-335BE39",
            "keypassword:aW5kaWFuc2VhZm9vZEAyMDIwIQ"
    })
    @POST("api.php?requestparm=indianseafood")
    Call<StallResult> getAllData(@Body JsonObject object);
    Call<StallModel> getStallResult(@Body JsonObject object);
}
