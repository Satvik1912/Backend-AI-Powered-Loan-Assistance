package com.cars24.ai_loan_assistance.data.requests;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateBankDetailsTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenAccountNumberIsEmpty() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("");
        request.setAccountHolderName("John Doe");
        request.setIfscCode("HDFC0001234");
        request.setBankName("HDFC Bank");
        request.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenAccountNumberIsInvalid() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("12345"); // Less than 9 digits
        request.setAccountHolderName("John Doe");
        request.setIfscCode("HDFC0001234");
        request.setBankName("HDFC Bank");
        request.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenAccountHolderNameIsEmpty() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("123456789");
        request.setAccountHolderName(""); // Empty
        request.setIfscCode("HDFC0001234");
        request.setBankName("HDFC Bank");
        request.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenIfscCodeIsInvalid() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("123456789");
        request.setAccountHolderName("John Doe");
        request.setIfscCode("HDFC123456"); // Invalid format
        request.setBankName("HDFC Bank");
        request.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenBankNameIsTooShort() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("123456789");
        request.setAccountHolderName("John Doe");
        request.setIfscCode("HDFC0001234");
        request.setBankName("AB"); // Too short
        request.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenBankAccountTypeIsNull() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("123456789");
        request.setAccountHolderName("John Doe");
        request.setIfscCode("HDFC0001234");
        request.setBankName("HDFC Bank");
        request.setBankAccountType(null); // Null bank account type

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassWhenAllFieldsAreValid() {
        CreateBankDetails request = new CreateBankDetails();
        request.setAccountNumber("123456789123");
        request.setAccountHolderName("John Doe");
        request.setIfscCode("HDFC0001234");
        request.setBankName("HDFC Bank");
        request.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<CreateBankDetails>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
