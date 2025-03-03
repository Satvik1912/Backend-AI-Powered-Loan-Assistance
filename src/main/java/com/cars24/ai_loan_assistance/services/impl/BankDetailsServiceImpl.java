package com.gemini.gemini.Services.impl;

import com.gemini.gemini.Services.BankDetailsService;
import com.gemini.gemini.dao.impl.BankDetailsDaoImpl;
import com.gemini.gemini.data.entity.BankDetailsEntity;
import com.gemini.gemini.data.request.CreateBankDetails;
import com.gemini.gemini.data.request.GetBankDetailsOfUser;
import com.gemini.gemini.data.response.GetBankDetailsRespUID;
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
