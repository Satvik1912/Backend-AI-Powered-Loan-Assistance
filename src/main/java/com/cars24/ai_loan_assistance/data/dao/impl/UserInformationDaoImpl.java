package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.UserInformationDao;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.UserInformationEntity;
import com.cars24.ai_loan_assistance.data.repositories.UserInformationRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.SalaryDetailsResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInformationDaoImpl implements UserInformationDao {

    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;

    @Override
    public SalaryDetailsResponse getSalaryDetails(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        return SalaryDetailsResponse.builder()
                .salary(userInformationEntity.getSalary())
                .incomeType(userInformationEntity.getIncomeType())
                .build();
    }

    @Override
    public String updateSalaryDetails(long userId, SalaryUpdateRequest request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        if(request.getSalary() != 0){
            userInformationEntity.setSalary(request.getSalary());
        }
        if(request.getIncomeType() != null){
            userInformationEntity.setIncomeType(request.getIncomeType());
        }
        userInformationRepository.save(userInformationEntity);
        return "Your salary details have been updated successfully!";
    }

    @Override
    public int getCibil(long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        return userInformationEntity.getCibil();
    }
}
