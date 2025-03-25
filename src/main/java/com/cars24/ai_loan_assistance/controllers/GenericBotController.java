package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.ChatbotInteractionResponse;
import com.cars24.ai_loan_assistance.data.responses.NextPromptResponse;
import com.cars24.ai_loan_assistance.data.responses.RespCollectionResponse;
import com.cars24.ai_loan_assistance.services.PromptService;
import com.cars24.ai_loan_assistance.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
public class GenericBotController {


    private final PromptService promptService;
    private final ResponseService responseService;

    // Constructor Injection
    @Autowired
    public GenericBotController(PromptService promptService, ResponseService responseService) {
        this.promptService = promptService;
        this.responseService = responseService;
    }

    @GetMapping("/interaction")
    public ResponseEntity<ApiResponse> getChatbotData(
        @RequestParam(value = "prompt_id", required = false) String promptId){

        ChatbotInteractionResponse response = new ChatbotInteractionResponse();

        // If promptId is not provided, return initial prompts
        if (promptId == null || promptId.isEmpty()) {
            List<NextPromptResponse> initialPrompts = promptService.getInitialPrompts();
            response.setInitialPrompts(initialPrompts);

            // Create success response directly
            ApiResponse apiResponse = new ApiResponse(
                    HttpStatus.OK.value(),
                    "Initial prompts fetched successfully",
                    "GENERIC_CHATBOT-" + HttpStatus.OK.value(),
                    true,
                    response
            );

            return ResponseEntity.ok(apiResponse);
        }

        // Otherwise, return interaction details for the given promptId
        RespCollectionResponse botResponse = responseService.getResponseByPromptId(promptId);
        response.setResponse(botResponse);

        List<NextPromptResponse> nextPrompts = promptService.getNextPrompts(promptId);
        response.setNextPrompts(nextPrompts);

        // Create success response directly
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.OK.value(),
                "Interaction details fetched successfully",
                "GENERIC_CHATBOT-" + HttpStatus.OK.value(),
                true,
                response
        );

        return ResponseEntity.ok(apiResponse);
    }

}