package com.cars24.ai_loan_assistance.services;

public interface EmiService {

    String getNextEmiDueDate(String email);

    String getEmiAmount(String email);

    String getEmiStatus(String email);

    String checkMissedPayments(String email);

    String getLateFee(String email);

    String getCompleteEmiSchedule(String email);
}

