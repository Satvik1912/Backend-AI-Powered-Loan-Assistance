package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.impl.BankDetailsDaoImpl;
import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.BankFullDetails;
import com.cars24.ai_loan_assistance.data.responses.CountBankAcc;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import com.cars24.ai_loan_assistance.services.BankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsDaoImpl bankDetailsDao;
    @Override
    public ResponseEntity<ApiResponse> createBankDetails(String email,CreateBankDetails createBankDetails) {
        bankDetailsDao.createBankDetails(email,createBankDetails);
        return null;

    }

    @Override
    public GetBankDetailsRespUID getBankDetailsRespUID(GetBankDetailsOfUser getBankDetailsOfUser) {
        return bankDetailsDao.getbankdetailsofid(getBankDetailsOfUser);
    }

    @Override
    public List<BankDetailsEntity> getAllBank() {
        return bankDetailsDao.getAllBankDetails();
    }

    @Override
    public ResponseEntity<ApiResponse> countofbanks(String email) {


        CountBankAcc response = bankDetailsDao.countofbanks(email);

        if (response.getBankCount() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "No bank accounts found for user ID: " ,
                            "BankDetailsService",
                            false,
                            null
                    ));
        }

        return ResponseEntity.ok(new ApiResponse(
                HttpStatus.OK.value(),
                "Bank details retrieved successfully",
                "BankDetailsService",
                true,
                response
        ));
    }

    @Override
    public ResponseEntity<ApiResponse> bankfulldetails(String email, long bankid) {
        BankFullDetails bankFullDetails = bankDetailsDao.bankfulldetails(email,bankid);
        return ResponseEntity.ok(new ApiResponse(
                HttpStatus.OK.value(),
                "Bank details retrieved successfully",
                "BankDetailsService",
                true,
                bankFullDetails
        ));
    }
}
