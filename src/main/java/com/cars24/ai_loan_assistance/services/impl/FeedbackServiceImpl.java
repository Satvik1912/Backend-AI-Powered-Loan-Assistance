package com.cars24.ai_loan_assistance.services.impl;
import com.cars24.ai_loan_assistance.data.entities.FeedBackEntity;
import com.cars24.ai_loan_assistance.data.repositories.FeedBackRepository;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl {

    private final FeedBackRepository feedBackRepository;
    public void feedback(Long loggedInUserId, String feedback)
    {
        FeedBackEntity feedBackEntity = new FeedBackEntity();
        feedBackEntity.setUser_id(loggedInUserId);
        feedBackEntity.setFeedback(feedback);
        feedBackRepository.save(feedBackEntity);
    }}
