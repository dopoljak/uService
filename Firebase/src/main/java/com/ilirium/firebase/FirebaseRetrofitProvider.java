package com.ilirium.firebase;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

public class FirebaseRetrofitProvider {


    public static Retrofit createRetrofit(String baseUrl, String authorization) {
        OkHttpClient okHttpClient = createOkHttpClient(authorization);
        Retrofit retrofit = createRetrofit(baseUrl, okHttpClient);
        return retrofit;
    }

    public static Retrofit createRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(JacksonConverterFactory.create()).build();
        return retrofit;
    }

    public static OkHttpClient createOkHttpClient(String authorization) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator((Route route, Response response) -> response.request().newBuilder()
                .header("Authorization", authorization)
                .build()).build();
        return okHttpClient;
    }

    public static ApiError parseError(Retrofit client, retrofit2.Response response) {
        try {
            Converter<ResponseBody, ApiError> converter = client.responseBodyConverter(ApiError.class, new Annotation[0]);
            ApiError error = converter.convert(response.errorBody());
            return error;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class ApiError {

    }


}
