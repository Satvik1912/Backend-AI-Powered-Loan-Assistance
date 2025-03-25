package com.cars24.ai_loan_assistance.data.responses;

import lombok.Data;

@Data
public class GetBankDetailsRespUID {
    private String fullName;

    private String accountNo;

    private  String bankAccType;

    private  String bankName;

    private  String ifscCode;
}
