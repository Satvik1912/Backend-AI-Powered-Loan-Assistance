package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.SalaryDetailsResponse;

public interface UserInformationDao {
    SalaryDetailsResponse getSalaryDetails(String email);
    void updateSalaryDetails(String email, SalaryUpdateRequest request);
    int getCibil(String email);
}
