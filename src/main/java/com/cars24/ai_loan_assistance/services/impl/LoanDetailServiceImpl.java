package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.LoanDetailEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanDetailRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.LoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanDetailServiceImpl implements LoanDetailService {

    @Autowired
    private LoanDetailRepository loanDetailRepository;

        @Override
        public ResponseEntity<ApiResponse> getActiveLoans(String email) {
            List<LoanDetailEntity> loanDetails = loanDetailRepository.getLoanDetailsByEmail(email);

            if (loanDetails == null || loanDetails.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse(400, "No active loans found.", "LoanCount", false, null)
                );
            }

            return ResponseEntity.ok(new ApiResponse(200, "Active loans found", "LoanCount", true, loanDetails));
        }

}


