package com.cars24.ai_loan_assistance.data.dao.impl;


import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class LoanDaoImpl implements LoanDao {

    private final LoanRepository loanRepository;
    public LoanEntity store(LoanEntity loan)
    {
        loanRepository.save(loan);
        return loan;
    }
}
