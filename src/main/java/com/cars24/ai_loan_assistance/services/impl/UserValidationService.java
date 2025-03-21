package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public boolean isValidUser(Long loggedInUserId,  Long additionalId,int promptId) {
//        if (!loggedInUserId.equals(userId)) {
//            return false;
//        }
        return userRepository.existsByUserIdAndAdditionalId(loggedInUserId, additionalId,promptId);
    }
}
