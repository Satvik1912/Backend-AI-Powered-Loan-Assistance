package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanInfo {
    private LocalDate disbursedDate;
    private LoanType type;

    public LoanInfo(LocalDate disbursedDate, LoanType type) {
        this.disbursedDate = disbursedDate;
        this.type = type;
    }
}
