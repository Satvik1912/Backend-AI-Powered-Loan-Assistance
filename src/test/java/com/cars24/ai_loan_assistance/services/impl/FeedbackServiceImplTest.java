package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.FeedBackEntity;
import com.cars24.ai_loan_assistance.data.repositories.FeedBackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class FeedbackServiceImplTest {

    @Mock
    private FeedBackRepository feedBackRepository;

    @Captor
    private ArgumentCaptor<FeedBackEntity> feedBackArgumentCaptor;

    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedbackService = new FeedbackServiceImpl(feedBackRepository);
    }

    @Test
    void testFeedback_WhenValidInputProvided_ShouldSaveFeedbackEntity() {
        // Arrange
        Long loggedInUserId = 123L;
        String feedbackText = "Great service!";

        // Act
        feedbackService.feedback(loggedInUserId, feedbackText);

        // Assert
        verify(feedBackRepository).save(feedBackArgumentCaptor.capture());
        FeedBackEntity capturedEntity = feedBackArgumentCaptor.getValue();

        assertThat(capturedEntity.getUserId()).isEqualTo(loggedInUserId);
        assertThat(capturedEntity.getFeedback()).isEqualTo(feedbackText);
    }
}