package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestJsonService {
    private final String CLIENT_ID = "37831675488-lvf4hlqr6auo5kvocpiifh70dap2r4uo.apps.googleusercontent.com";
    private final String CLIENT_SECRET= "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";
    private final String REDIRECT_URI= "http://localhost:8080/test";
    private final String GRANT_TYPE= "authorization_code";
    private final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    public String getAccessTokenJsonData(String code){
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("grant_type", GRANT_TYPE);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(TOKEN_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return "error";
    }
}
