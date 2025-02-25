package com.cars24.ai_loan_assistance.data.dao.impl;


import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.exceptions.LoanServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public LoanEntity getLoan(String  loan_id) {
        return loanRepository.findById(loan_id)
                .orElseThrow(()->new LoanServiceException("Loan does not exist"));
    }

    @Override
    public Page<LoanEntity> getLoans(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return loanRepository.findAll(pageable);
    }


}
