package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.BankDetailsDao;
import com.cars24.ai_loan_assistance.data.dto.BankInfoDTO;
import com.cars24.ai_loan_assistance.data.entities.BankDetailsEntity;
import com.cars24.ai_loan_assistance.data.entities.BankEntity;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.repositories.BankDetailsRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.GetBankDetailsOfUser;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.CountBankAcc;
import com.cars24.ai_loan_assistance.data.responses.GetBankDetailsRespUID;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankDetailsDaoImpl implements BankDetailsDao {

    private final BankDetailsRepository bankDetailsRepository;

    UserRepository userRepository;
    GetBankDetailsRespUID getBankDetailsRespUID = new GetBankDetailsRespUID();
    @Override
    public String createBankDetails(String email , CreateBankDetails createBankDetails) {

        BankEntity bankEntity = new BankEntity();


        bankEntity.setBankName(createBankDetails.getBankName());

        bankEntity.setBankAccountType(createBankDetails.getBankAccountType());
        bankEntity.setAccountNumber(createBankDetails.getAccountNumber());
        bankEntity.setUser(createBankDetails.getUser());
        bankEntity.setIfscCode(createBankDetails.getIfscCode());
        bankEntity.setAccountHolderName(createBankDetails.getAccountHolderName());
        bankDetailsRepository.save(bankEntity);

        return null;



    }

    @Override
    public GetBankDetailsRespUID getbankdetailsofid(GetBankDetailsOfUser getBankDetailsOfUser) {

        System.out.println(getBankDetailsOfUser.getUid());
//       BankDetailsEntity user =  bankDetailsRepository.getByUid(getBankDetailsOfUser.getUid());
//        System.out.println(user);
//       getBankDetailsRespUID.setBank_name(user.getBank_name());
//
//       getBankDetailsRespUID.setBank_acc_type(user.getBank_acc_type());
//       getBankDetailsRespUID.setAccount_no(user.getAccount_no());
//       getBankDetailsRespUID.setIfsc_code(user.getIfsc_code());
//       getBankDetailsRespUID.setFull_name(user.getFull_name());
//       return getBankDetailsRespUID;
        return null;
    }

    @Override
    public List<BankDetailsEntity> getAllBankDetails() {
//        return bankDetailsRepository.findAll()   ;
        return null;
    }

    @Override
    public CountBankAcc countofbanks(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        List<BankEntity> bankEntityList = bankDetailsRepository.findByUserId(user.getId());

        int bankCount = bankEntityList.size();
        List<BankInfoDTO> bankDetails = bankEntityList.stream()
                .map(bank -> new BankInfoDTO(bank.getBankId(), bank.getBankName(), bank.getAccountNumber()))  // Include bankId
                .collect(Collectors.toList());

        return new CountBankAcc(bankCount, bankDetails);
    }

}
