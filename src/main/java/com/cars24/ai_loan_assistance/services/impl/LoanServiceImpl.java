package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

//    private final LoanDaoImpl loanDao;

    private final LoanDao loanDao;
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
            loanStatusEntity.setType(loanRequest.getType());
            loanStatusEntity.setLoanAmount(loanRequest.getLoanAmount());
            loanStatusEntity.setDisbursedDate(loanRequest.getDisbursedDate());
            loanStatusEntity.setStatus(LoanStatus.valueOf("pending"));

            LoanEntity savedLoanStatus = loanDao.store(loanStatusEntity);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("userId", savedLoanStatus.getUserId());
            responseData.put("loanType", savedLoanStatus.getType());
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

//    @Override
//    public ResponseEntity<ApiResponse> searchLoans(String fieldName, String fieldValue, int page, int size) {
//        try {
//            List<LoanEntity> loansExists = loanDao.searchByField(fieldName, fieldValue, 0, 1);
//
//            if (loansExists.isEmpty()) {
//                throw new RuntimeException("No loan records found for " + fieldName + " = " + fieldValue);
//            }
//            List<LoanEntity> loans = loanDao.searchByField(fieldName, fieldValue, page, size);
//            List<Map<String, Object>> responseData = loans.stream().map(loan -> {
//                Map<String, Object> loanData = new HashMap<>();
//                loanData.put("loanId", loan.getLId());
//                loanData.put("userId", loan.getUserId());
//                loanData.put("loanType", loan.getLoanType());
//                loanData.put("loanAmount", loan.getLoanAmount());
//                loanData.put("disbursalDate", loan.getDisbursalDate());
//                loanData.put("status", loan.getStatus());
//                return loanData;
//            }).collect(Collectors.toList());
//
//            ApiResponse response = new ApiResponse(
//                    HttpStatus.OK.value(),
//                    "Loan records retrieved successfully",
//                    "APPUSR-" + HttpStatus.OK.value(),
//                    true,
//                    responseData
//            );
//
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponse(
//                            HttpStatus.NOT_FOUND.value(),
//                            e.getMessage(),
//                            "APPUSR-" + HttpStatus.NOT_FOUND.value(),
//                            false,
//                            new HashMap<>()
//                    ));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(
//                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                            "An error occurred: " + e.getMessage(),
//                            "APPUSR-" + HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                            false,
//                            new HashMap<>()
//                    ));
//        }
//    }
}
