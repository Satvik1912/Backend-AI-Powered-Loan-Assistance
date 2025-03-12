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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {

    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;

    @Override
    public UserProfileResponse getUserProfile(long userId) {
        log.info("[getUserProfile] in dao");
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        return UserProfileResponse.builder()
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
                .build();
    }

    @Override
    public String updateContactInfo(long userId, ContactUpdateRequest request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        if(request.getPhone() != null){
            userEntity.setPhone(request.getPhone());
        }
        if(request.getAddress() != null){
            userEntity.setAddress(request.getAddress());
        }
        userRepository.save(userEntity);
        return "Contact details updated successfully!";
    }

    @Override
    public KycResponse getKycDetails(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));

        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        return KycResponse.builder()
                .Aadhar(userInformationEntity.getAadhar())
                .PAN(userInformationEntity.getPan())
                .build();
    }
}
