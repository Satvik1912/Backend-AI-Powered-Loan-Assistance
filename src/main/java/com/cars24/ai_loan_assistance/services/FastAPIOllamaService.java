package com.cars24.ai_loan_assistance.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FastAPIOllamaService {

    private final RestTemplate restTemplate;
    private static final String FASTAPI_URL = "http://localhost:8000/generate-response/"; // Update with actual FastAPI URL

    public FastAPIOllamaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callFastAPI(String userQuery, String userData) {
        log.info("Making FastAPI Call with user_data: {} and user_query: {}",userData, userQuery);
        // Create request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_query", userQuery);
        requestBody.put("user_data", userData);

        // Create an HTTP entity with headers and body
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make a POST request to FastAPI endpoint
        return restTemplate.postForObject(FASTAPI_URL, requestEntity, String.class);
    }
}
