package com.example.EasyWalletApplication.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
public class External {

    public static <T> ResponseEntity<T> makeCall(String key, Object request, String url, Class<T> response){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",key);
        HttpEntity<?> entity = new HttpEntity<>(request, headers);
        return restTemplate.postForEntity(url, entity, response);
    }
}
