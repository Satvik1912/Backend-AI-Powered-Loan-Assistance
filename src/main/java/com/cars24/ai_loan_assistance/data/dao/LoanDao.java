package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import org.springframework.data.domain.Page;

public interface LoanDao {
    LoanEntity store(LoanEntity loan);
    LoanEntity getLoan(String loan_id);
    Page<LoanEntity> getLoans(int page, int limit);
}
