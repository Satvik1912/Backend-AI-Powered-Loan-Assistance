package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.ChatbotService;
import com.cars24.ai_loan_assistance.services.UserInformationService;
import com.cars24.ai_loan_assistance.services.impl.AccountServiceImpl;
import com.cars24.ai_loan_assistance.services.impl.BankDetailsServiceImpl;
import com.cars24.ai_loan_assistance.services.impl.LoanServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
@Validated
public class ChatbotController {
    private final AccountServiceImpl accountService;
    private final LoanServiceImpl loanService;
    private final UserInformationService userInformationService;
    private final BankDetailsServiceImpl bankDetailsService;

    @GetMapping("/query")
    public ResponseEntity<ApiResponse> handleQuery(@RequestParam String email, @RequestParam ChatbotIntent intent, @RequestParam(defaultValue = "0") Long additional) {
        return switch (intent) {
            case ACC_PROFILE -> accountService.getUserProfile(email);
            case ACC_KYC -> accountService.getKycDetails(email);
            case LOAN_ACTIVE_NUMBER -> loanService.getActiveLoans(email);
            case LOAN_ACTIVE_DETAILS, BANK_LINKED_NUMBER, BANK_LINKED_DETAILS -> ResponseEntity.ok(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Feature not implemented yet.",
                    "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                    false,
                    null
            ));
            case LOAN_STATUS -> loanService.getLoansByUser(email);
            case BANK_VIEW_SALARY -> userInformationService.getSalaryDetails(email);
            case BANK_CIBIL -> userInformationService.getCibil(email);
            default -> ResponseEntity.badRequest().body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "ERROR: INVALID DATA!",
                    "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                    false,
                    null
            ));
        };
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> handleUpdate(@RequestParam String email, @RequestParam ChatbotIntent intent, @Valid @RequestBody Map<String, Object> request) {
//        return chatbotService.processUpdate(email, intent, request);
        ObjectMapper objectMapper = new ObjectMapper();
        switch (intent) {
            case ACC_CONTACT:
                ContactUpdateRequest contactUpdateRequest = objectMapper.convertValue(request, ContactUpdateRequest.class);
                return accountService.updateContactInfo(email, contactUpdateRequest);
            case BANK_UPDATE:
                return null;
            case BANK_UPDATE_SALARY:
                SalaryUpdateRequest salaryUpdateRequest = objectMapper.convertValue(request, SalaryUpdateRequest.class);
                return userInformationService.updateSalaryDetails(email, salaryUpdateRequest);

            default:
                return null;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> handleCreate(@RequestParam String email, @RequestParam ChatbotIntent intent, @Valid @RequestBody Map<String, Object> request) {
//        return chatbotService.processCreate(email,intent,request);
        switch (intent) {
            case BANK_ADD:
                return null;
            default:
                return null;
        }
    }
}
