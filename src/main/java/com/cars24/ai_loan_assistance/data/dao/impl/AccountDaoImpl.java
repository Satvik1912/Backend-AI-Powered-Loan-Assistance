package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.AccountDao;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.UserInformationEntity;
import com.cars24.ai_loan_assistance.data.repositories.UserInformationRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.KycResponse;
import com.cars24.ai_loan_assistance.data.responses.UserProfileResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {

    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;

    @Override
    public UserProfileResponse getUserProfile(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        UserProfileResponse response = new UserProfileResponse();
        response.setName(userEntity.getName());
        response.setPhone(userEntity.getPhone());
        response.setEmail(userEntity.getEmail());
        response.setAddress(userEntity.getAddress());
        return response;
    }

    @Override
    public void updateContactInfo(String email, ContactUpdateRequest request) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        if(request.getPhone() != null){
            userEntity.setPhone(request.getPhone());
        }
        if(request.getAddress() != null){
            userEntity.setAddress(request.getAddress());
        }
        userRepository.save(userEntity);
    }

    @Override
    public KycResponse getKycDetails(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        KycResponse response = new KycResponse();
        response.setAadhar(userInformationEntity.getAadhar());
        response.setPAN(userInformationEntity.getPan());
        return response;
    }
}
