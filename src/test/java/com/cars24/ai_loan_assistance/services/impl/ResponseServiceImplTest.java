package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.Prompt;
import com.cars24.ai_loan_assistance.data.entities.Response;
import com.cars24.ai_loan_assistance.data.repositories.ResponseRepository;
import com.cars24.ai_loan_assistance.data.responses.RespCollectionResponse;
import com.cars24.ai_loan_assistance.exceptions.PromptNotFoundException;
import com.cars24.ai_loan_assistance.exceptions.ResponseNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResponseServiceImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private ResponseServiceImpl responseService;

    private Prompt mockPrompt;
    private Response mockResponse;

    @BeforeEach
    void setUp() {
        mockPrompt = new Prompt();
        mockPrompt.setPrompt_id("1");

        mockResponse = new Response();
        mockResponse.setResponse_id("101");
        mockResponse.setPrompt_id("1");
        mockResponse.setText("Sample response");
        mockResponse.setHasAttachments(false);
    }

    @Test
    void testGetResponseByPromptId_ValidPromptAndResponse() {
        when(mongoTemplate.findOne(any(Query.class), eq(Prompt.class))).thenReturn(mockPrompt);
        when(mongoTemplate.findOne(any(Query.class), eq(Response.class))).thenReturn(mockResponse);

        RespCollectionResponse response = responseService.getResponseByPromptId("1");

        assertNotNull(response);
        assertEquals("101", response.getResponseId());
        assertEquals("1", response.getPromptId());
        assertEquals("Sample response", response.getText());
        assertFalse(response.isHasAttachments());

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Prompt.class));
        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Response.class));
    }

    @Test
    void testGetResponseByPromptId_PromptNotFound() {
        when(mongoTemplate.findOne(any(Query.class), eq(Prompt.class))).thenReturn(null);

        assertThrows(PromptNotFoundException.class, () -> responseService.getResponseByPromptId("999"));

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Prompt.class));
        verify(mongoTemplate, never()).findOne(any(Query.class), eq(Response.class));
    }

    @Test
    void testGetResponseByPromptId_ResponseNotFound() {
        when(mongoTemplate.findOne(any(Query.class), eq(Prompt.class))).thenReturn(mockPrompt);
        when(mongoTemplate.findOne(any(Query.class), eq(Response.class))).thenReturn(null);

        assertThrows(ResponseNotFoundException.class, () -> responseService.getResponseByPromptId("1"));

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Prompt.class));
        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Response.class));
    }
}
