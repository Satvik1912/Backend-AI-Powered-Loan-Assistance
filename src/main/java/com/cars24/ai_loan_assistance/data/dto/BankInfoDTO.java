package com.cars24.ai_loan_assistance.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankInfoDTO {
    private String accountNumber;
    private String bankName;
}
