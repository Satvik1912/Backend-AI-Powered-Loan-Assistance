package com.cars24.ai_loan_assistance.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ColabApiService {

    private final RestTemplate restTemplate;

    public ColabApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callColabApi() {
        String colabUrl = "https://8112-34-75-141-115.ngrok-free.app/"; // Replace with actual ngrok URL
        return restTemplate.getForObject(colabUrl, String.class);
    }
}

