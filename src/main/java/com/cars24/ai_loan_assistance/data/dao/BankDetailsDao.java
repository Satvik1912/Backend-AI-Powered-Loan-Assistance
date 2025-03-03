package com.cars24.ai_loan_assistance.data.dao;

import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankDetailsDao {
    public String createBankDetails(CreateBankDetails createBankDetails);
    public GetBankDetailsRespUID getbankdetailsofid(GetBankDetailsOfUser getBankDetailsOfUser);
    public List<BankDetailsEntity> getAllBankDetails();
}
