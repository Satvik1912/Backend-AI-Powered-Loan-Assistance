package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BankDetailsDao {
    public String createBankDetails(String email ,CreateBankDetails createBankDetails);
    public GetBankDetailsRespUID getbankdetailsofid(GetBankDetailsOfUser getBankDetailsOfUser);
    public List<BankDetailsEntity> getAllBankDetails();
    public CountBankAcc countofbanks(String email);
    public BankFullDetails bankfulldetails(String email , long bankid);
    public UpdateBankDetails updatebankdetails(String email , BankDetailsUpdateRequest request);
}
