package com.cars24.ai_loan_assistance.data.responses;


import lombok.Data;
import java.util.List;

@Data
public class ActiveLoansResponse {
    int numberOfLoans;
    private List<LoanInfo> loans;
}
