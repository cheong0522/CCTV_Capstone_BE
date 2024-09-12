package com.simcheong.cctv.predict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PredictController {

    @Autowired
    private RestTemplate restTemplate;

    private final String flaskUrl = "http://flask-app:5000/predict"; // Docker Compose의 서비스 이름과 포트를 사용

    @GetMapping("/predict")
    public ResponseEntity<String> predict(@RequestParam("video_urls") String videoUrls) {
        // Flask 서버로 요청 전송
        String url = flaskUrl + "?video_urls=" + videoUrls;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(response.getBody());
    }
}
