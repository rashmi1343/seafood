package com.app.seafoodapp.Interface;

import com.app.seafoodapp.model.Commonresult;
import com.app.seafoodapp.model.LeadResult;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LeadInterface {

    @Headers("Content-Type:application/json")
    @POST("user")
    Call<LeadResult> sendMethod(@Body JsonObject jleadmethod);



    @Headers("Content-Type:application/json")
    @POST("user")
    Call<Commonresult> sendnotes(@Body JsonObject jleadnotes);


}
