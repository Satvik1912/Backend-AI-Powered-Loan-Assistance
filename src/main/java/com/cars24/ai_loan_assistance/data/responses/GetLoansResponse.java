package com.cars24.ai_loan_assistance.data.responses;


import lombok.Data;

import java.util.List;

@Data
public class GetLoansResponse {
    private int numberOfLoans;
    private List<LoanStatusInfo> loans;
}
