package com.cars24.ai_loan_assistance.services;

public interface LoanDetailService {
    String getLoanDetailsByEmail(String email);
    String getPrincipalByEmail(String email);
    String getTenureByEmail(String email);
    String getInterestByEmail(String email);
    String getLoanCountByEmail(String email);
}
