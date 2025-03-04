package com.cars24.ai_loan_assistance.data.requests;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class CreateBankDetails {
    private String full_name;

    private String account_no;

    private  String bank_acc_type;

    private  String bank_name;

    private  String ifsc_code;

    private  Long uid;


}
