package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import lombok.Data;

@Data
public class BankFullDetails {
//    private UserEntity user;

    private String accountNumber;

    private String accountHolderName;

    private String ifscCode;

    private String bankName;

    private BankAccountType bankAccountType;
}
