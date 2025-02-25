package com.cars24.ai_loan_assistance.data.requests;


import lombok.Data;


@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
