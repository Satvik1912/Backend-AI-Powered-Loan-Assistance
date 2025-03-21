package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmiService {
    Object getEmiDetails(long userId, Long loanId);
}