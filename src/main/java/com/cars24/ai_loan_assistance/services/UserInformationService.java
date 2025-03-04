package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserInformationService {
    ResponseEntity<ApiResponse> getSalaryDetails(String email);
    ResponseEntity<ApiResponse> updateSalaryDetails(String email, SalaryUpdateRequest request);
    ResponseEntity<ApiResponse> getCibil(String email);
}
