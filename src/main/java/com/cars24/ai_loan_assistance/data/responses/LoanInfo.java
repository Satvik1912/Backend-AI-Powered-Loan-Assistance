package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanInfo {
    private long loan_id;
    private LocalDate disbursedDate;
    private LoanType type;

    public LoanInfo(long loan_id, LocalDate disbursedDate, LoanType type) {
        this.loan_id = loan_id;
        this.disbursedDate = disbursedDate;
        this.type = type;
    }
}
