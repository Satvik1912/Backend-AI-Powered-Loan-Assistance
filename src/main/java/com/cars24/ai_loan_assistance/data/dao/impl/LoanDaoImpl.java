package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.responses.*;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanDaoImpl implements LoanDao {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanEntity store(LoanEntity loan)
    {
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public LoanEntity getLoan(long  loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(()->new NotFoundException("Loan does not exist"));
    }

    @Override
    public Page<LoanEntity> getLoans(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return loanRepository.findAll(pageable);
    }

    @Override
    public ActiveLoansResponse getActiveLoans(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        List<LoanEntity> activeLoans = loanRepository.findByUserIdAndStatus(user.getId(), LoanStatus.DISBURSED);

        // Prepare response
        ActiveLoansResponse response = new ActiveLoansResponse();
        response.setNumberOfLoans(activeLoans.size());

        // Convert each loan into LoanInfo object and store in response
        List<LoanInfo> loanDetails = activeLoans.stream()
                .map(loan -> new LoanInfo(loan.getLoanId(), loan.getDisbursedDate(), loan.getType()))
                .toList();

        response.setLoans(loanDetails);
        return response;
    }

    @Override
    public GetLoansResponse getLoansByUser(long userId) {
        // Fetch user details
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        // Fetch all loans for the user
        List<LoanEntity> allLoans = loanRepository.findByUserId(user.getId());

        // Prepare response
        GetLoansResponse response = new GetLoansResponse();
        response.setNumberOfLoans(allLoans.size());

        // Convert each loan into LoanStatusInfo object
        List<LoanStatusInfo> loanStatusList = allLoans.stream()
                .map(loan -> new LoanStatusInfo(loan.getType(), loan.getStatus()))
                .toList();

        response.setLoans(loanStatusList);

        return response;
    }

    @Override
    public ActiveLoansDetailsResponse getActiveLoansDetails(long userId, Long additional) {
        LoanEntity activeLoanDetail = loanRepository.getLoanDetailsById(userId, additional);


        return ActiveLoansDetailsResponse.builder()
                .loanId(activeLoanDetail.getLoanId())
                .loanStatus(activeLoanDetail.getStatus())
                .loanType(activeLoanDetail.getType())
                .principal(activeLoanDetail.getPrincipal())
                .interest(activeLoanDetail.getInterest())
                .tenure(activeLoanDetail.getTenure())
                .disbursedDate(activeLoanDetail.getDisbursedDate())
                .build();
    }
}
