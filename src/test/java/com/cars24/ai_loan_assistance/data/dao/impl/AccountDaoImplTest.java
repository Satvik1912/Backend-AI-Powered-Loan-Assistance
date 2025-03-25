package com.cars24.ai_loan_assistance.data.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.UserInformationEntity;
import com.cars24.ai_loan_assistance.data.repositories.UserInformationRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.KycResponse;
import com.cars24.ai_loan_assistance.data.responses.UserProfileResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountDaoImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserInformationRepository userInformationRepository;

    @InjectMocks
    private AccountDaoImpl accountDao;

    private UserEntity userEntity;
    private UserInformationEntity userInformationEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setPhone("1234567890");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setAddress("123 Street, City");

        userInformationEntity = new UserInformationEntity();
        userInformationEntity.setAadhar("123456789012");
        userInformationEntity.setPan("ABCDE1234F");
    }

    @Test
    void testGetUserProfile_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserProfileResponse response = accountDao.getUserProfile(1L);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("1234567890", response.getPhone());
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals("123 Street, City", response.getAddress());
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountDao.getUserProfile(1L));
    }

    @Test
    void testUpdateContactInfo_Success() {
        ContactUpdateRequest request = new ContactUpdateRequest();
        request.setPhone("9876543210");
        request.setAddress("456 Avenue, City");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        String response = accountDao.updateContactInfo(1L, request);
        assertEquals("Contact details updated successfully!", response);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdateContactInfo_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountDao.updateContactInfo(1L, new ContactUpdateRequest()));
    }

    @Test
    void testGetKycDetails_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(Optional.of(userInformationEntity));

        KycResponse response = accountDao.getKycDetails(1L);

        assertNotNull(response);
        assertEquals("123456789012", response.getAadhar());
        assertEquals("ABCDE1234F", response.getPan());
    }

    @Test
    void testGetKycDetails_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountDao.getKycDetails(1L));
    }

    @Test
    void testGetKycDetails_UserInformationNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountDao.getKycDetails(1L));
    }
}
