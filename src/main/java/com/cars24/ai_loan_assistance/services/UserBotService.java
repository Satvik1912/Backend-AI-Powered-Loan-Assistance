package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;

import java.util.Map;

public interface UserBotService {
    UserBotResponse interact(int promptId, long userId, Long additional);
    UserBotResponse update(int promptId, long userId, Map<String, Object> request, Long additional);
    UserBotResponse create(int promptId, long userId, Map<String, Object> request);
}
