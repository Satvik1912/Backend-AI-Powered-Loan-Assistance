package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    ApiResponse registerUser(SignupRequest user);
    ApiResponse login(LoginRequest user);
}
