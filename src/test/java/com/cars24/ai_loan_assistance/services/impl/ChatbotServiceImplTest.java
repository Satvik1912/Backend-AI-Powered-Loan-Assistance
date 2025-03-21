package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.*;
import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.UserProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatbotServiceImplTest {

    @Mock
    private AccountDao accountDao;

    @Mock
    private LoanDao loanDao;

    @Mock
    private UserInformationDao userInformationDao;

    @Mock
    private BankDetailsDao bankDetailsDao;

    @Mock
    private EmiServiceImpl emiServiceimpl;

    @Mock
    private Validator validator;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private ChatbotServiceImpl chatbotService;

    @BeforeEach
    void setUp() {
        // Explicitly set the validator
        chatbotService.UserBotServiceImpl(validator);

        // Don't set up validator here - we'll do it in the specific tests that need it
    }

    @Test
    void testProcessQuery_AccProfile() {
        // Arrange
        long userId = 123L;
        UserProfileResponse mockResponse = new UserProfileResponse();
        mockResponse.setName("John Doe");
        mockResponse.setEmail("john.doe@example.com");
        mockResponse.setPhone("9876543210");
        mockResponse.setAddress("123 Street");

        when(accountDao.getUserProfile(userId)).thenReturn(mockResponse);

        // Act
        Object result = chatbotService.processQuery(userId, ChatbotIntent.ACC_PROFILE, null);

        // Assert
        assertNotNull(result);
        assertEquals(mockResponse, result);
        verify(accountDao, times(1)).getUserProfile(userId);
    }

    @Test
    void testProcessUpdate_AccContact() {
        // Arrange
        long userId = 123L;
        Map<String, Object> request = Map.of("phone", "9876543210");

        // Setup validator for this specific test
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        // Setup the return value from the DAO
        when(accountDao.updateContactInfo(eq(userId), any(ContactUpdateRequest.class))).thenReturn("Success");

        // Act
        String result = chatbotService.processUpdate(userId, ChatbotIntent.ACC_CONTACT, request, null);

        // Assert
        assertEquals("Success", result);
        verify(accountDao, times(1)).updateContactInfo(eq(userId), any(ContactUpdateRequest.class));
    }

    @Test
    void testProcessCreate_BankAdd() {
        // Arrange
        long userId = 123L;
        Map<String, Object> request = Map.of("accountNumber", "123456789", "ifscCode", "ABCD0001234");

        // Setup validator for this specific test
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        // Setup the return value from the DAO
        when(bankDetailsDao.createBankDetails(eq(userId), any(CreateBankDetails.class))).thenReturn("Bank Created");

        // Act
        String result = chatbotService.processCreate(userId, ChatbotIntent.BANK_ADD, request);

        // Assert
        assertEquals("Bank Created", result);
        verify(bankDetailsDao, times(1)).createBankDetails(eq(userId), any(CreateBankDetails.class));
    }
}