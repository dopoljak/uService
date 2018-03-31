package com.iLirium.uService.restclient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ISystemService {

    @Headers({"Accept: application/json"})
    @POST("/system")
    Call<String> getSystem();
}
