package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;

import java.util.Map;

public interface UserBotService {
    UserBotResponse interact(int prompt_id, String email, Long additional);
    UserBotResponse update(int prompt_id, String email, Map<String, Object> request, Long additional);
    UserBotResponse create(int prompt_id, String email, Map<String, Object> request);
}
