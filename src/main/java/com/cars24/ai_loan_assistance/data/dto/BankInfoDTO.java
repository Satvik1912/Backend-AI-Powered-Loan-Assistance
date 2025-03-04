package com.cars24.ai_loan_assistance.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankInfoDTO {
    private Long bankId;  // Add this field
    private String bankName;
    private String accountNumber;

    public BankInfoDTO(Long bankId, String bankName, String accountNumber) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters
    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
