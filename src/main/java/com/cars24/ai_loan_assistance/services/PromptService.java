package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.responses.NextPromptResponse;

import java.util.List;

public interface PromptService {
    List<NextPromptResponse> getInitialPrompts();
    List<NextPromptResponse> getNextPrompts(String promptId);
}
