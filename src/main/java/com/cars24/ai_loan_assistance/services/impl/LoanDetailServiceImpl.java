package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.LoanDetailEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanDetailRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.LoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanDetailServiceImpl implements LoanDetailService {

    @Autowired
    private LoanDetailRepository loanDetailRepository;

    @Override
    public ResponseEntity<ApiResponse> getLoanDetailsByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(400, "Sorry, There is no such loan Detail.", "InvalidRequest", false, null)
            );
        }

        String details = loans.stream()
                .map(loan -> String.format(
                        "Loan ID: %d  Principal Amount: %.2f  Tenure: %.0f months  Interest Rate: %.2f%%",
                        loan.getLoan().getLoanId(), loan.getPrincipal(), loan.getTenure(), loan.getInterest()))
                .collect(Collectors.joining("\n\n------------------------------------\n\n"));

        return ResponseEntity.ok(new ApiResponse(200, "Here are your loan details.", "LoanDetails", true, details));
    }

    @Override
    public ResponseEntity<ApiResponse> getPrincipalByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(400, "Sorry, No such Data present.", "InvalidRequest", false, null)
            );
        }

        String principalAmounts = loans.stream()
                .map(loan -> String.format("Loan ID: %d - %.2f", loan.getLoan().getLoanId(), loan.getPrincipal()))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(new ApiResponse(200, "Your principal amounts.", "PrincipalDetails", true, principalAmounts));
    }

    @Override
    public ResponseEntity<ApiResponse> getTenureByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(400, "Sorry, No such data.", "InvalidRequest", false, null)
            );
        }

        String tenures = loans.stream()
                .map(loan -> String.format("Loan ID: %d - %.0f months", loan.getLoan().getLoanId(), loan.getTenure()))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(new ApiResponse(200, "Your loan tenures.", "TenureDetails", true, tenures));
    }

    @Override
    public ResponseEntity<ApiResponse> getInterestByEmail(String email) {
        List<LoanDetailEntity> loans = loanDetailRepository.findByLoan_User_Email(email);
        if (loans.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(400, "Sorry, No such data", "InvalidRequest", false, null)
            );
        }

        String interests = loans.stream()
                .map(loan -> String.format("Loan ID: %d - %.2f%% interest", loan.getLoan().getLoanId(), loan.getInterest()))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(new ApiResponse(200, "Your loan interest rates.", "InterestDetails", true, interests));
    }

    @Override
    public ResponseEntity<ApiResponse> getLoanCountByEmail(String email) {
        long count = loanDetailRepository.findByLoan_User_Email(email).size();
        if (count == 0) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(400, "No active loans found.", "LoanCount", false, 0)
            );
        }
        return ResponseEntity.ok(new ApiResponse(200, "You currently have " + count + " active loan(s).", "LoanCount", true, count));
    }
}
