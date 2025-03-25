package com.cars24.ai_loan_assistance.data.dao;


import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;

import com.cars24.ai_loan_assistance.data.responses.KycResponse;
import com.cars24.ai_loan_assistance.data.responses.UserProfileResponse;


public interface AccountDao {
    UserProfileResponse getUserProfile(long userId);
    String updateContactInfo(long userId, ContactUpdateRequest request);
    KycResponse getKycDetails(long userId);
}
