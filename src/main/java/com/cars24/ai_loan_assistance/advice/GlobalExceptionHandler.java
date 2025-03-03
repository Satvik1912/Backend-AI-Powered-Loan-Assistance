package com.cars24.ai_loan_assistance.advice;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.exceptions.LoanServiceException;
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

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(),"ERROR: INVALID DATA!", "APP_USER - " + HttpStatus.BAD_REQUEST.value(), false, errorMap);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(LoanServiceException.class)
    public  ResponseEntity<ApiResponse> handleServiceExceptions(LoanServiceException exception)
    {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), "APP_USER - " + HttpStatus.BAD_REQUEST.value(),false,null);
        return ResponseEntity.badRequest().body(apiResponse);

    }

}
