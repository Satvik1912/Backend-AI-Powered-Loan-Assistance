package com.cars24.ai_loan_assistance.data.responses;

import lombok.Data;

@Data
public class NextPromptResponse {
    private String prompt_id;
    private String text;
    private String category;
}

