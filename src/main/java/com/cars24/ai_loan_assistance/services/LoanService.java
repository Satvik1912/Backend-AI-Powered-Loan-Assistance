package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.requests.LoanRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface LoanService {
    ResponseEntity<ApiResponse> createLoan(LoanRequest loanRequest);

    ResponseEntity<ApiResponse> searchLoans(String loanFieldName, String loanFieldValue, int page, int size);
}
