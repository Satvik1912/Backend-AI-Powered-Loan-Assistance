package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.services.impl.EmiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emi")
public class EmiController {

    @Autowired
    private EmiServiceImpl emiServiceImpl;

    @GetMapping("/question")
    public String chatbot(@RequestParam String query, @RequestParam String email) {
        switch (query.toLowerCase()) {
            case "what is my next emi due date?":
                return emiServiceImpl.getNextEmiDueDate(email);
            case "what is my emi amount?":
                return emiServiceImpl.getEmiAmount(email);
            case "what is my emi status?":
                return emiServiceImpl.getEmiStatus(email);
            case "have i missed any emi payments?":
                return emiServiceImpl.checkMissedPayments(email);
            case "what is my late fee?":
                return emiServiceImpl.getLateFee(email);
            case "show my complete emi schedule.":
                return emiServiceImpl.getCompleteEmiSchedule(email);
            default:
                return "Sorry, I didn't understand that. Please ask a valid EMI-related question.";
        }
    }
}
