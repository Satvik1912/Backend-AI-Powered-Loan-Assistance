//package com.cars24.ai_loan_assistance.services.impl;
//
//import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserValidationServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserValidationService userValidationService;
//
//    private Long loggedInUserId;
//    private Long additionalId;
//    private int promptId;
//
//    @BeforeEach
//    void setUp() {
//        loggedInUserId = 1L;
//        additionalId = 100L;
//        promptId = 10;
//    }
//
//    @Test
//    void testIsValidUser_ReturnsTrue_WhenRepositoryReturnsTrue() {
//        // Arrange
//        when(userRepository.existsByUserIdAndAdditionalId(loggedInUserId, additionalId, promptId))
//                .thenReturn(true);
//
//        // Act
//        boolean result = userValidationService.isValidUser(loggedInUserId, additionalId, promptId);
//
//        // Assert
//        assertTrue(result);
//        verify(userRepository, times(1))
//                .existsByUserIdAndAdditionalId(loggedInUserId, additionalId, promptId);
//    }
//
//    @Test
//    void testIsValidUser_ReturnsFalse_WhenRepositoryReturnsFalse() {
//        // Arrange
//        when(userRepository.existsByUserIdAndAdditionalId(loggedInUserId, additionalId, promptId))
//                .thenReturn(false);
//
//        // Act
//        boolean result = userValidationService.isValidUser(loggedInUserId, additionalId, promptId);
//
//        // Assert
//        assertFalse(result);
//        verify(userRepository, times(1))
//                .existsByUserIdAndAdditionalId(loggedInUserId, additionalId, promptId);
//    }
//}