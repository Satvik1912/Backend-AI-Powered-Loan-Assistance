package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.ChatbotService;
import com.cars24.ai_loan_assistance.services.UserInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;

import static com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent.ACC_PROFILE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {
    private final AccountServiceImpl accountService;
    private final LoanServiceImpl loanService;
    private final UserInformationService userInformationService;
    @Override
    public ResponseEntity<ApiResponse> processQuery(String email, ChatbotIntent intent, String additional) {
        if (intent == ACC_PROFILE) {
            log.info("[chatbotService if-else]");
            return accountService.getUserProfile(email);
        } else if (intent == ChatbotIntent.ACC_KYC) {
            return accountService.getKycDetails(email);
        } else if (intent == ChatbotIntent.LOAN_ACTIVE_NUMBER) {
            return loanService.getActiveLoans(email);
        } else if (intent == ChatbotIntent.LOAN_ACTIVE_DETAILS) {
            return ResponseEntity.ok(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Feature not implemented yet.",
                    "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                    false,
                    null
            ));
        } else if (intent == ChatbotIntent.LOAN_STATUS) {
            return loanService.getLoansByUser(email);
        } else if (intent == ChatbotIntent.BANK_LINKED_NUMBER) {
            return ResponseEntity.ok(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Feature not implemented yet.",
                    "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                    false,
                    null
            ));
        } else if (intent == ChatbotIntent.BANK_LINKED_DETAILS) {
            return ResponseEntity.ok(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Feature not implemented yet.",
                    "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                    false,
                    null
            ));
        } else if (intent == ChatbotIntent.BANK_VIEW_SALARY) {
            return userInformationService.getSalaryDetails(email);
        } else if (intent == ChatbotIntent.BANK_CIBIL) {
            return userInformationService.getCibil(email);
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "ERROR: INVALID DATA!",
                    "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                    false,
                    null
            ));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> processUpdate(String email, ChatbotIntent intent, Map<String, Object> request) {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (intent) {
            case ACC_CONTACT:
                log.info("[hello]");
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

    @Override
    public ResponseEntity<ApiResponse> processCreate(String email, ChatbotIntent intent, Map<String, Object> request) {
        switch (intent) {
            case BANK_ADD:
                return null;
            default:
                return null;
        }
    }
}
