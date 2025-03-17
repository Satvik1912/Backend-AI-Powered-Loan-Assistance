package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.CustomUserDetails;
import com.cars24.ai_loan_assistance.data.entities.UserBot;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
import com.cars24.ai_loan_assistance.services.UserBotService;
import com.cars24.ai_loan_assistance.services.impl.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBotControllerTest {

    @Mock
    private UserBotService userBotService;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserBotController userBotController;

    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new CustomUserDetails(1L, "testUser", "password");
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void testHandleQuery_AuthorizedUser() {
        int promptId = 1;
        long userId = 1L;
        long additional = 2L;

        // Fix: Provide a valid UserBot object instead of null
        UserBot userBot = new UserBot(); // Assuming UserBot has a no-argument constructor
// Set the required properties manually if needed

        UserBotResponse mockResponse = new UserBotResponse(userBot, Collections.emptyList(), null);

       // UserBotResponse mockResponse = new UserBotResponse(new UserBot("Sample response"), Collections.emptyList(), null);

        when(userValidationService.isValidUser(userDetails.getUserId(), userId, additional, promptId)).thenReturn(true);
        when(userBotService.interact(promptId, userId, additional)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse> response = userBotController.handleQuery(promptId, userId, additional, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testHandleQuery_UnauthorizedUser() {
        int promptId = 1;
        long userId = 2L;
        long additional = 2L;

        when(userValidationService.isValidUser(userDetails.getUserId(), userId, additional, promptId)).thenReturn(false);

        ResponseEntity<ApiResponse> response = userBotController.handleQuery(promptId, userId, additional, authentication);

        assertEquals(403, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void testHandleUpdate_AuthorizedUser() {
        int promptId = 1;
        long userId = 1L;
        Map<String, Object> request = Map.of("key", "value");

        // Fix: Provide a valid UserBot object instead of null
        UserBot userBot = new UserBot(); // Assuming UserBot has a no-argument constructor
// Set the required properties manually if needed

        UserBotResponse mockResponse = new UserBotResponse(userBot, Collections.emptyList(), null);

        //UserBotResponse mockResponse = new UserBotResponse(new UserBot("Updated text"), Collections.emptyList(), null);

        when(userBotService.update(promptId, userId, request, null)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse> response = userBotController.handleUpdate(promptId, userId, null, request, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testHandleCreate_AuthorizedUser() {
        int promptId = 1;
        long userId = 1L;
        Map<String, Object> request = Map.of("key", "value");

        // Fix: Provide a valid UserBot object instead of null
        UserBot userBot = new UserBot(); // Assuming UserBot has a no-argument constructor
// Set the required properties manually if needed

        UserBotResponse mockResponse = new UserBotResponse(userBot, Collections.emptyList(), null);

        //UserBotResponse mockResponse = new UserBotResponse(new UserBot("Created response"), Collections.emptyList(), null);

        when(userValidationService.isValidUser(userDetails.getUserId(), userId, null, promptId)).thenReturn(true);
        when(userBotService.create(promptId, userId, request)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse> response = userBotController.handleCreate(promptId, userId, request, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }
}
