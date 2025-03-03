package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.AccountDao;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.KycResponse;
import com.cars24.ai_loan_assistance.data.responses.UserProfileResponse;
import com.cars24.ai_loan_assistance.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Override
    public ResponseEntity<ApiResponse> getUserProfile(String email) {
        UserProfileResponse data = accountDao.getUserProfile(email);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "User Profile retrieved successfully",
                "ACCOUNT_SERVICE-" + HttpStatus.OK.value(),
                true,
                data);


        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> updateContactInfo(String email, ContactUpdateRequest request) {
        accountDao.updateContactInfo(email,request);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "User Contact updated successfully",
                "ACCOUNT_SERVICE-" + HttpStatus.OK.value(),
                true,
                null);


        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> getKycDetails(String email) {
        KycResponse data = accountDao.getKycDetails(email);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "User KYC retrieved successfully",
                "ACCOUNT_SERVICE-" + HttpStatus.OK.value(),
                true,
                data);


        return ResponseEntity.ok().body(apiResponse);
    }
}
