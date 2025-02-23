package com.cars24.ai_loan_assistance.controllers;


import com.cars24.ai_loan_assistance.data.requests.LoanRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.impl.LoanServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanServiceImpl loanService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
    }
}

