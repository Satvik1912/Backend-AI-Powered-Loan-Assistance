package com.cars24.ai_loan_assistance.data.responses;


import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankFullDetails {
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
    private String bankName;
    private BankAccountType bankAccountType;
}
