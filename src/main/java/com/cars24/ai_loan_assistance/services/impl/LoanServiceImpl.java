package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.impl.LoanDaoImpl;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.LoanRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanDaoImpl loanDao;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Override
    public ResponseEntity<ApiResponse> createLoan(LoanRequest loanRequest) {
        try {
            Optional<UserEntity> userExists = userRepository.findById(loanRequest.getUserId());

            if (userExists.isEmpty()) {
                throw new RuntimeException("User does not exist");
            }

            LoanEntity loanStatusEntity = new LoanEntity();
            loanStatusEntity.setUserId(loanRequest.getUserId());
            loanStatusEntity.setLoanType(loanRequest.getLoanType());
            loanStatusEntity.setLoanAmount(loanRequest.getLoanAmount());
            loanStatusEntity.setDisbursalDate(loanRequest.getDisbursalDate());
            loanStatusEntity.setStatus("pending");

            LoanEntity savedLoanStatus = loanDao.store(loanStatusEntity);


            Map<String, Object> responseData = new HashMap<>();
            responseData.put("userId", savedLoanStatus.getUserId());
            responseData.put("loanType", savedLoanStatus.getLoanType());
            responseData.put("loanAmount", savedLoanStatus.getLoanAmount());
            responseData.put("status", savedLoanStatus.getStatus());

            ApiResponse response = new ApiResponse(
                    HttpStatus.CREATED.value(),
                    "Loan status created successfully",
                    "APPUSR-" + HttpStatus.CREATED.value(),
                    true,
                    responseData
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            e.getMessage(),
                            "APPUSR-" + HttpStatus.BAD_REQUEST.value(),
                            false,
                            new HashMap<>()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "An error occurred: " + e.getMessage(),
                            "APPUSR-" + HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            false,
                            new HashMap<>()
                    ));
        }
    }

}
