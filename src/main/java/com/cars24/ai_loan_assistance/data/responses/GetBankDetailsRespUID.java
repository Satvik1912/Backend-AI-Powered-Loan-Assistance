package com.gemini.gemini.data.response;

import lombok.Data;

@Data
public class GetBankDetailsRespUID {
    private String full_name;

    private String account_no;

    private  String bank_acc_type;

    private  String bank_name;

    private  String ifsc_code;
}
