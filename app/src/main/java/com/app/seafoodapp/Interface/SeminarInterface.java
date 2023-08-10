package com.app.seafoodapp.Interface;

import com.app.seafoodapp.model.Commonresult;
import com.app.seafoodapp.model.SeminarModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SeminarInterface
{
    @Headers("Content-Type:application/json")
    @POST("user")
    Call<SeminarModel> sendMethod(@Body JsonObject jsonObject);

    @Headers("Content-Type:application/json")
    @POST("user")
    Call<Commonresult> sendquery(@Body JsonObject jqueryObject);

}
