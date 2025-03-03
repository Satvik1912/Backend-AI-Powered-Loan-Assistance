package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface LoanDetailService {
    ResponseEntity<ApiResponse> getLoanDetailsByEmail(String email);
    ResponseEntity<ApiResponse>  getPrincipalByEmail(String email);
    ResponseEntity<ApiResponse>  getTenureByEmail(String email);
    ResponseEntity<ApiResponse>  getInterestByEmail(String email);
    ResponseEntity<ApiResponse>  getLoanCountByEmail(String email);
}
