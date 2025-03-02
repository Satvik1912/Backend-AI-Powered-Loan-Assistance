package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.services.EmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmiServiceImpl implements EmiService {

    @Autowired
    private EmiRepository emiRepository;

    public String getNextEmiDueDate(String email) {
        Optional<EmiEntity> nextEmi = emiRepository.findByUserEmail(email).stream()
                .filter(emi -> emi.getStatus().name().equals("PENDING"))
                .min((e1, e2) -> e1.getDueDate().compareTo(e2.getDueDate()));

        return nextEmi.map(emi -> "Your next EMI is due on: " + emi.getDueDate())
                .orElse("No pending EMIs found.");
    }

    public String getEmiAmount(String email) {
        Optional<EmiEntity> emi = emiRepository.findByUserEmail(email).stream().findFirst();
        return emi.map(e -> "Your EMI amount is: " + e.getEmiAmount())
                .orElse("No EMI records found.");
    }

    public String getEmiStatus(String email) {
        List<EmiEntity> emis = emiRepository.findByUserEmail(email);
        if (emis.isEmpty()) return "No EMI records found.";

        long pendingCount = emis.stream().filter(e -> e.getStatus().name().equals("PENDING")).count();
        long paidCount = emis.stream().filter(e -> e.getStatus().name().equals("PAID")).count();
        long overdueCount = emis.stream().filter(e -> e.getStatus().name().equals("OVERDUE")).count();

        return String.format("EMI Status: PENDING (%d), PAID (%d), OVERDUE (%d)", pendingCount, paidCount, overdueCount);
    }

    public String checkMissedPayments(String email) {
        List<EmiEntity> overdueEmis = emiRepository.findByUserEmail(email).stream()
                .filter(emi -> emi.getStatus().name().equals("OVERDUE"))
                .toList();

        return overdueEmis.isEmpty() ? "You have no missed EMI payments."
                : "You have " + overdueEmis.size() + " overdue EMIs.";
    }

    public String getLateFee(String email) {
        double totalLateFee = emiRepository.findByUserEmail(email).stream()
                .mapToDouble(EmiEntity::getLateFee)
                .sum();

        return "Your total late fee is: ₹" + totalLateFee;
    }

    public String getCompleteEmiSchedule(String email) {
        List<EmiEntity> emis = emiRepository.findByUserEmail(email);
        if (emis.isEmpty()) return "No EMI records found.";

        StringBuilder schedule = new StringBuilder("Your EMI Schedule:\n");
        for (EmiEntity emi : emis) {
            schedule.append(String.format(
                    "₹%.2f | Due: %s | Status: %s\n",
                    emi.getEmiAmount(), emi.getDueDate(), emi.getStatus().name()
            ));
        }
        return schedule.toString();
    }
}
