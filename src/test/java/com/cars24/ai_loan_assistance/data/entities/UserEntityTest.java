package com.cars24.ai_loan_assistance.data.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

class UserEntityTest {

    private Validator validator;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        // Set up the Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Create a valid UserEntity object
        user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("John Doe");
        user.setPassword("securePass");
        user.setPhone("9876543210");
        user.setAddress("123 Main St");
        user.setIsActive(true);
    }

    @Test
    void testValidUserEntity() {
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid user should not have validation errors");
    }

    @Test
    void testInvalidEmail() {
        user.setEmail("invalid-email");
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Invalid email should trigger validation error");
    }

    @Test
    void testShortPassword() {
        user.setPassword("123"); // Too short
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Short password should trigger validation error");
    }

    @Test
    void testInvalidPhoneNumber() {
        user.setPhone("12345"); // Invalid format
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Invalid phone number should trigger validation error");
    }

    @Test
    void testRelationships() {
        // Checking default lists (should be empty, not null)
        assertNotNull(user.getBankEntities(), "BankEntities list should be initialized");
        assertNotNull(user.getLoans(), "Loans list should be initialized");
    }
}
