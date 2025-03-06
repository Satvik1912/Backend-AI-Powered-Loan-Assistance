package com.cars24.ai_loan_assistance.data.requests;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class CreateBankDetails {

    private String accountNumber;

    private String accountHolderName;

    private String ifscCode;

    private String bankName;

    private BankAccountType bankAccountType;


}
