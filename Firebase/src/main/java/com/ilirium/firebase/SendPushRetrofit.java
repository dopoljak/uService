package com.ilirium.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilirium.firebase.dto.PushDataDTO;
import com.ilirium.firebase.dto.PushResponseDTO;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.io.IOException;

/**
 *
 * @author dpoljak
 */
public class SendPushRetrofit {

    public static void main(String[] args) throws JsonProcessingException {

        String authorization = "key=AIzaSyCGkEAJTvG5ztZg1bj2qeeKHlpwynxGEjY";

        Retrofit retrofit = FirebaseRetrofitProvider.createRetrofit("http://www.google.com", authorization);

        IFirebaseCloudMessaging iFirebaseCloudMessaging = retrofit.create(IFirebaseCloudMessaging.class);

        PushDataDTO pushData = new PushDataDTO.Builder()
                .priority(PushDataDTO.Priority.normal)
                .to("dK4LBBJoOV8:APA91bFQOzub_ai6nXlNW-_bLtPPBSjjoj0rM0srMckhkcBU3dw7tEynO3t8JX8Vlz_SEoqZYAaHwskUVQQsbVBfxmEq_BLkMhsOrWpsYpYnHmkgUXLUkR12PIHOQedQD7B409mykR-o")
                .addData("test1", "test_1_data")
                .build();


        ObjectMapper mapper = new ObjectMapper();

        System.out.println("------------------");
        System.out.println("" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pushData));
        System.out.println("------------------");

        try {

            Response<PushResponseDTO> pushResponseDTO = iFirebaseCloudMessaging.push(pushData).execute();

            System.out.println("------------------");
            System.out.println("" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pushResponseDTO.body()));
            System.out.println("------------------");

            System.out.println("" + pushResponseDTO.code());
            System.out.println("" + pushResponseDTO.isSuccessful());
            System.out.println("" + pushResponseDTO.body());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
