package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.BankDetailsDao;
import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.repositories.BankDetailsRepository;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
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
