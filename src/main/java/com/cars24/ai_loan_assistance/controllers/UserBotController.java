package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.CustomUserDetails;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.FeedbackResponse;
import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
import com.cars24.ai_loan_assistance.services.UserBotService;
import com.cars24.ai_loan_assistance.services.impl.FeedbackServiceImpl;
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
    UserBotResponse userBotResponse;
    private final FeedbackServiceImpl feedbackService;

    @GetMapping("/query")
    public ResponseEntity<ApiResponse> handleQuery(
            @RequestParam int prompt_id,
            @RequestParam(required = false) Long additional,
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            Long loggedInUserId = userDetails.getUserId();
            if (userValidationService.isValidUser(loggedInUserId, additional, prompt_id)) {
                userBotResponse = userBotService.interact(prompt_id, loggedInUserId, additional);

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(403, "You are not authorized to see this data", "USERBOT_SERVICE", false, null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(403, "Invalid Principle object", "USERBOT_SERVICE", false, null));
        }


        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Prompt retrieved successfully", "USERBOT_SERVICE", true, userBotResponse));
    }

    @PutMapping("/query")
    public ResponseEntity<ApiResponse> handleUpdate(
            @RequestParam int prompt_id,
            @RequestParam(required = false) Long additional,
            @Valid @RequestBody Map<String, Object> request,
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            Long loggedInUserId = userDetails.getUserId();

            if (additional != null && !userValidationService.isValidUser(loggedInUserId, additional, prompt_id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(403, "You are not authorized to update this data", "USERBOT_SERVICE", false, null));
            }

            UserBotResponse userBotResponse = userBotService.update(prompt_id, loggedInUserId, request, additional);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Record updated successfully", "USERBOT_SERVICE", true, userBotResponse));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse(403, "Invalid Principal object", "USERBOT_SERVICE", false, null));
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse> handleCreate(
            @RequestParam int prompt_id,
            @Valid @RequestBody Map<String, Object> request,
            Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            Long loggedInUserId = userDetails.getUserId();

            if (!userValidationService.isValidUser(loggedInUserId, null, prompt_id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(403, "You are not authorized to create this data", "USERBOT_SERVICE", false, null));
            }

            UserBotResponse userBotResponse = userBotService.create(prompt_id, loggedInUserId, request);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Record created successfully", "USERBOT_SERVICE", true, userBotResponse));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse(403, "Invalid Principal object", "USERBOT_SERVICE", false, null));
    }

    @PostMapping("/feedback")
    public ResponseEntity<ApiResponse> feedbackhandler(@RequestBody String feedback, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        Long loggedInUserId = null;
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            loggedInUserId = userDetails.getUserId();
        }
        feedbackService.feedback(loggedInUserId, feedback);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Feedback sent successfully", "USERBOT_SERVICE", true,null));
    }

}