package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.*;
import org.springframework.stereotype.Service;

@Service
public interface BankDetailsDao {
    String createBankDetails(long userId ,CreateBankDetails createBankDetails);
    CountBankAcc countofbanks(long userId);
    BankFullDetails bankfulldetails(long userId , long bankid);
    String  updatebankdetails(long userId , BankDetailsUpdateRequest request, long additional);
}
