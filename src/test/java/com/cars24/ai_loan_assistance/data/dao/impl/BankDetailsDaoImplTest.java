package com.cars24.ai_loan_assistance.data.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cars24.ai_loan_assistance.data.entities.BankEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.repositories.BankDetailsRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.responses.*;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BankDetailsDaoImplTest {

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankDetailsDaoImpl bankDetailsDao;

    private UserEntity user;
    private BankEntity bank;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("John Doe");

        bank = new BankEntity();
        bank.setBankId(1L);
        bank.setBankName("ABC Bank");
        bank.setAccountNumber("1234567890");
        bank.setUser(user);
    }

    @Test
    void createBankDetails_Success() {
        CreateBankDetails request = new CreateBankDetails();
        request.setBankName("ABC Bank");
        request.setAccountNumber("1234567890");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.save(any(BankEntity.class))).thenReturn(bank);

        assertNull(bankDetailsDao.createBankDetails(1L, request));
    }

    @Test
    void createBankDetails_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        CreateBankDetails request = new CreateBankDetails();
        assertThrows(NotFoundException.class, () -> bankDetailsDao.createBankDetails(1L, request));
    }

    @Test
    void countofbanks_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.findByUserId(1L)).thenReturn(List.of(bank));

        CountBankAcc result = bankDetailsDao.countofbanks(1L);
        assertEquals(1, result.getBankCount());
    }

    @Test
    void countofbanks_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bankDetailsDao.countofbanks(1L));
    }

    @Test
    void bankfulldetails_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.of(bank));

        BankFullDetails result = bankDetailsDao.bankfulldetails(1L, 1L);
        assertEquals("ABC Bank", result.getBankName());
    }

    @Test
    void bankfulldetails_BankNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bankDetailsDao.bankfulldetails(1L, 1L));
    }

    @Test
    void updatebankdetails_Success() {
        BankDetailsUpdateRequest request = new BankDetailsUpdateRequest();
        request.setBankName("XYZ Bank");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(bankDetailsRepository.save(any(BankEntity.class))).thenReturn(bank);

        String result = bankDetailsDao.updatebankdetails(1L, request, 1L);
        assertEquals("Bank details updated successfully!", result);
    }

    @Test
    void updatebankdetails_BankNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        BankDetailsUpdateRequest request = new BankDetailsUpdateRequest();
        assertThrows(NotFoundException.class, () -> bankDetailsDao.updatebankdetails(1L, request, 1L));
    }

    @Test
    void updatebankdetails_Unauthorized() {
        UserEntity otherUser = new UserEntity();
        otherUser.setId(2L);
        bank.setUser(otherUser);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.of(bank));

        BankDetailsUpdateRequest request = new BankDetailsUpdateRequest();
        assertThrows(ResponseStatusException.class, () -> bankDetailsDao.updatebankdetails(1L, request, 1L));
    }
}
