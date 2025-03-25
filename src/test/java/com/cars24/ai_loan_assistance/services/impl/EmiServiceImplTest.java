package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.cars24.ai_loan_assistance.data.repositories.EmiRepository;
import com.cars24.ai_loan_assistance.data.repositories.LoanRepository;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.util.PdfGeneratorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmiServiceImplTest {

    @Mock
    private EmiRepository emiRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private EmiServiceImpl emiService;

    private UserEntity mockUser;
    private LoanEntity mockLoan;
    private List<EmiEntity> mockEmis;

    @BeforeEach
    void setup() {
        // Create mock user
        mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        // Create mock loan
        mockLoan = new LoanEntity();
        mockLoan.setLoanId(1L);

        // Create mock EMIs
        mockEmis = Arrays.asList(
                createEmiEntity(1L, EmiStatus.PAID, LocalDate.now().minusDays(30)),
                createEmiEntity(2L, EmiStatus.PENDING, LocalDate.now().plusDays(30)),
                createEmiEntity(3L, EmiStatus.OVERDUE, LocalDate.now().minusDays(10))
        );
    }

    private EmiEntity createEmiEntity(Long id, EmiStatus status, LocalDate dueDate) {
        EmiEntity emi = new EmiEntity();
        emi.setEmiId(id);
        emi.setStatus(status);
        emi.setDueDate(dueDate);
        return emi;
    }

    @Test
    void testGetEmiDetails_UserNotFound() {
        // Arrange
        long userId = 1L;
        long loanId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Object result = emiService.getEmiDetails(userId, loanId);

        // Assert
        assertTrue(result instanceof ApiResponse);
        ApiResponse apiResponse = (ApiResponse) result;
        assertEquals(404, apiResponse.getStatusCode());
        assertEquals("User not found.", apiResponse.getMessage());
        assertEquals("USER_NOT_FOUND", apiResponse.getService());
        assertFalse(apiResponse.isSuccess());
    }

    @Test
    void testGetEmiDetails_LoanNotFound() {
        // Arrange
        long userId = 1L;
        long loanId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Act
        Object result = emiService.getEmiDetails(userId, loanId);

        // Assert
        assertTrue(result instanceof ApiResponse);
        ApiResponse apiResponse = (ApiResponse) result;
        assertEquals(404, apiResponse.getStatusCode());
        assertEquals("Loan not found.", apiResponse.getMessage());
        assertEquals("LOAN_NOT_FOUND", apiResponse.getService());
        assertFalse(apiResponse.isSuccess());
    }

    @Test
    void testGetEmiDetails_NoEmiFound() {
        // Arrange
        long userId = 1L;
        long loanId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(Collections.emptyList());

        // Act
        Object result = emiService.getEmiDetails(userId, loanId);

        // Assert
        assertTrue(result instanceof ResponseEntity);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertNotNull(apiResponse);
        assertEquals(403, apiResponse.getStatusCode());
        assertEquals("No EMI details found for this loan.", apiResponse.getMessage());
        assertEquals("LOAN_EMI_DETAILS", apiResponse.getService());
        assertFalse(apiResponse.isSuccess());
    }

    @Test
    void testGetEmiDetails_Success() {
        // Arrange
        long userId = 1L;
        long loanId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(mockEmis);

        // Mock PDF generation
        try (MockedStatic<PdfGeneratorUtil> mockedPdfUtil = mockStatic(PdfGeneratorUtil.class)) {
            mockedPdfUtil.when(() -> PdfGeneratorUtil.generateEmiPdf(
                    anyString(),
                    any(LoanEntity.class),
                    anyList()
            )).thenReturn("/path/to/generated/pdf");

            // Act
            Object result = emiService.getEmiDetails(userId, loanId);

            // Assert
            assertTrue(result instanceof Map);
            Map<String, Object> resultMap = (Map<String, Object>) result;

            // Check last paid EMIs
            List<EmiEntity> lastPaid = (List<EmiEntity>) resultMap.get("lastPaid");
            assertNotNull(lastPaid);
            assertEquals(1, lastPaid.size());
            assertEquals(EmiStatus.PAID, lastPaid.get(0).getStatus());

            // Check pending EMIs
            List<EmiEntity> pending = (List<EmiEntity>) resultMap.get("pending");
            assertNotNull(pending);
            assertEquals(1, pending.size());
            assertEquals(EmiStatus.PENDING, pending.get(0).getStatus());

            // Check overdue EMIs
            List<EmiEntity> overdue = (List<EmiEntity>) resultMap.get("overdue");
            assertNotNull(overdue);
            assertEquals(1, overdue.size());
            assertEquals(EmiStatus.OVERDUE, overdue.get(0).getStatus());

            // Check PDF path
            assertEquals("/path/to/generated/pdf", resultMap.get("pdfPath"));
        }
    }

    @Test
    void testGetEmiDetails_EmptyLastPaidList() {
        // Arrange
        long userId = 1L;
        long loanId = 1L;
        List<EmiEntity> emis = Arrays.asList(
                createEmiEntity(2L, EmiStatus.PENDING, LocalDate.now().plusDays(30)),
                createEmiEntity(3L, EmiStatus.OVERDUE, LocalDate.now().minusDays(10))
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(emiRepository.findEmisByLoanId(loanId)).thenReturn(emis);

        // Mock PDF generation
        try (MockedStatic<PdfGeneratorUtil> mockedPdfUtil = mockStatic(PdfGeneratorUtil.class)) {
            mockedPdfUtil.when(() -> PdfGeneratorUtil.generateEmiPdf(
                    anyString(),
                    any(LoanEntity.class),
                    anyList()
            )).thenReturn("/path/to/generated/pdf");

            // Act
            Object result = emiService.getEmiDetails(userId, loanId);

            // Assert
            assertTrue(result instanceof Map);
            Map<String, Object> resultMap = (Map<String, Object>) result;

            // Check last paid EMIs
            List<EmiEntity> lastPaid = (List<EmiEntity>) resultMap.get("lastPaid");
            assertNotNull(lastPaid);
            assertTrue(lastPaid.isEmpty());
        }
    }
}