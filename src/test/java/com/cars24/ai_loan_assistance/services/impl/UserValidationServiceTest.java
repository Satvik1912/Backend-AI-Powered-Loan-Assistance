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
//    private long userId;
//    private Long additionalId;
//    private int promptId;
//
//    @BeforeEach
//    void setUp() {
//        loggedInUserId = 1L;
//        userId = 1L;
//        additionalId = 100L;
//        promptId = 10;
//    }
//
//    @Test
//    void testIsValidUser_Success() {
//        when(userRepository.existsByUserIdAndAdditionalId(userId, additionalId, promptId)).thenReturn(true);
//
//        boolean result = userValidationService.isValidUser(loggedInUserId, userId, additionalId, promptId);
//
//        assertTrue(result);
//        verify(userRepository, times(1)).existsByUserIdAndAdditionalId(userId, additionalId, promptId);
//    }
//
//    @Test
//    void testIsValidUser_Fails_WhenUserIdMismatch() {
//        boolean result = userValidationService.isValidUser(2L, userId, additionalId, promptId);
//
//        assertFalse(result);
//        verify(userRepository, never()).existsByUserIdAndAdditionalId(anyLong(), anyLong(), anyInt());
//    }
//
//    @Test
//    void testIsValidUser_Fails_WhenRepositoryReturnsFalse() {
//        when(userRepository.existsByUserIdAndAdditionalId(userId, additionalId, promptId)).thenReturn(false);
//
//        boolean result = userValidationService.isValidUser(loggedInUserId, userId, additionalId, promptId);
//
//        assertFalse(result);
//        verify(userRepository, times(1)).existsByUserIdAndAdditionalId(userId, additionalId, promptId);
//    }
//}
