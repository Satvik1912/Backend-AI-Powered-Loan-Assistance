package com.cars24.ai_loan_assistance.data.requests;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class ContactUpdateRequest {

    @Valid
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number should be 10 digits only!")
    private String phone;

    @Valid
    @Size(min=10, max = 45, message = "Address should be between 10 to 45 characters only!")
    private String address;
}
