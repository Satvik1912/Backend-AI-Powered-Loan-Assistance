package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;

import java.util.List;

public interface LoanDao {
    LoanEntity store(LoanEntity loan);

//    List<LoanEntity> searchByField(String fieldName, String fieldValue, int page, int size);
}
