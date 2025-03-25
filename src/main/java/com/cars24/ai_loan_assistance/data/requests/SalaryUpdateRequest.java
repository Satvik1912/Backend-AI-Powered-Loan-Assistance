package com.cars24.ai_loan_assistance.data.requests;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Valid
public class SalaryUpdateRequest {

    @Valid
    @Min(value = 30000, message = "Salary needs to atleast be 30000 INR")
    double salary;
}
