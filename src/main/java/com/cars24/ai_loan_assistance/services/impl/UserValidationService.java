package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public boolean isValidUser(String loggedInEmail, String targetEmail, Long additionalId) {
        if (!loggedInEmail.equals(targetEmail)) {
            return false;
        }
        return userRepository.existsByEmailAndAdditionalId(targetEmail, additionalId);
    }
}
