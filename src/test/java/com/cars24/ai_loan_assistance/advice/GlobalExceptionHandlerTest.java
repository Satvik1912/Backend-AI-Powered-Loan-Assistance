package com.cars24.ai_loan_assistance.advice;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import com.cars24.ai_loan_assistance.exceptions.PromptNotFoundException;
import com.cars24.ai_loan_assistance.exceptions.ResponseNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleValidationExceptions() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(new FieldError("field", "fieldName", "Error Message")));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiResponse> response = exceptionHandler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR: INVALID DATA!", response.getBody().getMessage());
        assertFalse(response.getBody().isSuccess());

        // Validate errors stored in 'data' field
        assertTrue(response.getBody().getData() instanceof Map);
        Map<String, String> errors = (Map<String, String>) response.getBody().getData();
        assertEquals("Error Message", errors.get("fieldName"));
    }

    @Test
    void testHandleServiceExceptions() {
        NotFoundException exception = new NotFoundException("Not Found!");

        ResponseEntity<ApiResponse> response = exceptionHandler.handleServiceExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not Found!", response.getBody().getMessage());
    }

    @Test
    void testHandleConstraintViolationExceptions() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        // Mock property path properly
        jakarta.validation.Path propertyPath = mock(jakarta.validation.Path.class);
        when(propertyPath.toString()).thenReturn("fieldName");

        when(violation.getPropertyPath()).thenReturn(propertyPath);  // Ensure this is not null
        when(violation.getMessage()).thenReturn("must not be null");

        ConstraintViolationException exception = new ConstraintViolationException("Constraint Violation", Set.of(violation));

        ResponseEntity<ApiResponse> response = exceptionHandler.handleConstraintViolationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR: INVALID DATA!", response.getBody().getMessage());

        // Validate errors stored in 'data' field
        assertTrue(response.getBody().getData() instanceof Map);
        Map<String, String> errors = (Map<String, String>) response.getBody().getData();
        assertEquals("must not be null", errors.get("fieldName"));
    }


    @Test
    void testHandleInvalidFormatException() {
        InvalidFormatException exception = mock(InvalidFormatException.class);
        when(exception.getMessage()).thenReturn("Invalid value!");

        ResponseEntity<ApiResponse> response = exceptionHandler.handleInvalidFormatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Invalid value!"));
    }

    @Test
    void testHandlePromptNotFoundException() {
        PromptNotFoundException exception = new PromptNotFoundException("Prompt Not Found");

        ResponseEntity<ApiResponse> response = exceptionHandler.handlePromptNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Prompt Not Found", response.getBody().getMessage());
    }

    @Test
    void testHandleResponseNotFoundException() {
        ResponseNotFoundException exception = new ResponseNotFoundException("Response Not Found");

        ResponseEntity<ApiResponse> response = exceptionHandler.handleResponseNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Response Not Found", response.getBody().getMessage());
    }

    @Test
    void testHandleGlobalException() {
        Exception exception = new Exception("Internal Error");

        ResponseEntity<ApiResponse> response = exceptionHandler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", response.getBody().getMessage());
    }
}
