package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.LoanDetailEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanDetailRepository;
import com.cars24.ai_loan_assistance.services.LoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanDetailServiceImpl implements LoanDetailService {

    @Autowired
    private LoanDetailRepository loanDetailRepository;

    @Override
    public String getLoanDetailsByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) return "No loan details found for your email.** Please check your email or contact support.";

        return "Here are your loan details:**\n\n" + loans.stream()
                .map(loan -> String.format(
                        "Loan ID: %d\n" +
                                "Principal Amount:%.2f\n" +
                                "Tenure:** %.0f months\n" +
                                "Interest Rate: %.2f%%\n" +
                                "------------------------------------",
                        loan.getLoan().getLoanId(), loan.getPrincipal(), loan.getTenure(), loan.getInterest()))
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public String getPrincipalByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) return "No principal amount found.";

        return "Your principal amounts:\n\n" + loans.stream()
                .map(loan -> String.format("Loan ID: %d - %.2f", loan.getLoan().getLoanId(), loan.getPrincipal()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getTenureByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) return "No tenure details found.";

        return "Your loan tenures:\n\n" + loans.stream()
                .map(loan -> String.format("Loan ID: %d - %.0f months", loan.getLoan().getLoanId(), loan.getTenure()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getInterestByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) return "No interest rate found.";

        return "Your loan interest rates:\n\n" + loans.stream()
                .map(loan -> String.format("Loan ID: %d - %.2f%% interest", loan.getLoan().getLoanId(), loan.getInterest()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getLoanCountByEmail(String email) {
        long count = loanDetailRepository.findByLoan_User_Email(email).size();
        return count > 0 ? "You currently have " + count + " active loan(s)." : "No active loans found.";
    }
}
