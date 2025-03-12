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
    public String createBankDetails(long userId , CreateBankDetails createBankDetails) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        BankEntity bankEntity = new BankEntity();


        bankEntity.setBankName(createBankDetails.getBankName());

        bankEntity.setBankAccountType(createBankDetails.getBankAccountType());
        bankEntity.setAccountNumber(createBankDetails.getAccountNumber());
        bankEntity.setUser(userEntity);
        bankEntity.setIfscCode(createBankDetails.getIfscCode());
        bankEntity.setAccountHolderName(createBankDetails.getAccountHolderName());
        bankDetailsRepository.save(bankEntity);

        return null;



    }





    @Override
    public CountBankAcc countofbanks(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        List<BankEntity> bankEntityList = bankDetailsRepository.findByUserId(user.getId());

        int bankCount = bankEntityList.size();
        List<BankInfoDTO> bankDetails = bankEntityList.stream()
                .map(bank -> new BankInfoDTO(bank.getBankId(), bank.getBankName(), bank.getAccountNumber()))  // Include bankId
                .collect(Collectors.toList());

        return new CountBankAcc(bankCount, bankDetails);
    }

    @Override
    public BankFullDetails bankfulldetails(long userId, long bankid) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        BankEntity bank = bankDetailsRepository.findById(bankid)
                .orElseThrow(() -> new NotFoundException("Bank details not found!"));

        return BankFullDetails.builder()
                .bankName(bank.getBankName())
                .bankAccountType(bank.getBankAccountType())
                .ifscCode(bank.getIfscCode())
                .accountNumber(bank.getAccountNumber())
                .accountHolderName(bank.getAccountHolderName())
                .build();
    }

    @Override
    public String updatebankdetails(long userId, BankDetailsUpdateRequest request, long additional) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        BankEntity bank = bankDetailsRepository.findById(additional)
                .orElseThrow(() -> new NotFoundException("Bank details not found!"));


        if (!bank.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this bank account!");
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

        return "Bank details updated successfully!";
    }

}
