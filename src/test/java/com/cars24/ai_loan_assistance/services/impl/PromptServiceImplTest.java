package com.cars24.ai_loan_assistance.services.impl;

import static org.junit.jupiter.api.Assertions.*;

//package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.Prompt;
import com.cars24.ai_loan_assistance.data.responses.NextPromptResponse;
import com.cars24.ai_loan_assistance.exceptions.PromptNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromptServiceImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private PromptServiceImpl promptService;

    private Prompt prompt1;
    private Prompt prompt2;

    @BeforeEach
    void setUp() {
        prompt1 = new Prompt();
        prompt1.setPrompt_id("1");
        prompt1.setText("First Prompt");
        prompt1.setCategory("main_category");
        prompt1.setNextPromptIds(Arrays.asList("2"));

        prompt2 = new Prompt();
        prompt2.setPrompt_id("2");
        prompt2.setText("Second Prompt");
        prompt2.setCategory("sub_category");
        prompt2.setNextPromptIds(Collections.emptyList());
    }

    @Test
    void testGetInitialPrompts() {
        when(mongoTemplate.find(any(Query.class), eq(Prompt.class))).thenReturn(Arrays.asList(prompt1, prompt2));

        List<NextPromptResponse> responses = promptService.getInitialPrompts();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("1", responses.get(0).getPromptId());
        assertEquals("First Prompt", responses.get(0).getText());

        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Prompt.class));
    }

    @Test
    void testGetNextPrompts_ValidPromptId() {
        when(mongoTemplate.findOne(any(Query.class), eq(Prompt.class))).thenReturn(prompt1);
        when(mongoTemplate.find(any(Query.class), eq(Prompt.class))).thenReturn(List.of(prompt2));

        List<NextPromptResponse> responses = promptService.getNextPrompts("1");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("2", responses.get(0).getPromptId());
        assertEquals("Second Prompt", responses.get(0).getText());

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Prompt.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Prompt.class));
    }

    @Test
    void testGetNextPrompts_InvalidPromptId() {
        when(mongoTemplate.findOne(any(Query.class), eq(Prompt.class))).thenReturn(null);

        assertThrows(PromptNotFoundException.class, () -> promptService.getNextPrompts("999"));

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(Prompt.class));
        verify(mongoTemplate, never()).find(any(Query.class), eq(Prompt.class));
    }
}
