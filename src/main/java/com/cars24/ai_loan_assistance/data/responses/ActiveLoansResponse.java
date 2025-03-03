package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ActiveLoansResponse {
    int numberOfLoans;
    private List<LoanInfo> loans;
}
