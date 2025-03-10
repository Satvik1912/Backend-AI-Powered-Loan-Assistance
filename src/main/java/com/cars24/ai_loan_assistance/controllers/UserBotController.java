package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
import com.cars24.ai_loan_assistance.services.UserBotService;
import com.cars24.ai_loan_assistance.services.impl.UserValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/userbot")
@RequiredArgsConstructor
@Validated
public class UserBotController {

    private final UserBotService userBotService;
    private final UserValidationService userValidationService;

    @GetMapping("/query")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> handleQuery(
            @RequestParam int prompt_id,
            @RequestParam String email,
            @RequestParam(required = false) Long additional,
            Authentication authentication) {

        String loggedInUserEmail = authentication.getName();

        if (!userValidationService.isValidUser(loggedInUserEmail, email, additional)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(403, "You are not authorized to view this data", "USERBOT_SERVICE", false, null));
        }

        UserBotResponse userBotResponse = userBotService.interact(prompt_id, email, additional);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Prompt retrieved successfully", "USERBOT_SERVICE", true, userBotResponse));
    }

    @PutMapping("/query")
    public ResponseEntity<ApiResponse> handleUpdate(
            @RequestParam int prompt_id,
            @RequestParam String email,
            @RequestParam(required = false) Long additional,
            @Valid @RequestBody Map<String, Object> request,
            Authentication authentication) {

        String loggedInUserEmail = authentication.getName();

        if (!userValidationService.isValidUser(loggedInUserEmail, email, additional)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(403, "You are not authorized to update this data", "USERBOT_SERVICE", false, null));
        }

        UserBotResponse userBotResponse = userBotService.update(prompt_id, email, request, additional);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Record updated successfully", "USERBOT_SERVICE", true, userBotResponse));
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse> handleCreate(
            @RequestParam int prompt_id,
            @RequestParam String email,
            @Valid @RequestBody Map<String, Object> request,
            Authentication authentication) {

        String loggedInUserEmail = authentication.getName();

        if (!userValidationService.isValidUser(loggedInUserEmail, email, null)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(403, "You are not authorized to create this data", "USERBOT_SERVICE", false, null));
        }

        UserBotResponse userBotResponse = userBotService.create(prompt_id, email, request);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Record created successfully", "USERBOT_SERVICE", true, userBotResponse));
    }
}