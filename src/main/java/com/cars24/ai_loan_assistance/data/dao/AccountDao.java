package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.KycResponse;
import com.cars24.ai_loan_assistance.data.responses.UserProfileResponse;
import org.springframework.http.ResponseEntity;

public interface AccountDao {
    UserProfileResponse getUserProfile(String email);
    String updateContactInfo(String email, ContactUpdateRequest request);
    KycResponse getKycDetails(String email);
}
