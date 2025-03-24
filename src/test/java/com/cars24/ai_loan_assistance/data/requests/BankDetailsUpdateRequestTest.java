package com.cars24.ai_loan_assistance.data.requests;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankDetailsUpdateRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    static Stream<BankDetailsUpdateRequest> invalidBankDetailsProvider() {
        return Stream.of(
                createRequest("AB", "John Doe", BankAccountType.SAVINGS),       // Bank name too short
                createRequest("A".repeat(51), "John Doe", BankAccountType.SAVINGS), // Bank name too long
                createRequest("Test Bank", "John123", BankAccountType.SAVINGS),  // Account holder name contains numbers
                createRequest("Test Bank", "Jo", BankAccountType.SAVINGS)        // Account holder name too short
        );
    }

    private static BankDetailsUpdateRequest createRequest(String bankName, String accountHolderName, BankAccountType type) {
        BankDetailsUpdateRequest request = new BankDetailsUpdateRequest();
        request.setBankName(bankName);
        request.setAccountHolderName(accountHolderName);
        request.setBankAccountType(type);
        return request;
    }

    @ParameterizedTest
    @MethodSource("invalidBankDetailsProvider")
    void shouldFailValidation(BankDetailsUpdateRequest request) {
        Set<ConstraintViolation<BankDetailsUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail for invalid input");
    }
}
