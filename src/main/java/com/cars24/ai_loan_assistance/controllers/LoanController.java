package com.cars24.ai_loan_assistance.controllers;


import com.cars24.ai_loan_assistance.data.requests.LoanRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class LoanController {

//    private final LoanServiceImpl loanService;
    private final LoanService loanService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') and !hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
    }

    @GetMapping("/loan/{id}")
    @PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getLoan(@Valid @PathVariable("id") long loan_id){
        return loanService.getLoan(loan_id);
    }

    @GetMapping("/loans")
    @PreAuthorize("!hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getLoans(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit){
        return loanService.getLoans(page, limit);
    }

    /*
    @Id
    private String lId;
    @Field("userId")
    @Field("loanAmount")
    @Field("loanType")
    @Field("status")
    @Field("disbursalDate")
    */

//    @GetMapping("/searchLoans")
//    public ResponseEntity<ApiResponse> searchLoans(
//            @RequestParam String fieldName,
//            @RequestParam String fieldValue,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return loanService.searchLoans(fieldName, fieldValue, page, size);
//    }

}

