package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.UserInformationDao;
import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ActiveLoansResponse;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.SalaryDetailsResponse;
import com.cars24.ai_loan_assistance.services.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInformationServiceImpl implements UserInformationService {
    private final UserInformationDao userInformationDao;
    @Override
    public ResponseEntity<ApiResponse> getSalaryDetails(String email) {
        SalaryDetailsResponse data = userInformationDao.getSalaryDetails(email);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "Salary details retrieved successfully",
                "BANK_SERVICE-" + HttpStatus.OK.value(),
                true,
                data);

        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> updateSalaryDetails(String email, SalaryUpdateRequest request) {
        userInformationDao.updateSalaryDetails(email,request);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "Salary details updated successfully",
                "BANK_SERVICE-" + HttpStatus.OK.value(),
                true,
                null);

        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> getCibil(String email) {
        int cibil = userInformationDao.getCibil(email);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "CIBIL score retrieved successfully",
                "BANK_SERVICE-" + HttpStatus.OK.value(),
                true,
                cibil);

        return ResponseEntity.ok().body(apiResponse);
    }
}
