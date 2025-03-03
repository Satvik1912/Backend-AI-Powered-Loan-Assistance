package com.gemini.gemini.Services;

import com.gemini.gemini.data.entity.BankDetailsEntity;
import com.gemini.gemini.data.request.CreateBankDetails;
import com.gemini.gemini.data.request.GetBankDetailsOfUser;
import com.gemini.gemini.data.response.GetBankDetailsRespUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankDetailsService {
    public String createBankDetails(CreateBankDetails createBankDetails);
    public GetBankDetailsRespUID getBankDetailsRespUID(GetBankDetailsOfUser getBankDetailsOfUser);
    public List<BankDetailsEntity> getAllBank();
}
