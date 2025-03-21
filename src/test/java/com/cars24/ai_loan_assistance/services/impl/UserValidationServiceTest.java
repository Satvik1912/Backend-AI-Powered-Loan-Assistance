package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidationService userValidationService;

    private Long loggedInUserId;
    private Long userId;
    private Long additionalId;
    private int promptId;

    @BeforeEach
    void setUp() {
        loggedInUserId = 1L;
        userId = 1L;
        additionalId = 100L;
        promptId = 10;
    }

    @Test
    void testIsValidUser_Success() {
        // Arrange: Stub the repository to return true for valid input.
        when(userRepository.existsByUserIdAndAdditionalId(loggedInUserId, userId, additionalId, promptId))
                .thenReturn(true);

        // Act: Call the service method.
        boolean result = userValidationService.isValidUser(loggedInUserId, userId, additionalId, promptId);

        // Assert: The result should be true and the repository should be called exactly once.
        assertTrue(result);
        verify(userRepository, times(1))
                .existsByUserIdAndAdditionalId(loggedInUserId, userId, additionalId, promptId);
    }

    @Test
    void testIsValidUser_Fails_WhenRepositoryReturnsFalse() {
        // Arrange: Stub the repository to return false.
        when(userRepository.existsByUserIdAndAdditionalId(loggedInUserId, userId, additionalId, promptId))
                .thenReturn(false);

        // Act: Call the service method.
        boolean result = userValidationService.isValidUser(loggedInUserId, userId, additionalId, promptId);

        // Assert: The result should be false and the repository should be called exactly once.
        assertFalse(result);
        verify(userRepository, times(1))
                .existsByUserIdAndAdditionalId(loggedInUserId, userId, additionalId, promptId);
    }

    @Test
    void testIsValidUser_Fails_WhenLoggedInUserIdDiffers() {
        // Arrange: If loggedInUserId is different from userId, assume the repository returns false.
        Long invalidLoggedInUserId = 2L;
        when(userRepository.existsByUserIdAndAdditionalId(invalidLoggedInUserId, userId, additionalId, promptId))
                .thenReturn(false);

        // Act: Call the service method with the mismatched loggedInUserId.
        boolean result = userValidationService.isValidUser(invalidLoggedInUserId, userId, additionalId, promptId);

        // Assert: The result should be false.
        assertFalse(result);
        verify(userRepository, times(1))
                .existsByUserIdAndAdditionalId(invalidLoggedInUserId, userId, additionalId, promptId);
    }
}
