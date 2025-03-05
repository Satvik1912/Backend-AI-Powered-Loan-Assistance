package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BankDetailsService {
    public ResponseEntity<ApiResponse> createBankDetails(String email ,CreateBankDetails createBankDetails);
    public ResponseEntity<ApiResponse> countofbanks(String email);
    public ResponseEntity<ApiResponse> bankfulldetails(String email , long bankid);
    public ResponseEntity<ApiResponse> updatebankdetails(String email, BankDetailsUpdateRequest request);
}
