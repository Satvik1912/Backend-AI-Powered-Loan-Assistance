package com.cars24.ai_loan_assistance.data.dao.impl;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.responses.*;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanDaoImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanDaoImpl loanDao;

    private LoanEntity loanEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);

        loanEntity = new LoanEntity();
        loanEntity.setLoanId(100L);
        loanEntity.setUser(userEntity);
        loanEntity.setStatus(LoanStatus.DISBURSED);
        loanEntity.setType(LoanType.HOME_LOAN);
        loanEntity.setPrincipal(500000.0);
        loanEntity.setInterest(5.5);
        loanEntity.setTenure(15.0);
        loanEntity.setDisbursedDate(LocalDate.now());
    }

    @Test
    void testStoreLoan() {
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loanEntity);
        LoanEntity savedLoan = loanDao.store(loanEntity);
        assertNotNull(savedLoan);
        assertEquals(loanEntity.getLoanId(), savedLoan.getLoanId());
    }

    @Test
    void testGetLoan_Success() {
        when(loanRepository.findById(100L)).thenReturn(Optional.of(loanEntity));
        LoanEntity retrievedLoan = loanDao.getLoan(100L);
        assertNotNull(retrievedLoan);
        assertEquals(100L, retrievedLoan.getLoanId());
    }

    @Test
    void testGetLoan_NotFound() {
        when(loanRepository.findById(200L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> loanDao.getLoan(200L));
    }

    @Test
    void testGetLoans() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<LoanEntity> page = new PageImpl<>(List.of(loanEntity));
        when(loanRepository.findAll(pageable)).thenReturn(page);
        Page<LoanEntity> result = loanDao.getLoans(0, 10);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetActiveLoans_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(loanRepository.findByUserIdAndStatus(1L, LoanStatus.DISBURSED)).thenReturn(List.of(loanEntity));
        ActiveLoansResponse response = loanDao.getActiveLoans(1L);
        assertEquals(1, response.getNumberOfLoans());
    }

    @Test
    void testGetActiveLoans_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> loanDao.getActiveLoans(2L));
    }

    @Test
    void testGetLoansByUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(loanRepository.findByUserId(1L)).thenReturn(List.of(loanEntity));
        GetLoansResponse response = loanDao.getLoansByUser(1L);
        assertEquals(1, response.getNumberOfLoans());
    }

    @Test
    void testGetLoansByUser_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> loanDao.getLoansByUser(2L));
    }

    @Test
    void testGetActiveLoansDetails_Success() {
        when(loanRepository.getLoanDetailsById(1L, 100L)).thenReturn(loanEntity);
        ActiveLoansDetailsResponse response = loanDao.getActiveLoansDetails(1L, 100L);
        assertEquals(100L, response.getLoanId());
        assertEquals(LoanType.HOME_LOAN, response.getLoanType());
    }
}
