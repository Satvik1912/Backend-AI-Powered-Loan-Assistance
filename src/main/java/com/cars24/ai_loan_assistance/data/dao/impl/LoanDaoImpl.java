package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.responses.ActiveLoansResponse;
import com.cars24.ai_loan_assistance.data.responses.GetLoansResponse;
import com.cars24.ai_loan_assistance.data.responses.LoanInfo;
import com.cars24.ai_loan_assistance.data.responses.LoanStatusInfo;
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

//    @Override
//    public List<LoanEntity> searchByField(String fieldName, String fieldValue, int page, int size) {
//        Query query = new Query();
//
//        if ("loanAmount".equals(fieldName)) {
//            String trimmedValue = fieldValue.trim();
//            try {
//                // Allow optional whitespace around hyphen for range
//                Pattern rangePattern = Pattern.compile("^(\\s*\\d+(\\.\\d+)?\\s*-\\s*\\d+(\\.\\d+)?\\s*)$");
//                Matcher rangeMatcher = rangePattern.matcher(trimmedValue);
//                if (rangeMatcher.matches()) {
//                    String[] parts = trimmedValue.split("\\s*-\\s*");
//                    double lower = Double.parseDouble(parts[0].trim());
//                    double upper = Double.parseDouble(parts[1].trim());
//                    query.addCriteria(Criteria.where("loanAmount").gte(lower).lte(upper));
//                }
//                // Check for ">" operator
//                else if (trimmedValue.startsWith(">")) {
//                    String valueStr = trimmedValue.substring(1).trim();
//                    double value = Double.parseDouble(valueStr);
//                    query.addCriteria(Criteria.where("loanAmount").gt(value));
//                }
//                // Check for "<" operator
//                else if (trimmedValue.startsWith("<")) {
//                    String valueStr = trimmedValue.substring(1).trim();
//                    double value = Double.parseDouble(valueStr);
//                    query.addCriteria(Criteria.where("loanAmount").lt(value));
//                }
//                // Exact match
//                else {
//                    double value = Double.parseDouble(trimmedValue);
//                    query.addCriteria(Criteria.where("loanAmount").is(value));
//                }
//            } catch (NumberFormatException e) {
//                throw new IllegalArgumentException("Invalid loanAmount format: " + fieldValue, e);
//            }
//        } else {
//            query.addCriteria(Criteria.where(fieldName).is(fieldValue));
//        }
//
//        // Pagination
//        query.skip((long) page * size).limit(size);
//        return mongoTemplate.find(query, LoanEntity.class);
//    }

    @Override
    public LoanEntity getLoan(long  loan_id) {
        return loanRepository.findById(loan_id)
                .orElseThrow(()->new NotFoundException("Loan does not exist"));
    }

    @Override
    public Page<LoanEntity> getLoans(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return loanRepository.findAll(pageable);
    }

    @Override
    public ActiveLoansResponse getActiveLoans(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        // Fetch active loans (only those with status DISBURSED)
        List<LoanEntity> activeLoans = loanRepository.findByUserIdAndStatus(user.getId(), LoanStatus.DISBURSED);

        // Prepare response
        ActiveLoansResponse response = new ActiveLoansResponse();
        response.setNumberOfLoans(activeLoans.size());

        // Convert each loan into LoanInfo object and store in response
        List<LoanInfo> loanDetails = activeLoans.stream()
                .map(loan -> new LoanInfo(loan.getDisbursedDate(), loan.getType()))
                .toList();

        response.setLoans(loanDetails);
        return response;
    }

    @Override
    public GetLoansResponse getLoansByUser(String email) {
        // Fetch user details
        UserEntity user = userRepository.findByEmail(email)
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
}
