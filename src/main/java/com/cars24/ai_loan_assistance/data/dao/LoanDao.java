package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;


import com.cars24.ai_loan_assistance.data.responses.ActiveLoansDetailsResponse;
import com.cars24.ai_loan_assistance.data.responses.ActiveLoansResponse;
import com.cars24.ai_loan_assistance.data.responses.GetLoansResponse;
import org.springframework.data.domain.Page;



public interface LoanDao {
    LoanEntity store(LoanEntity loan);
    LoanEntity getLoan(long loanId);
    Page<LoanEntity> getLoans(int page, int limit);
    ActiveLoansResponse getActiveLoans(long userId);
    GetLoansResponse getLoansByUser(long userId);
    ActiveLoansDetailsResponse getActiveLoansDetails(long userId, Long additional);
}
