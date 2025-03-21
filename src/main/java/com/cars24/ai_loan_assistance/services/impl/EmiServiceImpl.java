package com.cars24.ai_loan_assistance.services.impl;

import ch.qos.logback.classic.Logger;
import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.EmiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import com.cars24.ai_loan_assistance.util.PdfGeneratorUtil;


import java.util.*;

@Service
@Slf4j
public class EmiServiceImpl implements EmiService {

    @Autowired
    private EmiRepository emiRepository;
    private LoanRepository loanRepository;
    private UserRepository userRepository;

    @Override
    public Object getEmiDetails(long userId, Long loanId) {
        List<EmiEntity> emis = emiRepository.findEmisByLoanId(loanId);

        if (emis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(403, "No EMI details found for this loan.", "LOAN_EMI_DETAILS", false, null));
        }
        LoanEntity loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(403, "Loan not found!", "LOAN_DETAILS", false, null));
        }

        // Fetch user's name (assuming it's available in LoanEntity)
        String userName = userRepository.findUserNameById(userId);
        if (userName == null) userName = "UnknownUser" ;// Ensure this method exists

        // Generate PDF and get the URL
      //  String pdfLink = PdfGeneratorUtil.generateLoanPdf(userName, loan, emis);

        return Map.of(

                "lastPaid", emis.stream().filter(e -> e.getStatus() == EmiStatus.PAID).toList(),
                "pending", emis.stream().filter(e -> e.getStatus() == EmiStatus.PENDING).toList(),
                "overdue", emis.stream().filter(e -> e.getStatus() == EmiStatus.OVERDUE).toList()
            //    "pdfLink", "http://localhost:8080" + pdfLink  // Return PDF URL
        );
    }
    }






