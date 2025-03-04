package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.CountBankAcc;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankDetailsService {
    public ResponseEntity<ApiResponse> createBankDetails(String email ,CreateBankDetails createBankDetails);
    public GetBankDetailsRespUID getBankDetailsRespUID(GetBankDetailsOfUser getBankDetailsOfUser);
    public List<BankDetailsEntity> getAllBank();
    public ResponseEntity<ApiResponse> countofbanks(String email);
}
