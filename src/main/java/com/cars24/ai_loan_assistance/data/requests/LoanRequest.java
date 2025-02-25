package com.cars24.ai_loan_assistance.data.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanRequest
{
    private String userId;
    private double loanAmount;
    private String loanType;
    private String status;
    private String disbursalDate;
}
