package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.impl.UserDaoImpl;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.UserService;
import com.cars24.ai_loan_assistance.util.JwtUtil;
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
            return generateApiResponse(HttpStatus.BAD_REQUEST.value(), false, "User Already Exists", null, "APPUSER");
        }

        // Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(user.getRole());
        userEntity.setPhone(user.getPhone());

        dao.saveUser(userEntity);

        return generateApiResponse(HttpStatus.OK.value(), true, "User registered successfully", null, "APPUSER");
    }

    @Override
    public ApiResponse login(LoginRequest user) {
        Optional<UserEntity> userExists = userRepository.findByEmail(user.getEmail());

        if (userExists.isPresent()) {
            UserEntity userEntity = userExists.get();
            if (passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
                String id = userEntity.getId();
                String role = userEntity.getRole();
                String name = userEntity.getName();

                String token = jwtUtil.generateToken(user.getEmail(), id, role);

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("token", token);
                responseData.put("id", id);
                responseData.put("name", name);
                responseData.put("role", role);

                return generateApiResponse(HttpStatus.OK.value(), true, "User Logged in successfully", responseData, "APPUSER");
            } else {
                return generateApiResponse(HttpStatus.UNAUTHORIZED.value(), false, "Invalid Username or Password", null, "APPUSER");
            }
        }
        return generateApiResponse(HttpStatus.BAD_REQUEST.value(), false, "User doesn't exist. Please SignUp", null, "APPUSER");
    }

    private ApiResponse generateApiResponse(int statusCode, boolean success, String message, Object data, String service) {
        return new ApiResponse(statusCode, message, service, success, data);
    }

}
