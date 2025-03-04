package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.impl.AccountServiceImpl;
import com.cars24.ai_loan_assistance.services.impl.EmiServiceImpl;
import com.cars24.ai_loan_assistance.services.impl.LoanDetailServiceImpl;
import lombok.RequiredArgsConstructor;
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
    private final EmiServiceImpl emiServiceImpl;
    private final LoanDetailServiceImpl loanDetailService;
    @GetMapping("/query")
    public ResponseEntity<ApiResponse> processQuery(String email, ChatbotIntent intent, String additional) {
        switch (intent) {
            case ACC_PROFILE:
                return accountService.getUserProfile(email);
            case ACC_KYC:
                return accountService.getKycDetails(email);
            case LOAN_ACTIVE_NUMBER:
//                return loanDetailService.getLoanCountByEmail(email);
            case LOAN_ACTIVE_DETAILS:
                return loanDetailService.getLoanDetailsByEmail(email);
            case PRINCIPAL_AMOUNT:
                return loanDetailService.getPrincipalByEmail(email);
            case LOAN_TENURE:
                return loanDetailService.getTenureByEmail(email);
            case INTEREST_RATE:
                return loanDetailService.getInterestByEmail(email);
            case LOAN_STATUS:
                return null;
            case BANK_LINKED_NUMBER:
                return null;
            case BANK_LINKED_DETAILS:
                return null;
            case BANK_VIEW_SALARY:
                return null;
            case BANK_CIBIL:
                return null;
            case EMI_DUE_DATE:
                return emiServiceImpl.getNextEmiDueDate(email);
            case EMI_AMOUNT:
                return emiServiceImpl.getEmiAmount(email);
            case EMI_STATUS:
                return emiServiceImpl.getEmiStatus(email);
            case EMI_PAYMENTS:
                return emiServiceImpl.checkMissedPayments(email);
            case LATE_FEE:
                return emiServiceImpl.getLateFee(email);
            case EMI_SCHEDULE:
                return emiServiceImpl.getCompleteEmiSchedule(email);

            default:
                return ResponseEntity.badRequest().body(
                        new ApiResponse(400,
                                "Sorry, I didn't understand that. Please ask a valid question.",
                                "InvalidRequest",
                                false,
                                null)
                );
        }

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> processUpdate(String email, ChatbotIntent intent, Map<String, Object> request) {
        switch (intent) {
            case ACC_CONTACT:
                return accountService.updateContactInfo(email, (ContactUpdateRequest) request);
            case BANK_UPDATE:
                return null;
            case BANK_UPDATE_SALARY:
                return null;
            default:
                return null;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> processCreate(String email, ChatbotIntent intent, Map<String, Object> request) {
        switch (intent) {
            case BANK_ADD:
                return null;
            default:
                return null;
        }
    }
}
