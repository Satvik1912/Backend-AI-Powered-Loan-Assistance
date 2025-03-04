package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmiService {

    ResponseEntity<ApiResponse> getEmiMenu(String email);

    ResponseEntity<ApiResponse> getNextEmiDueDate(String email);

    ResponseEntity<ApiResponse> getEmiAmount(String email);

    ResponseEntity<ApiResponse> getEmiStatus(String email);

    ResponseEntity<ApiResponse> checkMissedPayments(String email);

    ResponseEntity<ApiResponse> getLateFee(String email);

    ResponseEntity<ApiResponse> getCompleteEmiSchedule(String email);
}