package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ChatbotService {
    ResponseEntity<ApiResponse> processQuery(String email, ChatbotIntent intent, String additional);
    ResponseEntity<ApiResponse> processUpdate(String email, ChatbotIntent intent, Map<String, Object> request);
    ResponseEntity<ApiResponse> processCreate(String email, ChatbotIntent intent, Map<String, Object> request);
}
