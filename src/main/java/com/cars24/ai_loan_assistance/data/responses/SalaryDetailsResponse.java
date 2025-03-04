package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.IncomeType;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class SalaryDetailsResponse {
    double salary;
    IncomeType incomeType;
}
