package com.cars24.ai_loan_assistance.services.impl;

import ch.qos.logback.classic.Logger;
import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.EmiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EmiServiceImpl implements EmiService {

    @Autowired
    private EmiRepository emiRepository;

    @Override
    public ResponseEntity<ApiResponse> getEmiDetails(String email, Long loanId) {
        List<EmiEntity> emis = emiRepository. findEmisByLoanId(loanId);

        if (emis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, "No EMI details found for this loan.", "EMI_DETAILS", false, null));
        }

        List<EmiEntity> lastPaid = emis.stream()
                .filter(emi -> emi.getStatus() == EmiStatus.PAID)
                .sorted(Comparator.comparing(EmiEntity::getDueDate).reversed())
                .limit(1)
                .toList();

        List<EmiEntity> pending = emis.stream()
                .filter(emi -> emi.getStatus() == EmiStatus.PENDING)
                .toList();

        List<EmiEntity> overdue = emis.stream()
                .filter(emi -> emi.getStatus() == EmiStatus.OVERDUE)
                .toList();

        Map<String, Object> emiDetails = Map.of(
                "lastPaid", lastPaid,
                "pending", pending,
                "overdue", overdue
        );

        return ResponseEntity.ok(new ApiResponse(200, "EMI details fetched successfully", "EMI_DETAILS", true, emiDetails));
    }





}
