package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.responses.RespCollectionResponse;

public interface ResponseService {
    RespCollectionResponse getResponseByPromptId(String promptId);
}

