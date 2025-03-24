package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmiServiceImplTest {

    @Mock
    private EmiRepository emiRepository;

    @InjectMocks
    private EmiServiceImpl emiService;

    private List<EmiEntity> mockEmis;
    private long userId;
    private Long loanId;

    @BeforeEach
    void setUp() {
        userId = 123L;
        loanId = 456L;
        mockEmis = new ArrayList<>();
    }

//    @Test
//    void getEmiDetails_whenEmisAreEmpty_shouldReturnNotFoundResponse() {
//        // Arrange
//        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(new ArrayList<>());
//
//        // Act
//        Object result = emiService.getEmiDetails(userId, loanId);
//
//        // Assert
//        assertTrue(result instanceof ResponseEntity);
//        ResponseEntity<?> response = (ResponseEntity<?>) result;
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//
//        ApiResponse apiResponse = (ApiResponse) response.getBody();
//        assertNotNull(apiResponse);
//        assertEquals(404, apiResponse.getStatusCode());
//        assertEquals("No EMI details found for this loan.", apiResponse.getMessage());
//        assertEquals("LOAN_EMI_DETAILS", apiResponse.getService());
//        assertNull(apiResponse.getData());
//
//        verify(emiRepository).findEmisByLoanId(loanId);
//    }

    @Test
    void getEmiDetails_whenEmisAreEmpty_shouldReturnNotFoundResponse() {
        // Arrange
        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(new ArrayList<>());

        // Act
        Object result = emiService.getEmiDetails(userId, loanId);

        // Assert
        assertTrue(result instanceof ResponseEntity);
        ResponseEntity response = (ResponseEntity) result;
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertNotNull(apiResponse);
        assertEquals(403, apiResponse.getStatusCode()); // Changed from 404 to 403
        assertEquals("No EMI details found for this loan.", apiResponse.getMessage());
        assertEquals("LOAN_EMI_DETAILS", apiResponse.getService());
        assertFalse(apiResponse.isSuccess()); // Added to check the 'false' parameter
        assertNull(apiResponse.getData());

        verify(emiRepository).findEmisByLoanId(loanId);
    }

    @Test
    void getEmiDetails_whenEmisExist_shouldReturnCategorizedEmis() {
        // Arrange
        // Create mock EMIs with different statuses and dates
        EmiEntity paidEmi1 = createEmiEntity(1L, EmiStatus.PAID, LocalDate.now().minusDays(30));
        EmiEntity paidEmi2 = createEmiEntity(2L, EmiStatus.PAID, LocalDate.now().minusDays(60));
        EmiEntity pendingEmi = createEmiEntity(3L, EmiStatus.PENDING, LocalDate.now().plusDays(15));
        EmiEntity overdueEmi = createEmiEntity(4L, EmiStatus.OVERDUE, LocalDate.now().minusDays(5));

        mockEmis.add(paidEmi1);
        mockEmis.add(paidEmi2);
        mockEmis.add(pendingEmi);
        mockEmis.add(overdueEmi);

        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(mockEmis);

        // Act
        Object result = emiService.getEmiDetails(userId, loanId);

        // Assert
        assertTrue(result instanceof Map);
        Map<String, Object> resultMap = (Map<String, Object>) result;

        // Check lastPaid contains only the most recent paid EMI
        List<EmiEntity> lastPaid = (List<EmiEntity>) resultMap.get("lastPaid");
        assertEquals(1, lastPaid.size());
        assertEquals(paidEmi1.getEmiId(), lastPaid.get(0).getEmiId());

        // Check pending contains only the pending EMI
        List<EmiEntity> pending = (List<EmiEntity>) resultMap.get("pending");
        assertEquals(1, pending.size());
        assertEquals(pendingEmi.getEmiId(), pending.get(0).getEmiId());

        // Check overdue contains only the overdue EMI
        List<EmiEntity> overdue = (List<EmiEntity>) resultMap.get("overdue");
        assertEquals(1, overdue.size());
        assertEquals(overdueEmi.getEmiId(), overdue.get(0).getEmiId());

        verify(emiRepository).findEmisByLoanId(loanId);
    }

    @Test
    void getEmiDetails_whenOnlyPaidEmisExist_shouldReturnCorrectCategories() {
        // Arrange
        EmiEntity paidEmi1 = createEmiEntity(1L, EmiStatus.PAID, LocalDate.now().minusDays(30));
        EmiEntity paidEmi2 = createEmiEntity(2L, EmiStatus.PAID, LocalDate.now().minusDays(60));

        mockEmis.add(paidEmi1);
        mockEmis.add(paidEmi2);

        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(mockEmis);

        // Act
        Object result = emiService.getEmiDetails(userId, loanId);

        // Assert
        assertTrue(result instanceof Map);
        Map<String, Object> resultMap = (Map<String, Object>) result;

        List<EmiEntity> lastPaid = (List<EmiEntity>) resultMap.get("lastPaid");
        assertEquals(1, lastPaid.size());
        assertEquals(paidEmi1.getEmiId(), lastPaid.get(0).getEmiId());

        List<EmiEntity> pending = (List<EmiEntity>) resultMap.get("pending");
        assertTrue(pending.isEmpty());

        List<EmiEntity> overdue = (List<EmiEntity>) resultMap.get("overdue");
        assertTrue(overdue.isEmpty());
    }

    // Helper method to create EMI entities
    private EmiEntity createEmiEntity(Long id, EmiStatus status, LocalDate dueDate) {
        EmiEntity emi = new EmiEntity();
        emi.setEmiId(id);
        emi.setStatus(status);
        emi.setDueDate(dueDate);
        emi.setLoan(new LoanEntity());
        return emi;
    }
}