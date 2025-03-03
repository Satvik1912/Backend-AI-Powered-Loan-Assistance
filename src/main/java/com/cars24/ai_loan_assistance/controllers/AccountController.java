package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile(@RequestParam String email) {
        return accountService.getUserProfile(email);
    }

    @PutMapping("/contact")
    public ResponseEntity<ApiResponse> updateContactInfo(@RequestParam String email, @Valid @RequestBody ContactUpdateRequest request) {
        return accountService.updateContactInfo(email, request);
    }

    @GetMapping("/kyc")
    public ResponseEntity<ApiResponse> getKycDetails(@RequestParam String email) {
        return accountService.getKycDetails(email);
    }

}
