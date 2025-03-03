package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.EmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmiServiceImpl implements EmiService {

    @Autowired
    private EmiRepository emiRepository;

    @Override
    public ResponseEntity<ApiResponse> getNextEmiDueDate(String email) {
        Optional<EmiEntity> nextEmi = emiRepository.findByUserEmail(email).stream()
                .filter(emi -> emi.getStatus().name().equals("PENDING"))
                .min((e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate()));

        return ResponseEntity.ok(new ApiResponse(200, "Next EMI due date retrieved",
                "getNextEmiDueDate", true, nextEmi.orElse(null)));
    }

    @Override
    public ResponseEntity<ApiResponse> getEmiAmount(String email) {
        Optional<EmiEntity> emi = emiRepository.findByUserEmail(email).stream().findFirst();
        return ResponseEntity.ok(new ApiResponse(200, "EMI amount retrieved",
                "getEmiAmount", true, emi));
    }

    @Override
    public ResponseEntity<ApiResponse> getEmiStatus(String email) {
        List<EmiEntity> emis = emiRepository.findByUserEmail(email);
        return ResponseEntity.ok(new ApiResponse(200, "EMI status retrieved",
                "getEmiStatus", true, emis));
    }

    @Override
    public ResponseEntity<ApiResponse> checkMissedPayments(String email) {
        List<EmiEntity> overdueEmis = emiRepository.findByUserEmail(email).stream()
                .filter(emi -> emi.getStatus().name().equals("OVERDUE"))
                .toList();

        return ResponseEntity.ok(new ApiResponse(200, "Missed payments check",
                "checkMissedPayments", true, overdueEmis));
    }

    @Override
    public ResponseEntity<ApiResponse> getLateFee(String email) {
        double totalLateFee = emiRepository.findByUserEmail(email).stream()
                .mapToDouble(EmiEntity::getLateFee)
                .sum();

        return ResponseEntity.ok(new ApiResponse(200, "Late fee retrieved",
                "getLateFee", true, totalLateFee));
    }

    @Override
    public ResponseEntity<ApiResponse> getCompleteEmiSchedule(String email) {
        List<EmiEntity> emis = emiRepository.findByUserEmail(email);
        return ResponseEntity.ok(new ApiResponse(200, "EMI schedule retrieved",
                "getCompleteEmiSchedule", true, emis));
    }
}
