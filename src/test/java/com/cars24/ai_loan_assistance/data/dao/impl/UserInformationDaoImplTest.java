package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.dao.impl.UserInformationDaoImpl;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.UserInformationEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.IncomeType;
import com.cars24.ai_loan_assistance.data.repositories.UserInformationRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.SalaryDetailsResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInformationDaoImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserInformationRepository userInformationRepository;

    @InjectMocks
    private UserInformationDaoImpl userInformationDao;

    private UserEntity userEntity;
    private UserInformationEntity userInformationEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");

        userInformationEntity = new UserInformationEntity();
        userInformationEntity.setUser(userEntity);
        userInformationEntity.setSalary(50000.0);
        userInformationEntity.setIncomeType(IncomeType.SALARIED);
        userInformationEntity.setCibil(750);
    }

    @Test
    void testGetSalaryDetails_Success() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(userInformationEntity));

        SalaryDetailsResponse response = userInformationDao.getSalaryDetails(1L);

        assertEquals(50000.0, response.getSalary());
        assertEquals(IncomeType.SALARIED, response.getIncomeType());
    }

    @Test
    void testGetSalaryDetails_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userInformationDao.getSalaryDetails(1L));
    }

    @Test
    void testGetSalaryDetails_UserInfoNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userInformationDao.getSalaryDetails(1L));
    }

    @Test
    void testUpdateSalaryDetails_Success() {
        SalaryUpdateRequest request = new SalaryUpdateRequest();
        request.setSalary(60000.0);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(userInformationEntity));

        String result = userInformationDao.updateSalaryDetails(1L, request);

        assertEquals("Your salary details have been updated successfully!", result);
        assertEquals(60000.0, userInformationEntity.getSalary());
        verify(userInformationRepository, times(1)).save(userInformationEntity);
    }

    @Test
    void testUpdateSalaryDetails_UserNotFound() {
        SalaryUpdateRequest request = new SalaryUpdateRequest();
        request.setSalary(60000.0);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userInformationDao.updateSalaryDetails(1L, request));
    }

    @Test
    void testUpdateSalaryDetails_UserInfoNotFound() {
        SalaryUpdateRequest request = new SalaryUpdateRequest();
        request.setSalary(60000.0);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userInformationDao.updateSalaryDetails(1L, request));
    }

    @Test
    void testGetCibil_Success() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(java.util.Optional.of(userInformationEntity));

        int cibilScore = userInformationDao.getCibil(1L);

        assertEquals(750, cibilScore);
    }

    @Test
    void testGetCibil_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userInformationDao.getCibil(1L));
    }

    @Test
    void testGetCibil_UserInfoNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userInformationRepository.findByUserId(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userInformationDao.getCibil(1L));
    }
}
