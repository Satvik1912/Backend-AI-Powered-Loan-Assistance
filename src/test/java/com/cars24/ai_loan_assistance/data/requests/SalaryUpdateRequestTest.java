package com.cars24.ai_loan_assistance.data.requests;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SalaryUpdateRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenSalaryIsBelowMinimum() {
        SalaryUpdateRequest request = new SalaryUpdateRequest();
        request.setSalary(25000); // Below 30000

        Set<ConstraintViolation<SalaryUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassWhenSalaryIsExactlyMinimum() {
        SalaryUpdateRequest request = new SalaryUpdateRequest();
        request.setSalary(30000); // Exactly 30000

        Set<ConstraintViolation<SalaryUpdateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldPassWhenSalaryIsAboveMinimum() {
        SalaryUpdateRequest request = new SalaryUpdateRequest();
        request.setSalary(50000); // Above 30000

        Set<ConstraintViolation<SalaryUpdateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
