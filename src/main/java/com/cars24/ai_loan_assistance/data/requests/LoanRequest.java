package com.cars24.ai_loan_assistance.data.requests;


import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanRequest
{
    @NotNull(message = "Loan amount cannot be null")
    private String email;

    @NotNull(message = "Loan amount cannot be null")
    @Positive(message = "Loan amount must be positive")
    private Double amountLeft;

    @NotNull(message = "Loan status cannot be null")
    private LoanStatus status;

    @NotNull(message = "Loan type cannot be null")
    private LoanType type;

    private LocalDate disbursedDate;

}
