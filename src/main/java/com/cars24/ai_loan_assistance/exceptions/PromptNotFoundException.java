package com.cars24.ai_loan_assistance.exceptions;

public class PromptNotFoundException extends RuntimeException{
    public PromptNotFoundException(String message) {
        super(message);
    }
}
