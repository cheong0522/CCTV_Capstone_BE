package com.simcheong.cctv.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    @Autowired
    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/oauth")
    public String handleKakaoOauth(@RequestParam(required = false) String code) throws IOException {
        if (code == null) {
            // Redirect to Kakao authorization URL
            return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoService.getClientId() + "&redirect_uri=" + kakaoService.getRedirectUri() + "&scope=talk_message,friends";
        } else {
            // Handle OAuth callback
            String accessToken = kakaoService.getAccessToken(code);
            return "Access Token: " + accessToken;
        }
    }

    @GetMapping("/api/kakao/friends")
    public String getFriends(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
        String accessToken = authorizationHeader.substring(7);
        return kakaoService.getFriends(accessToken);
    }

    @PostMapping("/api/kakao/send")
    public void sendKakaoMessageToMe(@RequestBody SendMessageRequest sendMessageRequest) throws IOException {
        kakaoService.sendMessageToMe(sendMessageRequest.getAccessToken(), sendMessageRequest.getBody());
    }

    public static class SendMessageRequest {
        private String accessToken;
        private String body;

        // Getters and setters
        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
