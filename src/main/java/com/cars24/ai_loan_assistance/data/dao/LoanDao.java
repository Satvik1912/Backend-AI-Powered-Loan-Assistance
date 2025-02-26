package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanDao {
    LoanEntity store(LoanEntity loan);
    LoanEntity getLoan(long loan_id);
    Page<LoanEntity> getLoans(int page, int limit);
//    List<LoanEntity> searchByField(String fieldName, String fieldValue, int page, int size);
}
