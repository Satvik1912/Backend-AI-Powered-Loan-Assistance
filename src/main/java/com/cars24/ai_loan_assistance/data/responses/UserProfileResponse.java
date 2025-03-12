package com.cars24.ai_loan_assistance.data.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String name;
    private String phone;
    private String email;
    private String address;
}
