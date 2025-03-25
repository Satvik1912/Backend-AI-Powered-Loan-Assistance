package com.cars24.ai_loan_assistance.services;
import com.cars24.ai_loan_assistance.data.dto.UserDetailsResponseDto;
import com.cars24.ai_loan_assistance.data.entities.*;
import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsMapper {
    @Autowired
    private UserRepository userRepository;

    public String getUserDetailsAsParagraph(Long userId) {
        try {
            List<Object[]> results = userRepository.getUserDetails(userId);

            if (results.isEmpty()) {
                return "No details found for the given user ID.";
            }

            Object[] data = results.get(0);

            // Extracting values
            String name = (String) data[0];
            String phone = (String) data[1];
            String address = (String) data[2];
            String email = (String) data[3];
            String pan = (String) data[4];
            String aadhar = (String) data[5];
            Double salary = (Double) data[6];
            Integer cibil = (Integer) data[7];
            String incomeType = (String) data[8];
            String loanStatus = (String) data[9];
            String loanType = (String) data[10];
            LocalDate disbursedDate = (LocalDate) data[11];
            Double principal = (Double) data[12];
            Double tenure = (Double) data[13];
            Double interest = (Double) data[14];
            Double emiAmount = (Double) data[15];
            Integer overdueCount = (Integer) data[16];
            Integer pendingCount = (Integer) data[17];
            Integer paidCount = (Integer) data[18];
            LocalDate nextDueDate = data[19] != null ? (LocalDate) data[19] : null;
            Double totalLateFee = (Double) data[20];
            String accountNumber = (String) data[21];
            String accountHolderName = (String) data[22];
            String ifscCode = (String) data[23];
            String bankName = (String) data[24];
            String bankAccountType = (String) data[25];

            // Formatting into a paragraph
            return String.format("User %s with phone number %s and address %s has the email %s. " +
                            "Their PAN number is %s, Aadhar number is %s, with a salary of %.2f and a CIBIL score of %d. " +
                            "They have an income type of %s. Their loan status is %s, of type %s, disbursed on %s with a principal amount of %.2f, tenure of %.2f months, and interest of %.2f%%. " +
                            "The EMI amount is %.2f, with %d overdue payments, %d pending payments, and %d paid payments. The next due date for pending payments is %s with a total late fee of %.2f. " +
                            "The bank account number is %s, account holder name is %s, IFSC code is %s, bank name is %s, and the account type is %s.",
                    name, phone, address, email, pan, aadhar, salary, cibil, incomeType,
                    loanStatus, loanType, disbursedDate, principal, tenure, interest,
                    emiAmount, overdueCount, pendingCount, paidCount,
                    nextDueDate != null ? nextDueDate.toString() : "No pending EMIs",
                    totalLateFee, accountNumber, accountHolderName, ifscCode, bankName, bankAccountType
            );
        }
        catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return "Error fetching user details: " + e.getMessage();
        }
    }
}
