package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.requests.OllamaRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.FastAPIOllamaService;
import com.cars24.ai_loan_assistance.services.UserDetailsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
    class UserAllDetailsControllerTest {

    @Mock
    private UserDetailsMapper userService;

    @Mock
    private FastAPIOllamaService fastAPIOllamaService;

    @InjectMocks
    private UserAllDetailsController userAllDetailsController;

    private OllamaRequest ollamaRequest;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        ollamaRequest = new OllamaRequest();
        ollamaRequest.setUserQuery("Tell me about this user");
    }

    @Test
    void testGetUserDetails_Successful() {
        // Arrange
        String expectedUserData = "John Doe is a 30-year-old software engineer.";
        String expectedResponse = "Additional information about John Doe.";

        when(userService.getUserDetailsAsParagraph(userId))
                .thenReturn(expectedUserData);
        when(fastAPIOllamaService.callFastAPI(ollamaRequest.getUserQuery(), expectedUserData))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> responseEntity = userAllDetailsController.getUserDetails(userId, ollamaRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ApiResponse apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals(HttpStatus.OK.value(), apiResponse.getStatusCode());
        assertEquals("Response generated successfully!", apiResponse.getMessage());
        assertEquals("Ollama_Service", apiResponse.getService());
        assertTrue(apiResponse.isSuccess());
        assertEquals(expectedResponse, apiResponse.getData());

        // Verify interactions
        verify(userService).getUserDetailsAsParagraph(userId);
        verify(fastAPIOllamaService).callFastAPI(ollamaRequest.getUserQuery(), expectedUserData);
    }

    @Test
    void testGetUserDetails_EmptyUserQuery() {
        // Arrange
        userId = 1L;
        ollamaRequest.setUserQuery("");

        String expectedUserData = "John Doe is a 30-year-old software engineer.";
        String expectedResponse = "Additional information about John Doe.";

        when(userService.getUserDetailsAsParagraph(userId))
                .thenReturn(expectedUserData);
        when(fastAPIOllamaService.callFastAPI(ollamaRequest.getUserQuery(), expectedUserData))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> responseEntity = userAllDetailsController.getUserDetails(userId, ollamaRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ApiResponse apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.isSuccess());
        assertNotNull(apiResponse.getData());
    }

    @Test
    void testGetUserDetails_DifferentUserIdTypes() {
        // Test with various user ID types
        Long[] testUserIds = {1L, 999999L, Long.MAX_VALUE};

        for (Long testUserId : testUserIds) {
            // Arrange
            ollamaRequest.setUserQuery("Tell me about user " + testUserId);
            String expectedUserData = "User details for ID " + testUserId;
            String expectedResponse = "Response for user " + testUserId;

            when(userService.getUserDetailsAsParagraph(testUserId))
                    .thenReturn(expectedUserData);
            when(fastAPIOllamaService.callFastAPI(ollamaRequest.getUserQuery(), expectedUserData))
                    .thenReturn(expectedResponse);

            // Act
            ResponseEntity<ApiResponse> responseEntity = userAllDetailsController.getUserDetails(testUserId, ollamaRequest);

            // Assert
            assertNotNull(responseEntity);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            ApiResponse apiResponse = responseEntity.getBody();
            assertNotNull(apiResponse);
            assertTrue(apiResponse.isSuccess());
            assertEquals(expectedResponse, apiResponse.getData());

            // Verify interactions
            verify(userService).getUserDetailsAsParagraph(testUserId);
            verify(fastAPIOllamaService).callFastAPI(ollamaRequest.getUserQuery(), expectedUserData);
        }
    }

    @Test
    void testGetUserDetails_LongUserQuery() {
        // Arrange
        String longQuery = "A very long user query that exceeds typical query length and tests the system's ability to handle extensive input strings for user interactions.";
        ollamaRequest.setUserQuery(longQuery);

        String expectedUserData = "John Doe is a 30-year-old software engineer.";
        String expectedResponse = "Comprehensive response to the lengthy query.";

        when(userService.getUserDetailsAsParagraph(userId))
                .thenReturn(expectedUserData);
        when(fastAPIOllamaService.callFastAPI(ollamaRequest.getUserQuery(), expectedUserData))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ApiResponse> responseEntity = userAllDetailsController.getUserDetails(userId, ollamaRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ApiResponse apiResponse = responseEntity.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.isSuccess());
        assertEquals(expectedResponse, apiResponse.getData());
    }

    @Test
    void testGetUserDetails_MultipleConsecutiveCalls() {
        // Simulate multiple calls with different parameters
        for (int i = 0; i < 5; i++) {
            Long currentUserId = (long) (i + 1);
            ollamaRequest.setUserQuery("Query for user " + currentUserId);

            String expectedUserData = "User details for ID " + currentUserId;
            String expectedResponse = "Response for user " + currentUserId;

            when(userService.getUserDetailsAsParagraph(currentUserId))
                    .thenReturn(expectedUserData);
            when(fastAPIOllamaService.callFastAPI(ollamaRequest.getUserQuery(), expectedUserData))
                    .thenReturn(expectedResponse);

            // Act
            ResponseEntity<ApiResponse> responseEntity = userAllDetailsController.getUserDetails(currentUserId, ollamaRequest);

            // Assert
            assertNotNull(responseEntity);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            ApiResponse apiResponse = responseEntity.getBody();
            assertNotNull(apiResponse);
            assertTrue(apiResponse.isSuccess());
            assertEquals(expectedResponse, apiResponse.getData());
        }
    }
}