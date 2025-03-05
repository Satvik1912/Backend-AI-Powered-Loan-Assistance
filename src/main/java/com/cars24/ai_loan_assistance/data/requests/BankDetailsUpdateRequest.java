package com.cars24.ai_loan_assistance.data.requests;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import lombok.Data;

@Data
public class BankDetailsUpdateRequest {

    private String bankName;

    private BankAccountType bankAccountType;

    private String accountHolderName;

    private Long bankId;
}
