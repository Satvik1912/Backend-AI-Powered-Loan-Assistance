package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;

import java.util.Map;

public interface ChatbotService {
    Object processQuery(long userId, ChatbotIntent intent, Long additional);
    String processUpdate(long userId, ChatbotIntent intent, Map<String, Object> request, Long additional);
    String processCreate(long userId, ChatbotIntent intent, Map<String, Object> request);
}
