package com.simcheong.cctv.kakao;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KakaoService {
    private final String clientId = "YOUR_CLIENT_ID";
    private final String clientSecret = "YOUR_CLIENT_SECRET";
    private final String redirectUri = "YOUR_REDIRECT_URI";
    private final OkHttpClient httpClient = new OkHttpClient();

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getAccessToken(String code) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", clientId)
                .add("redirect_uri", redirectUri)
                .add("code", code)
                .add("client_secret", clientSecret)
                .build();

        Request request = new Request.Builder()
                .url("https://kauth.kakao.com/oauth/token")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public String getFriends(String accessToken) throws IOException {
        Request request = new Request.Builder()
                .url("https://kapi.kakao.com/v1/api/talk/friends")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public void sendMessageToMe(String accessToken, String body) throws IOException {
        String templateObject = "{ \"object_type\": \"text\", \"text\": \"" + body + "\", \"link\": { \"web_url\": \"https://developers.kakao.com\", \"mobile_web_url\": \"https://developers.kakao.com\" }, \"button_title\": \"바로 확인\" }";

        RequestBody formBody = new FormBody.Builder()
                .add("template_object", templateObject)
                .build();

        Request request = new Request.Builder()
                .url("https://kapi.kakao.com/v2/api/talk/memo/default/send")
                .addHeader("Authorization", "Bearer " + accessToken)
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }
    }
}
