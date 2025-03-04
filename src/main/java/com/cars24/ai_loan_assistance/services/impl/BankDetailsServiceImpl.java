package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.impl.BankDetailsDaoImpl;
import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import com.cars24.ai_loan_assistance.services.BankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsDaoImpl bankDetailsDao;
    @Override
    public String createBankDetails(CreateBankDetails createBankDetails) {
        bankDetailsDao.createBankDetails(createBankDetails);
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
}
