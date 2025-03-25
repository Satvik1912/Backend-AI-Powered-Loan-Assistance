package com.cars24.ai_loan_assistance.data.requests;

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
class ContactUpdateRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    static Stream<ContactUpdateRequest> invalidContactProvider() {
        return Stream.of(
                createRequest("1234567890", "Valid Address Here"), // Phone does not start with 6-9
                createRequest("98765", "Valid Address Here"), // Phone too short
                createRequest("9876543210987", "Valid Address Here"), // Phone too long
                createRequest("9876543210", "Short") // Address too short
        );
    }

    private static ContactUpdateRequest createRequest(String phone, String address) {
        ContactUpdateRequest request = new ContactUpdateRequest();
        request.setPhone(phone);
        request.setAddress(address);
        return request;
    }

    @ParameterizedTest
    @MethodSource("invalidContactProvider")
    void shouldFailValidation(ContactUpdateRequest request) {
        Set<ConstraintViolation<ContactUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should fail for invalid input");
    }
}
