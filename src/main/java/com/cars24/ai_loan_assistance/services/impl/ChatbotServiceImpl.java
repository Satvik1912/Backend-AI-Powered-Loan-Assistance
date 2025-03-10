package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.dao.AccountDao;
import com.cars24.ai_loan_assistance.data.dao.BankDetailsDao;
import com.cars24.ai_loan_assistance.data.dao.LoanDao;
import com.cars24.ai_loan_assistance.data.dao.UserInformationDao;
import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.BankDetailsUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.requests.CreateBankDetails;
import com.cars24.ai_loan_assistance.data.requests.SalaryUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.ChatbotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {
    private final AccountDao accountDao;
    private final LoanDao loanDao;
    private final UserInformationDao userInformationDao;
    private final BankDetailsDao bankDetailsDao;
    private final EmiServiceImpl emiServiceimpl;

    private Validator validator;

    @Autowired
    public void UserBotServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Object processQuery(String email, ChatbotIntent intent, Long additional) {
        switch (intent) {
            case ACC_PROFILE:
                return accountDao.getUserProfile(email);

            case ACC_KYC:
                return accountDao.getKycDetails(email);

            case LOAN_ACTIVE_NUMBER:
                return loanDao.getActiveLoans(email);

            case LOAN_ACTIVE_DETAILS:
                return loanDao.getActiveLoansDetails(email, additional);

            case BANK_LINKED_NUMBER:
                return bankDetailsDao.countofbanks(email);

            case BANK_LINKED_DETAILS:
                return bankDetailsDao.bankfulldetails(email,additional);

            case LOAN_STATUS:
                return loanDao.getLoansByUser(email);

            case ACC_VIEW_SALARY:
                return userInformationDao.getSalaryDetails(email);

            case ACC_CIBIL:
                return userInformationDao.getCibil(email);

            case LOAN_EMI_DETAILS:
                return emiServiceimpl.getEmiDetails(email, additional);

            default:
                return ResponseEntity.badRequest().body(new ApiResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "ERROR: INVALID DATA!",
                        "APP_USER - " + HttpStatus.BAD_REQUEST.value(),
                        false,
                        null
                ));
        }

    }

    @Override
    public String processUpdate(String email, ChatbotIntent intent, Map<String, Object> request, Long additional) {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (intent) {
            case ACC_CONTACT:
                ContactUpdateRequest contactUpdateRequest = objectMapper.convertValue(request, ContactUpdateRequest.class);
                validateRequest(contactUpdateRequest);
                return accountDao.updateContactInfo(email, contactUpdateRequest);

            case BANK_UPDATE:
                BankDetailsUpdateRequest bankDetailsUpdateRequest =  objectMapper.convertValue(request, BankDetailsUpdateRequest.class);
                validateRequest(bankDetailsUpdateRequest);
                return bankDetailsDao.updatebankdetails(email,bankDetailsUpdateRequest, additional);

            case ACC_UPDATE_SALARY:
                SalaryUpdateRequest salaryUpdateRequest = objectMapper.convertValue(request, SalaryUpdateRequest.class);
                validateRequest(salaryUpdateRequest);
                return userInformationDao.updateSalaryDetails(email, salaryUpdateRequest);

            default:
                return "INVALID PROMPT!";
        }
    }

    @Override
    public String processCreate(String email, ChatbotIntent intent, Map<String, Object> request) {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (intent) {
            case BANK_ADD:
                CreateBankDetails createBankDetails =  objectMapper.convertValue(request, CreateBankDetails.class);
                validateRequest(createBankDetails);
                return bankDetailsDao.createBankDetails(email,createBankDetails);

            default:
                return "INVALID PROMPT!";
        }
    }

    private <T> void validateRequest(T requestObject) {
        Set<ConstraintViolation<T>> violations = validator.validate(requestObject);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
