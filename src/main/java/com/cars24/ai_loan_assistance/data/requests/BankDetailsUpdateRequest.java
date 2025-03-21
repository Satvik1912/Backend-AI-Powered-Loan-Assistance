package com.cars24.ai_loan_assistance.data.requests;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class BankDetailsUpdateRequest {

    @Size(min = 3, max = 50, message = "Bank name must be between 3 and 50 characters")
    private String bankName;

    private BankAccountType bankAccountType;

    @Size(min = 3, max = 50, message = "Account holder name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Account holder name must contain only letters and spaces")
    private String accountHolderName;
}
