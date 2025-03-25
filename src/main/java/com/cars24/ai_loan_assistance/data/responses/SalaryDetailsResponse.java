package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.IncomeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalaryDetailsResponse {
    double salary;
    IncomeType incomeType;
}
