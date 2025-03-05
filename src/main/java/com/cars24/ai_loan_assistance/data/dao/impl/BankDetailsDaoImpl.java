package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.BankDetailsDao;
import com.cars24.ai_loan_assistance.data.dto.BankInfoDTO;
import com.cars24.ai_loan_assistance.data.entities.BankEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.repositories.BankDetailsRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.*;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankDetailsDaoImpl implements BankDetailsDao {

    private final BankDetailsRepository bankDetailsRepository;

    private final UserRepository userRepository;
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

    @Override
    public BankFullDetails bankfulldetails(String email, long bankid) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        BankEntity bank = bankDetailsRepository.findById(bankid)
                .orElseThrow(() -> new NotFoundException("Bank details not found!"));
        BankFullDetails bankFullDetails = new BankFullDetails();
        bankFullDetails.setBankName(bank.getBankName());
        bankFullDetails.setUser(bank.getUser());
        bankFullDetails.setBankAccountType(bank.getBankAccountType());
        bankFullDetails.setIfscCode(bank.getIfscCode());
        bankFullDetails.setAccountNumber(bank.getAccountNumber());
        bankFullDetails.setAccountHolderName(bank.getAccountHolderName());

        return bankFullDetails;
    }

    @Override
    public UpdateBankDetails updatebankdetails(String email, BankDetailsUpdateRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        BankEntity bank = bankDetailsRepository.findById(request.getBankId())
                .orElseThrow(() -> new NotFoundException("Bank details not found!"));

        if (!bank.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this bank account!");

        }
        if (request.getAccountHolderName() != null) {
            bank.setAccountHolderName(request.getAccountHolderName());
        }
        if (request.getBankAccountType() != null) {
            bank.setBankAccountType(request.getBankAccountType());
        }
        if(request.getBankName()!=null){
            bank.setBankName(request.getBankName());
        }

        bankDetailsRepository.save(bank);
        UpdateBankDetails updateBankDetails = new UpdateBankDetails();
        updateBankDetails.setBankName(request.getBankName());
        updateBankDetails.setBankAccountType(request.getBankAccountType());
        updateBankDetails.setAccountHolderName(request.getAccountHolderName());

        return updateBankDetails;
    }

}
