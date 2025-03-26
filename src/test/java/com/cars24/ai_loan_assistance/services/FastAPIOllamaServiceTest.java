package com.cars24.ai_loan_assistance.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FastAPIOllamaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FastAPIOllamaService fastAPIOllamaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCallFastAPI() {
        // Mock input data
        String userQuery = "What is AI?";
        String userData = "Sample user data";
        String mockResponse = "AI stands for Artificial Intelligence.";

        // Prepare expected request body
        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("user_query", userQuery);
        expectedBody.put("user_data", userData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(expectedBody, headers);

        // Mock the RestTemplate response
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), any()))
                .thenReturn(mockResponse);

        // Call the actual method
        String response = fastAPIOllamaService.callFastAPI(userQuery, userData);

        // Verify the response
        assertEquals(mockResponse, response);

        // Verify that RestTemplate was called once with expected parameters
        verify(restTemplate, times(1)).postForObject(anyString(), any(HttpEntity.class), any());
    }
}
