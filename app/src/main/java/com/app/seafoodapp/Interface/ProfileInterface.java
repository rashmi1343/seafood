package com.app.seafoodapp.Interface;

import com.app.seafoodapp.model.SeminarModel;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ProfileInterface
{
    @Headers("Content-Type:application/json")
    @POST("staff")
    Call<SeminarModel> sendMethod(@Body JsonObject jsonObject);
}
