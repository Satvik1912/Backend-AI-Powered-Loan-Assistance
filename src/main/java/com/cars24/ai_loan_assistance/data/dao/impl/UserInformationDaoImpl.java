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

    private static final String USER_INFORMATION_NOT_FOUND = "User information does not exist!";
    private static final String USER_NOT_FOUND = "User does not exist!";
    @Override
    public SalaryDetailsResponse getSalaryDetails(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException(USER_INFORMATION_NOT_FOUND));

        return SalaryDetailsResponse.builder()
                .salary(userInformationEntity.getSalary())
                .incomeType(userInformationEntity.getIncomeType())
                .build();
    }

    @Override
    public String updateSalaryDetails(long userId, SalaryUpdateRequest request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NotFoundException(USER_INFORMATION_NOT_FOUND));


        if(request.getSalary() != 0){
            userInformationEntity.setSalary(request.getSalary());
        }
        userInformationRepository.save(userInformationEntity);
        return "Your salary details have been updated successfully!";
    }

    @Override
    public int getCibil(long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NotFoundException(USER_INFORMATION_NOT_FOUND));

        return userInformationEntity.getCibil();
    }
}
