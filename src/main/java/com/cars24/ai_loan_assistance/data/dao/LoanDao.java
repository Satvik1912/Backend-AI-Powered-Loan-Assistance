package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;

import com.cars24.ai_loan_assistance.data.responses.ActiveLoansResponse;
import com.cars24.ai_loan_assistance.data.responses.GetLoansResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanDao {
    LoanEntity store(LoanEntity loan);
    LoanEntity getLoan(long loan_id);
    Page<LoanEntity> getLoans(int page, int limit);
    ActiveLoansResponse getActiveLoans(String email);
    GetLoansResponse getLoansByUser(String email);
}
