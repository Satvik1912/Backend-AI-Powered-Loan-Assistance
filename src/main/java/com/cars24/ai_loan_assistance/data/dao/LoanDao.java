package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;

public interface LoanDao {
    LoanEntity store(LoanEntity loan);
}
