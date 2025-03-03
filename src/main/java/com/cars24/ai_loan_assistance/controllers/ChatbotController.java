package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.requests.ContactUpdateRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
@Validated
public class ChatbotController {
    private final ChatbotService chatbotService;
    @GetMapping("/query")
    public ResponseEntity<ApiResponse> handleQuery(@RequestParam String email, @RequestParam ChatbotIntent intent, @RequestParam(defaultValue = "0") Long additional) {
        return chatbotService.processQuery(email,intent, String.valueOf(additional));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> handleUpdate(@RequestParam String email, @RequestParam ChatbotIntent intent, @Valid @RequestBody Map<String, Object> request) {
        return chatbotService.processUpdate(email, intent, request);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> handleCreate(@RequestParam String email, @RequestParam ChatbotIntent intent, @Valid @RequestBody Map<String, Object> request) {
        return chatbotService.processCreate(email,intent,request);
    }
}
