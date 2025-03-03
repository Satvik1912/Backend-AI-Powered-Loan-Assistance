package com.cars24.ai_loan_assistance.data.responses;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String name;
    private String phone;
    private String email;
    private String address;
}
