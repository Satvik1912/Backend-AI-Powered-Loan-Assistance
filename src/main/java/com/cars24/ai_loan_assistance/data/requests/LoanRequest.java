package com.cars24.ai_loan_assistance.data.requests;


import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LoanRequest
{
    private Long userId;
    private double loanAmount;
    private LoanType loanType;
    private String status;
    private LocalDate disbursalDate;
}
