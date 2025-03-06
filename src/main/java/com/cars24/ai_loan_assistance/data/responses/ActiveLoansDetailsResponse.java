package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActiveLoansDetailsResponse {
    long loanId;
    LoanStatus loanStatus;
    LoanType loanType;
    LocalDate disbursedDate;
    Double principal;
    Double tenure;
    Double interest;
}
