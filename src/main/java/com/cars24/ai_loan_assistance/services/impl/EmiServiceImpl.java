package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.EmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmiServiceImpl implements EmiService {

    @Autowired
    private EmiRepository emiRepository;

    @Override
    public ResponseEntity<ApiResponse> getEmiMenu(String email) {
        List<EmiEntity> emis = emiRepository.findByUserEmail(email);

        if (emis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse(404, "Sorry, there is no such EMI detail.", "InvalidRequest", false, null)
            );
        }

        if (emis.size() == 1) {
            EmiEntity emi = emis.get(0);
            Map<String, Object> emiDetail = new HashMap<>();
            emiDetail.put("status", emi.getStatus());
            emiDetail.put("emiAmount", emi.getEmiAmount());

            return ResponseEntity.ok(new ApiResponse(200, "You have only one EMI:", "getEmiMenu", true, emiDetail));
        }
        List<Map<String, Object>> emiDetails = new ArrayList<>();
        for (EmiEntity emi : emis) {
            Map<String, Object> emiMap = new HashMap<>();
            emiMap.put("status", emi.getStatus());
            emiMap.put("emiAmount", emi.getEmiAmount());
            emiDetails.add(emiMap);
        }

        return ResponseEntity.ok(new ApiResponse(200, "You have multiple EMIs, please choose one:",
                "getEmiMenu", true, emiDetails));
    }




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
