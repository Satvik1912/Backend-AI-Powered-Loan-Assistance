package com.cars24.ai_loan_assistance.data.responses;

import lombok.Data;

@Data
public class GetBankDetailsRespUID {
    private String full_name;

    private String account_no;

    private  String bank_acc_type;

    private  String bank_name;

    private  String ifsc_code;
}
