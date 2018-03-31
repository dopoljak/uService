package com.iLirium.uService.restclient;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;

public class RestClientProvider {

    public static Retrofit createRetrofit(String baseUrl, retrofit2.Converter.Factory factory) {
        OkHttpClient okHttpClient = createOkHttpClient();
        Retrofit retrofit = createRetrofit(baseUrl, okHttpClient, factory);
        return retrofit;
    }

    private static Retrofit createRetrofit(String baseUrl, OkHttpClient okHttpClient, retrofit2.Converter.Factory factory) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(factory).build();
        return retrofit;
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator((Route route, Response response) -> response.request().newBuilder()
                //.header("Authorization", authorization)
                .build()).build();
        return okHttpClient;
    }
}
