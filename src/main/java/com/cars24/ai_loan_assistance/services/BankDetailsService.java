package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.CountBankAcc;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import com.cars24.ai_loan_assistance.data.responses.UpdateBankDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BankDetailsService {
    public ResponseEntity<ApiResponse> createBankDetails(String email ,CreateBankDetails createBankDetails);
    public GetBankDetailsRespUID getBankDetailsRespUID(GetBankDetailsOfUser getBankDetailsOfUser);
    public List<BankDetailsEntity> getAllBank();
    public ResponseEntity<ApiResponse> countofbanks(String email);
    public ResponseEntity<ApiResponse> bankfulldetails(String email , long bankid);
    public ResponseEntity<ApiResponse> updatebankdetails(String email, BankDetailsUpdateRequest request);
}
