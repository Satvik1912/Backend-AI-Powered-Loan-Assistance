package com.cars24.ai_loan_assistance.data.requests;


import com.cars24.ai_loan_assistance.data.entities.enums.Role;
import lombok.Data;

import static com.cars24.ai_loan_assistance.data.entities.enums.Role.USER;


@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role = USER;
}
