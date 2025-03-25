package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanInfo {
    private long loanId;
    private LocalDate disbursedDate;
    private LoanType type;

    public LoanInfo(long loan_id, LocalDate disbursedDate, LoanType type) {
        this.loanId = loan_id;
        this.disbursedDate = disbursedDate;
        this.type = type;
    }
}
