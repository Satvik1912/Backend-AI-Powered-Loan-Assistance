package com.cars24.ai_loan_assistance.controllers;


import com.cars24.ai_loan_assistance.data.requests.LoanRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.impl.LoanServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanServiceImpl loanService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
    }

    @GetMapping("/loan/{id}")
    public ResponseEntity<ApiResponse> getLoan(@Valid @PathVariable("id") String loan_id){
        return loanService.getLoan(loan_id);
    }

    @GetMapping("/loans")
    public ResponseEntity<ApiResponse> getLoans(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit){
        return loanService.getLoans(page, limit);
    }
}

