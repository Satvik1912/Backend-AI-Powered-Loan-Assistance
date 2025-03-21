//package com.cars24.ai_loan_assistance.controllers;
//
//import com.cars24.ai_loan_assistance.data.entities.CustomUserDetails;
//import com.cars24.ai_loan_assistance.data.entities.UserBot;
//import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
//import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
//import com.cars24.ai_loan_assistance.services.UserBotService;
//import com.cars24.ai_loan_assistance.services.impl.UserValidationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//public class UserBotControllerTest {
//
//    @Mock
//    private UserBotService userBotService;
//
//    @Mock
//    private UserValidationService userValidationService;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private CustomUserDetails customUserDetails;
//
//    @InjectMocks
//    private UserBotController userBotController;
//
//    private Map<String, Object> requestBody;
//    private UserBotResponse mockResponse;
//    private final Long userId = 123L;
//    private final int promptId = 456;
//    private final Long additionalId = 789L;
//
//    @BeforeEach
//    void setUp() {
//        requestBody = new HashMap<>();
//        requestBody.put("key1", "value1");
//        requestBody.put("key2", "value2");
//
//        // Create a dummy main prompt UserBot object
//        UserBot mainPrompt = new UserBot();
//        mainPrompt.setText("Main prompt text");
//        mainPrompt.setResponseText("Main response text");
//
//        // Create an empty followup list (or add dummy UserBot objects as needed)
//        List<UserBot> followupBots = new ArrayList<>();
//
//        // Define an extra action (could be null or a dummy object)
//        Object extraAction = null;
//
//        mockResponse = new UserBotResponse(mainPrompt, followupBots, extraAction);
//
//        when(authentication.getPrincipal()).thenReturn(customUserDetails);
//        when(customUserDetails.getUserId()).thenReturn(userId);
//    }
//
//    @Test
//    void handleQuery_ValidUser_ReturnsSuccess() {
//        // Arrange
//        when(userValidationService.isValidUser(userId, additionalId, promptId)).thenReturn(true);
//        when(userBotService.interact(promptId, userId, additionalId)).thenReturn(mockResponse);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleQuery(promptId, additionalId, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(200, body.getStatusCode());
//        assertEquals("Prompt retrieved successfully", body.getMessage());
//        assertEquals(true, body.isSuccess());
//        assertEquals(mockResponse, body.getData());
//
//        verify(userValidationService).isValidUser(userId, additionalId, promptId);
//        verify(userBotService).interact(promptId, userId, additionalId);
//    }
//
//    @Test
//    void handleQuery_InvalidUser_ReturnsForbidden() {
//        // Arrange
//        when(userValidationService.isValidUser(userId, additionalId, promptId)).thenReturn(false);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleQuery(promptId, additionalId, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(403, body.getStatusCode());
//        assertEquals("You are not authorized to see this data", body.getMessage());
//        assertEquals(false, body.isSuccess());
//        assertEquals(null, body.getData());
//
//        verify(userValidationService).isValidUser(userId, additionalId, promptId);
//        verify(userBotService, never()).interact(anyInt(), anyLong(), any());
//    }
//
//    @Test
//    void handleQuery_InvalidPrincipal_ReturnsForbidden() {
//        // Arrange
//        when(authentication.getPrincipal()).thenReturn("invalidPrincipal");
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleQuery(promptId, additionalId, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(403, body.getStatusCode());
//        assertEquals("Invalid Principle object", body.getMessage());
//        assertEquals(false, body.isSuccess());
//        assertEquals(null, body.getData());
//
//        verify(userValidationService, never()).isValidUser(anyLong(), any(), anyInt());
//        verify(userBotService, never()).interact(anyInt(), anyLong(), any());
//    }
//
//    @Test
//    void handleUpdate_ValidUser_ReturnsSuccess() {
//        // Arrange
//        when(userValidationService.isValidUser(userId, additionalId, promptId)).thenReturn(true);
//        when(userBotService.update(promptId, userId, requestBody, additionalId)).thenReturn(mockResponse);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleUpdate(promptId, additionalId, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(200, body.getStatusCode());
//        assertEquals("Record updated successfully", body.getMessage());
//        assertEquals(true, body.isSuccess());
//        assertEquals(mockResponse, body.getData());
//
//        verify(userValidationService).isValidUser(userId, additionalId, promptId);
//        verify(userBotService).update(promptId, userId, requestBody, additionalId);
//    }
//
//    @Test
//    void handleUpdate_InvalidUser_ReturnsForbidden() {
//        // Arrange
//        when(userValidationService.isValidUser(userId, additionalId, promptId)).thenReturn(false);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleUpdate(promptId, additionalId, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(403, body.getStatusCode());
//        assertEquals("You are not authorized to update this data", body.getMessage());
//        assertEquals(false, body.isSuccess());
//        assertEquals(null, body.getData());
//
//        verify(userValidationService).isValidUser(userId, additionalId, promptId);
//        verify(userBotService, never()).update(anyInt(), anyLong(), any(), any());
//    }
//
//    @Test
//    void handleUpdate_NullAdditional_SkipsValidation() {
//        // Arrange
//        when(userBotService.update(promptId, userId, requestBody, null)).thenReturn(mockResponse);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleUpdate(promptId, null, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(200, body.getStatusCode());
//        assertEquals("Record updated successfully", body.getMessage());
//        assertEquals(true, body.isSuccess());
//        assertEquals(mockResponse, body.getData());
//
//        verify(userValidationService, never()).isValidUser(anyLong(), any(), anyInt());
//        verify(userBotService).update(promptId, userId, requestBody, null);
//    }
//
//    @Test
//    void handleUpdate_InvalidPrincipal_ReturnsForbidden() {
//        // Arrange
//        when(authentication.getPrincipal()).thenReturn("invalidPrincipal");
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleUpdate(promptId, additionalId, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(403, body.getStatusCode());
//        assertEquals("Invalid Principal object", body.getMessage());
//        assertEquals(false, body.isSuccess());
//        assertEquals(null, body.getData());
//
//        verify(userValidationService, never()).isValidUser(anyLong(), any(), anyInt());
//        verify(userBotService, never()).update(anyInt(), anyLong(), any(), any());
//    }
//
//    @Test
//    void handleCreate_ValidUser_ReturnsSuccess() {
//        // Arrange
//        when(userValidationService.isValidUser(userId, null, promptId)).thenReturn(true);
//        when(userBotService.create(promptId, userId, requestBody)).thenReturn(mockResponse);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleCreate(promptId, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(200, body.getStatusCode());
//        assertEquals("Record created successfully", body.getMessage());
//        assertEquals(true, body.isSuccess());
//        assertEquals(mockResponse, body.getData());
//
//        verify(userValidationService).isValidUser(userId, null, promptId);
//        verify(userBotService).create(promptId, userId, requestBody);
//    }
//
//    @Test
//    void handleCreate_InvalidUser_ReturnsForbidden() {
//        // Arrange
//        when(userValidationService.isValidUser(userId, null, promptId)).thenReturn(false);
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleCreate(promptId, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(403, body.getStatusCode());
//        assertEquals("You are not authorized to create this data", body.getMessage());
//        assertEquals(false, body.isSuccess());
//        assertEquals(null, body.getData());
//
//        verify(userValidationService).isValidUser(userId, null, promptId);
//        verify(userBotService, never()).create(anyInt(), anyLong(), any());
//    }
//
//    @Test
//    void handleCreate_InvalidPrincipal_ReturnsForbidden() {
//        // Arrange
//        when(authentication.getPrincipal()).thenReturn("invalidPrincipal");
//
//        // Act
//        ResponseEntity<ApiResponse> response = userBotController.handleCreate(promptId, requestBody, authentication);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//        ApiResponse body = response.getBody();
//        assertEquals(403, body.getStatusCode());
//        assertEquals("Invalid Principal object", body.getMessage());
//        assertEquals(false, body.isSuccess());
//        assertEquals(null, body.getData());
//
//        verify(userValidationService, never()).isValidUser(anyLong(), any(), anyInt());
//        verify(userBotService, never()).create(anyInt(), anyLong(), any());
//    }
//}