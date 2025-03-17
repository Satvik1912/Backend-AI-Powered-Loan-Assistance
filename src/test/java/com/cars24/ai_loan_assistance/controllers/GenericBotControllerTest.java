package com.cars24.ai_loan_assistance.controllers;

import static org.junit.jupiter.api.Assertions.*;

//package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.responses.*;
import com.cars24.ai_loan_assistance.services.PromptService;
import com.cars24.ai_loan_assistance.services.ResponseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenericBotControllerTest {

    @Mock
    private PromptService promptService;

    @Mock
    private ResponseService responseService;

    @InjectMocks
    private GenericBotController genericBotController;

    @Test
    void testGetChatbotData_WithNoPromptId() {
        List<NextPromptResponse> mockPrompts = Collections.singletonList(new NextPromptResponse());
        when(promptService.getInitialPrompts()).thenReturn(mockPrompts);

        ResponseEntity<ApiResponse> response = genericBotController.getChatbotData(null);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals("Initial prompts fetched successfully", response.getBody().getMessage());
        assertNotNull(((ChatbotInteractionResponse) response.getBody().getData()).getInitialPrompts());
        verify(promptService, times(1)).getInitialPrompts();
    }

    @Test
    void testGetChatbotData_WithPromptId() {
        String promptId = "123";
        RespCollectionResponse mockResponse = new RespCollectionResponse();
        List<NextPromptResponse> mockNextPrompts = Collections.singletonList(new NextPromptResponse());

        when(responseService.getResponseByPromptId(promptId)).thenReturn(mockResponse);
        when(promptService.getNextPrompts(promptId)).thenReturn(mockNextPrompts);

        ResponseEntity<ApiResponse> response = genericBotController.getChatbotData(promptId);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals("Interaction details fetched successfully", response.getBody().getMessage());
        assertNotNull(((ChatbotInteractionResponse) response.getBody().getData()).getResponse());
        assertNotNull(((ChatbotInteractionResponse) response.getBody().getData()).getNextPrompts());
        verify(responseService, times(1)).getResponseByPromptId(promptId);
        verify(promptService, times(1)).getNextPrompts(promptId);
    }
}
