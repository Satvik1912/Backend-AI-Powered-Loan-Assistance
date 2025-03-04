package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import lombok.Data;

@Data
public class LoanStatusInfo {
    private LoanType type;
    private LoanStatus status;

    public LoanStatusInfo(LoanType type, LoanStatus status) {
        this.type = type;
        this.status = status;
    }
}
