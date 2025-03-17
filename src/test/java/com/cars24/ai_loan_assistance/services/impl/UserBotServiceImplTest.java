package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.UserBot;
import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.repositories.UserBotRepository;
import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBotServiceImplTest {

    @Mock
    private UserBotRepository userBotRepository;

    @Mock
    private ChatbotServiceImpl chatbotService;

    @InjectMocks
    private UserBotServiceImpl userBotService;

    private UserBot mockUserBot;
    private UserBot mockFollowupBot;
    private int promptId;
    private long userId;
    private Long additional;

    @BeforeEach
    void setUp() {
        promptId = 1;
        userId = 123L;
        additional = 456L;

        // Create mock UserBot with intent
        mockUserBot = new UserBot();
        mockUserBot.setPromptId(promptId);
        mockUserBot.setIntent(ChatbotIntent.ACC_PROFILE);
        mockUserBot.setFollowups(Arrays.asList(2, 3));
        mockUserBot.setText("Main prompt text");
        mockUserBot.setResponseText("Response text");
        mockUserBot.setRequestType("GET");

        // Create mock followup UserBot
        mockFollowupBot = new UserBot();
        mockFollowupBot.setPromptId(2);
        mockFollowupBot.setIntent(ChatbotIntent.ACC_CONTACT);
        mockFollowupBot.setFollowups(Collections.emptyList());
        mockFollowupBot.setText("Followup text");
        mockFollowupBot.setResponseText("Followup response");
        mockFollowupBot.setRequestType("POST");
    }

    @Test
    void interact_shouldReturnUserBotResponseWithFollowups() {
        // Arrange
        String expectedResponse = "User profile data";

        // Use lenient() to allow multiple calls to the same mock
        lenient().when(userBotRepository.findByPromptId(promptId)).thenReturn(mockUserBot);
        lenient().when(userBotRepository.findByPromptId(2)).thenReturn(mockFollowupBot);
        lenient().when(userBotRepository.findByPromptId(3)).thenReturn(null); // Simulate missing followup
        when(chatbotService.processQuery(userId, mockUserBot.getIntent(), additional)).thenReturn(expectedResponse);

        // Act
        UserBotResponse response = userBotService.interact(promptId, userId, additional);

        // Assert
        assertNotNull(response);
        assertEquals(mockUserBot.getText(), response.getMainPromptText());
        assertEquals(mockUserBot.getResponseText(), response.getResponseText());
        assertEquals(1, response.getFollowups().size());
        assertEquals(mockFollowupBot.getPromptId(), response.getFollowups().get(0).getPromptId());
        assertEquals(mockFollowupBot.getText(), response.getFollowups().get(0).getText());
        assertEquals(expectedResponse, response.getExtraAction());

        // Don't verify the number of calls to findByPromptId since it's called multiple times
        verify(chatbotService, times(1)).processQuery(userId, mockUserBot.getIntent(), additional);
    }

    @Test
    void interact_whenUserBotNotFound_shouldThrowNotFoundException() {
        // Arrange
        when(userBotRepository.findByPromptId(promptId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userBotService.interact(promptId, userId, additional);
        });

        assertEquals("PromptID does not exist!", exception.getMessage());
        verify(chatbotService, never()).processQuery(anyLong(), any(), any());
    }

    @Test
    void update_shouldReturnUserBotResponseWithUpdatedData() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("phone", "9876543210");
        String expectedResponse = "Contact updated successfully";

        lenient().when(userBotRepository.findByPromptId(promptId)).thenReturn(mockUserBot);
        lenient().when(userBotRepository.findByPromptId(2)).thenReturn(mockFollowupBot);
        lenient().when(userBotRepository.findByPromptId(3)).thenReturn(null);
        when(chatbotService.processUpdate(userId, mockUserBot.getIntent(), request, additional)).thenReturn(expectedResponse);

        // Act
        UserBotResponse response = userBotService.update(promptId, userId, request, additional);

        // Assert
        assertNotNull(response);
        assertEquals(mockUserBot.getText(), response.getMainPromptText());
        assertEquals(mockUserBot.getResponseText(), response.getResponseText());
        assertEquals(expectedResponse, response.getExtraAction());

        verify(chatbotService, times(1)).processUpdate(userId, mockUserBot.getIntent(), request, additional);
    }

    @Test
    void update_withNullRequest_shouldReturnUserBotResponseWithNullExtraAction() {
        // Arrange
        lenient().when(userBotRepository.findByPromptId(promptId)).thenReturn(mockUserBot);
        lenient().when(userBotRepository.findByPromptId(2)).thenReturn(mockFollowupBot);
        lenient().when(userBotRepository.findByPromptId(3)).thenReturn(null);

        // Act
        UserBotResponse response = userBotService.update(promptId, userId, null, additional);

        // Assert
        assertNotNull(response);
        assertNull(response.getExtraAction());

        verify(chatbotService, never()).processUpdate(anyLong(), any(), any(), any());
    }

    @Test
    void create_shouldReturnUserBotResponseWithCreatedData() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("accountNumber", "123456789");
        request.put("ifscCode", "ABCD0001234");
        String expectedResponse = "Bank details created successfully";

        lenient().when(userBotRepository.findByPromptId(promptId)).thenReturn(mockUserBot);
        lenient().when(userBotRepository.findByPromptId(2)).thenReturn(mockFollowupBot);
        lenient().when(userBotRepository.findByPromptId(3)).thenReturn(null);
        when(chatbotService.processCreate(userId, mockUserBot.getIntent(), request)).thenReturn(expectedResponse);

        // Act
        UserBotResponse response = userBotService.create(promptId, userId, request);

        // Assert
        assertNotNull(response);
        assertEquals(mockUserBot.getText(), response.getMainPromptText());
        assertEquals(mockUserBot.getResponseText(), response.getResponseText());
        assertEquals(expectedResponse, response.getExtraAction());

        verify(chatbotService, times(1)).processCreate(userId, mockUserBot.getIntent(), request);
    }

    @Test
    void create_withNullRequest_shouldReturnUserBotResponseWithNullExtraAction() {
        // Arrange
        lenient().when(userBotRepository.findByPromptId(promptId)).thenReturn(mockUserBot);
        lenient().when(userBotRepository.findByPromptId(2)).thenReturn(mockFollowupBot);
        lenient().when(userBotRepository.findByPromptId(3)).thenReturn(null);

        // Act
        UserBotResponse response = userBotService.create(promptId, userId, null);

        // Assert
        assertNotNull(response);
        assertNull(response.getExtraAction());

        verify(chatbotService, never()).processCreate(anyLong(), any(), any());
    }

    @Test
    void interact_withNullIntent_shouldReturnUserBotResponseWithNullExtraAction() {
        // Arrange
        mockUserBot.setIntent(null);

        lenient().when(userBotRepository.findByPromptId(promptId)).thenReturn(mockUserBot);
        lenient().when(userBotRepository.findByPromptId(2)).thenReturn(mockFollowupBot);
        lenient().when(userBotRepository.findByPromptId(3)).thenReturn(null);

        // Act
        UserBotResponse response = userBotService.interact(promptId, userId, additional);

        // Assert
        assertNotNull(response);
        assertNull(response.getExtraAction());

        verify(chatbotService, never()).processQuery(anyLong(), any(), any());
    }
}