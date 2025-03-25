package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

 class BankEntityTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidBankEntityShouldPass() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity()); // Valid user
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertTrue(violations.isEmpty(), "Valid bank entity should pass validation");
    }

    @Test
    void testNullUserShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(null); // Invalid
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Bank entity without user should fail validation");
    }

    @Test
    void testEmptyAccountNumberShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber(""); // Invalid
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Empty account number should fail validation");
    }

    @Test
    void testNullAccountNumberShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber(null); // Invalid
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Null account number should fail validation");
    }

    @Test
    void testNullAccountHolderNameShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName(null); // Invalid
        bank.setIfscCode("HDFC0001234");
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Null account holder name should fail validation");
    }

    @Test
    void testEmptyIfscCodeShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode(""); // Invalid
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Empty IFSC code should fail validation");
    }

    @Test
    void testNullIfscCodeShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode(null); // Invalid
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Null IFSC code should fail validation");
    }

    @Test
    void testNullBankNameShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName(null); // Invalid
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Null bank name should fail validation");
    }

    @Test
    void testEmptyBankNameShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName(""); // Invalid
        bank.setBankAccountType(BankAccountType.SAVINGS);

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Empty bank name should fail validation");
    }

    @Test
    void testNullBankAccountTypeShouldFail() {
        BankEntity bank = new BankEntity();
        bank.setUser(new UserEntity());
        bank.setAccountNumber("123456789012");
        bank.setAccountHolderName("John Doe");
        bank.setIfscCode("HDFC0001234");
        bank.setBankName("HDFC Bank");
        bank.setBankAccountType(null); // Invalid

        Set<ConstraintViolation<BankEntity>> violations = validator.validate(bank);
        assertFalse(violations.isEmpty(), "Null bank account type should fail validation");
    }
}
