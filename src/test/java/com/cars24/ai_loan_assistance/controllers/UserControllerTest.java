package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.UserService;
import com.cars24.ai_loan_assistance.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;
    private ApiResponse successResponse;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        loginRequest = new LoginRequest();

        Map<String, Object> data = new HashMap<>();
        data.put("token", "sample-token");

        successResponse = new ApiResponse(HttpStatus.OK.value(), "Success", "APPUSER", true, data);
    }

    @Test
    void testSignUp_Success() {
        when(userService.registerUser(any(SignupRequest.class))).thenReturn(successResponse);
        ResponseEntity<ApiResponse> response = userController.signUp(signupRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testSignUp_Exception() {
        when(userService.registerUser(any(SignupRequest.class))).thenThrow(new RuntimeException("User already exists"));
        ResponseEntity<ApiResponse> response = userController.signUp(signupRequest);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void testLogin_Success() {
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(userService.login(any(LoginRequest.class))).thenReturn(successResponse);
        ResponseEntity<ApiResponse> response = userController.login(loginRequest, httpServletResponse);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testLogin_Exception() {
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(userService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));
        ResponseEntity<ApiResponse> response = userController.login(loginRequest, httpServletResponse);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void testGetCurrentUser_Success() {
        when(jwtUtil.extractUserId("sample-token")).thenReturn("123");
        ResponseEntity<ApiResponse> response = userController.getCurrentUser("sample-token");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testGetCurrentUser_InvalidToken() {
        when(jwtUtil.extractUserId("invalid-token")).thenThrow(new RuntimeException("Token expired"));
        ResponseEntity<ApiResponse> response = userController.getCurrentUser("invalid-token");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
    }
}
