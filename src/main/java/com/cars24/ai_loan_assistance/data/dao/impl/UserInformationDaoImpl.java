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
    public SalaryDetailsResponse getSalaryDetails(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        SalaryDetailsResponse response = new SalaryDetailsResponse();
        response.setSalary(userInformationEntity.getSalary());
        response.setIncomeType(userInformationEntity.getIncomeType());
        return response;
    }

    @Override
    public String updateSalaryDetails(String email, SalaryUpdateRequest request) {
        UserEntity userEntity = userRepository.findByEmail(email)
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
    public int getCibil(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist!"));
        UserInformationEntity userInformationEntity = userInformationRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NotFoundException("User information does not exist!"));

        return userInformationEntity.getCibil();
    }
}
