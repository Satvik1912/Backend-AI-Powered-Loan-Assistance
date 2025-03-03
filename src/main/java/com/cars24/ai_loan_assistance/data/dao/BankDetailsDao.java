package com.gemini.gemini.dao;

import com.gemini.gemini.data.entity.BankDetailsEntity;
import com.gemini.gemini.data.request.CreateBankDetails;
import com.gemini.gemini.data.request.GetBankDetailsOfUser;
import com.gemini.gemini.data.response.GetBankDetailsRespUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankDetailsDao {
    public String createBankDetails(CreateBankDetails createBankDetails);
    public GetBankDetailsRespUID getbankdetailsofid(GetBankDetailsOfUser getBankDetailsOfUser);
    public List<BankDetailsEntity> getAllBankDetails();
}
