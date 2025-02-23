package com.cars24.ai_loan_assistance.data.requests;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
