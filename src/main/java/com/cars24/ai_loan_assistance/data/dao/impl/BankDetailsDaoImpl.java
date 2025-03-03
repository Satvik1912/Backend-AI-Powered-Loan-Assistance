package com.gemini.gemini.dao.impl;

import com.gemini.gemini.Repository.BankDetailsRepository;
import com.gemini.gemini.dao.BankDetailsDao;
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
public class BankDetailsDaoImpl implements BankDetailsDao {

    BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
    private final BankDetailsRepository bankDetailsRepository;
    GetBankDetailsRespUID getBankDetailsRespUID = new GetBankDetailsRespUID();
    @Override
    public String createBankDetails(CreateBankDetails createBankDetails) {
        bankDetailsEntity.setBank_name(createBankDetails.getBank_name());
        bankDetailsEntity.setBank_acc_type(createBankDetails.getBank_acc_type());
bankDetailsEntity.setAccount_no(createBankDetails.getAccount_no());
bankDetailsEntity.setIfsc_code(createBankDetails.getIfsc_code());
bankDetailsEntity.setFull_name(createBankDetails.getFull_name());
bankDetailsEntity.setUid(createBankDetails.getUid());
bankDetailsRepository.save(bankDetailsEntity);
return null;



    }

    @Override
    public GetBankDetailsRespUID getbankdetailsofid(GetBankDetailsOfUser getBankDetailsOfUser) {

        System.out.println(getBankDetailsOfUser.getUid());
       BankDetailsEntity user =  bankDetailsRepository.getByUid(getBankDetailsOfUser.getUid());
        System.out.println(user);
       getBankDetailsRespUID.setBank_name(user.getBank_name());

       getBankDetailsRespUID.setBank_acc_type(user.getBank_acc_type());
       getBankDetailsRespUID.setAccount_no(user.getAccount_no());
       getBankDetailsRespUID.setIfsc_code(user.getIfsc_code());
       getBankDetailsRespUID.setFull_name(user.getFull_name());
       return getBankDetailsRespUID;
    }

    @Override
    public List<BankDetailsEntity> getAllBankDetails() {
        return bankDetailsRepository.findAll()   ;
    }
}
