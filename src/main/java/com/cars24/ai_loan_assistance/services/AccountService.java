package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<ApiResponse> getUserProfile(String email);
    ResponseEntity<ApiResponse> updateContactInfo(String email, ContactUpdateRequest request);
    ResponseEntity<ApiResponse> getKycDetails(String email);

}
