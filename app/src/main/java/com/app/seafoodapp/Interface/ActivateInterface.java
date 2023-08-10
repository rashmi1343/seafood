package com.app.seafoodapp.Interface;

import com.app.seafoodapp.model.Commonresult;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ActivateInterface {

    @Headers("Content-Type:application/json")
    @POST("user")
    Call<Commonresult> Activatedelegate(@Body JsonObject jactivateobj);

    @Headers("Content-Type:application/json")
    @POST("user")
    Call<Commonresult> Updteactivatedelegate(@Body JsonObject jupdtactobj);




}
