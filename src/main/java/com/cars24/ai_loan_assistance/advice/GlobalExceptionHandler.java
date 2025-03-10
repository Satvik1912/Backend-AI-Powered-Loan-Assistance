package com.cars24.ai_loan_assistance.advice;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import com.cars24.ai_loan_assistance.exceptions.PromptNotFoundException;
import com.cars24.ai_loan_assistance.exceptions.ResponseNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class    GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception)
    {
        Map<String,String> errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->
        {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });

        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ERROR: INVALID DATA!",
                "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                false,
                errorMap);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public  ResponseEntity<ApiResponse> handleServiceExceptions(NotFoundException exception)
    {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                false,
                null);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationExceptions(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ERROR: INVALID DATA!",
                "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                false,
                errorMap);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponse> handleInvalidFormatException(InvalidFormatException exception) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ERROR: Invalid value for field! " + exception.getMessage(),
                "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(PromptNotFoundException.class)
    public ResponseEntity<ApiResponse> handlePromptNotFoundException(PromptNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "GENERIC_CHATBOT-" + HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // For ResponseNotFoundException
    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResponseNotFoundException(ResponseNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "GENERIC_CHATBOT-" + HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // General exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred.",
                "GENERIC_CHATBOT-" + HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
