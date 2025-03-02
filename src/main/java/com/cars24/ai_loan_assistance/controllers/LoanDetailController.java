package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.services.impl.LoanDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loanDetail")
public class LoanDetailController {

    @Autowired
    private LoanDetailServiceImpl loanDetailService;

    @GetMapping("/question")
    public String chatbotResponse(@RequestParam String email, @RequestParam String query) {
        query = query.toLowerCase();

        switch (query) {
            case "what are my loan details?":
                return loanDetailService.getLoanDetailsByEmail(email);

            case "what is my principal amount?":
                return loanDetailService.getPrincipalByEmail(email);

            case "what is my loan tenure?":
                return loanDetailService.getTenureByEmail(email);

            case "what is my interest rate?":
                return loanDetailService.getInterestByEmail(email);

            case "how many active loans do i have?":
                return loanDetailService.getLoanCountByEmail(email);

            default:
                return "Sorry, I didn't understand your query.  Try asking:\n" +
                        " 'What are my loan details?'\n" +
                        " 'What is my principal amount?'\n" +
                        " 'What is my loan tenure?'\n" +
                        " 'What is my interest rate?'\n" +
                        " 'How many active loans do I have?'\n\n" ;
        }
    }
}
