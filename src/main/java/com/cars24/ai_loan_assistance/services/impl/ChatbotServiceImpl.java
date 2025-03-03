package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {
    private final AccountServiceImpl accountService;
    @Override
    public ResponseEntity<ApiResponse> processQuery(String email, ChatbotIntent intent, String additional) {
        switch (intent) {
            case ACC_PROFILE:
                return accountService.getUserProfile(email);
            case ACC_KYC:
                return accountService.getKycDetails(email);
            case LOAN_ACTIVE_NUMBER:
                return null;
            case LOAN_ACTIVE_DETAILS:
                return null;
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
            default:
                return null;
        }

    }

    @Override
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
