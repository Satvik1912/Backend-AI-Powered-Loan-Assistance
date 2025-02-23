package com.cars24.ai_loan_assistance.data.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponse {
    private int statusCode;
    private String message;
    private String service;
    private boolean success;
    private Object data;
}
