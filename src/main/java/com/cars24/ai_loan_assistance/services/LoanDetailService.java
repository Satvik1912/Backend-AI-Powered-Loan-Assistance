package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface LoanDetailService {
    ResponseEntity<ApiResponse> getActiveLoans(String email);
}
