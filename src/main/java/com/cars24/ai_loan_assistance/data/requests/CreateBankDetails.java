package com.cars24.ai_loan_assistance.data.requests;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Valid
public class CreateBankDetails {

    @NotBlank(message = "Account number cannot be empty")
    @Pattern(regexp = "\\d{9,18}", message = "Account number must be between 9 and 18 digits")
    private String accountNumber;

    @NotBlank(message = "Account holder name cannot be empty")
    @Size(min = 3, max = 50, message = "Account holder name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Account holder name must contain only letters and spaces")
    private String accountHolderName;

    @NotBlank(message = "IFSC code cannot be empty")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;

    @NotBlank(message = "Bank name cannot be empty")
    @Size(min = 3, max = 50, message = "Bank name must be between 3 and 50 characters")
    private String bankName;

    @NotNull(message = "Bank account type is required")
    private BankAccountType bankAccountType;
}
