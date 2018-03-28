package com.ilirium.firebase;

import com.ilirium.firebase.dto.PushDataDTO;
import com.ilirium.firebase.dto.PushResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFirebaseCloudMessaging {

    @Headers({"Accept: application/json"})
    @POST("https://fcm.googleapis.com/fcm/send")
    Call<PushResponseDTO> push(@Body PushDataDTO pushData);
}
