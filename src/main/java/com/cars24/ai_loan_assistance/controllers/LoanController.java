package com.cars24.ai_loan_assistance.controllers;


import com.cars24.ai_loan_assistance.data.requests.LoanRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

//    private final LoanServiceImpl loanService;
    private final LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
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

    @GetMapping("/searchLoans")
    public ResponseEntity<ApiResponse> searchLoans(
            @RequestParam String fieldName,
            @RequestParam String fieldValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return loanService.searchLoans(fieldName, fieldValue, page, size);
    }

}

