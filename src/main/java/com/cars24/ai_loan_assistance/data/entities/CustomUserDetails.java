package com.cars24.ai_loan_assistance.data.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    // Constructor
    public CustomUserDetails(Long userId, String username, String password, List<SimpleGrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
