package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.*;
import org.springframework.stereotype.Service;

@Service
public interface BankDetailsDao {
    public String createBankDetails(String email ,CreateBankDetails createBankDetails);
    public CountBankAcc countofbanks(String email);
    public BankFullDetails bankfulldetails(String email , long bankid);
    public UpdateBankDetails updatebankdetails(String email , BankDetailsUpdateRequest request);
}
