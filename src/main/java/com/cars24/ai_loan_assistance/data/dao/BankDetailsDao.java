package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.*;
import org.springframework.stereotype.Service;

@Service
public interface BankDetailsDao {
    String createBankDetails(String email ,CreateBankDetails createBankDetails);
    CountBankAcc countofbanks(String email);
    BankFullDetails bankfulldetails(String email , long bankid);
    String  updatebankdetails(String email , BankDetailsUpdateRequest request, long additional);
}
