package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.impl.UserDaoImpl;
import com.cars24.ai_loan_assistance.data.dto.UserDetailsResponseDto;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.UserDetailsMapper;
import com.cars24.ai_loan_assistance.services.UserService;
import com.cars24.ai_loan_assistance.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDaoImpl dao;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse registerUser(SignupRequest user) {
        Optional<UserEntity> userExists = userRepository.findByEmail(user.getEmail());

        if (userExists.isPresent()) {
            throw new RuntimeException("User Already Exists!!");
        }

        // Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(encodedPassword);
        userEntity.setPhone(user.getPhone());

        dao.saveUser(userEntity);

        return new ApiResponse(
                HttpStatus.OK.value(),
                "User registered successfully! Please Login",
                "APPUSER",
                true,
                null
        );
    }

    @Override
    public ApiResponse login(LoginRequest user) {
        Optional<UserEntity> userExists = userRepository.findByEmail(user.getEmail());

        if (userExists.isPresent()) {
            UserEntity userEntity = userExists.get();
            if (passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
                String id = String.valueOf(userEntity.getId());
                String name = userEntity.getName();

                String token = jwtUtil.generateToken(user.getEmail(), id);

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("token", token);
                responseData.put("id", id);
                responseData.put("name", name);

                return new ApiResponse(
                        HttpStatus.OK.value(),
                        "User Logged in Successfully",
                        "APPUSER",
                        true,
                        responseData
                );
            } else {
                throw new RuntimeException("Invalid Password");
            }
        }
        throw new RuntimeException("User doesn't exist. Please SignUp");
    }
    private final UserDetailsMapper userDetailsMapper;

//    @Transactional
//    public UserDetailsResponseDto getUserCompleteDetails(Long userId) {
//        UserEntity userEntity = userRepository.findByIdWithAllDetails(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        return userDetailsMapper.mapToUserDetailsResponse(userEntity);
//    }

}
