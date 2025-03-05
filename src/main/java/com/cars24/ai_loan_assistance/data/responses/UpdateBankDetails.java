package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import lombok.Data;

@Data
public class UpdateBankDetails {

    private String bankName;

    private BankAccountType bankAccountType;

    private String accountHolderName;
}
