package com.cars24.ai_loan_assistance.services;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CustomUserDetails extends User {

    private final Long userId;

    public CustomUserDetails(UserEntity user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
        this.userId = user.getId(); // Store the user ID
    }

    // Getter for userId
    public Long getUserId() {
        return userId;
    }
}
