package com.cars24.ai_loan_assistance.data.dao.impl;


import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanDaoImpl implements LoanDao {

    private final LoanRepository loanRepository;
    private final MongoTemplate mongoTemplate;

    public LoanEntity store(LoanEntity loan)
    {
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public List<LoanEntity> searchByField(String fieldName, String fieldValue, int page, int size) {
        Query query = new Query();

        if ("loanAmount".equals(fieldName)) {
            String trimmedValue = fieldValue.trim();
            try {
                // Allow optional whitespace around hyphen for range
                Pattern rangePattern = Pattern.compile("^(\\s*\\d+(\\.\\d+)?\\s*-\\s*\\d+(\\.\\d+)?\\s*)$");
                Matcher rangeMatcher = rangePattern.matcher(trimmedValue);
                if (rangeMatcher.matches()) {
                    String[] parts = trimmedValue.split("\\s*-\\s*");
                    double lower = Double.parseDouble(parts[0].trim());
                    double upper = Double.parseDouble(parts[1].trim());
                    query.addCriteria(Criteria.where("loanAmount").gte(lower).lte(upper));
                }
                // Check for ">" operator
                else if (trimmedValue.startsWith(">")) {
                    String valueStr = trimmedValue.substring(1).trim();
                    double value = Double.parseDouble(valueStr);
                    query.addCriteria(Criteria.where("loanAmount").gt(value));
                }
                // Check for "<" operator
                else if (trimmedValue.startsWith("<")) {
                    String valueStr = trimmedValue.substring(1).trim();
                    double value = Double.parseDouble(valueStr);
                    query.addCriteria(Criteria.where("loanAmount").lt(value));
                }
                // Exact match
                else {
                    double value = Double.parseDouble(trimmedValue);
                    query.addCriteria(Criteria.where("loanAmount").is(value));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid loanAmount format: " + fieldValue, e);
            }
        } else {
            query.addCriteria(Criteria.where(fieldName).is(fieldValue));
        }

        // Pagination
        query.skip((long) page * size).limit(size);
        return mongoTemplate.find(query, LoanEntity.class);
    }
}
