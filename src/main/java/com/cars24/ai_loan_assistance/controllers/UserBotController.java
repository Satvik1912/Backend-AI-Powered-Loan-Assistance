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
    private static final String SERVICE_NAME = "USERBOT_SERVICE";
    @GetMapping("/query")
    public ResponseEntity<ApiResponse> handleQuery(

            @RequestParam int promptId,
            @RequestParam(required = false) Long additional,
            Authentication authentication) {

//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof CustomUserDetails) {
//            CustomUserDetails userDetails = (CustomUserDetails) principal;
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long loggedInUserId = userDetails.getUserId();
            if (userValidationService.isValidUser(loggedInUserId, additional, promptId)) {
                userBotResponse = userBotService.interact(promptId, loggedInUserId, additional);

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(403, "You are not authorized to see this data", SERVICE_NAME, false, null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(403, "Invalid Principle object", SERVICE_NAME, false, null));
        }


        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Prompt retrieved successfully", SERVICE_NAME, true, userBotResponse));
    }

    @PutMapping("/query")
    public ResponseEntity<ApiResponse> handleUpdate(
            @RequestParam int promptId,
            @RequestParam(required = false) Long additional,
            @Valid @RequestBody Map<String, Object> request,
            Authentication authentication) {

        //Object principal = authentication.getPrincipal();

        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long loggedInUserId = userDetails.getUserId();

            if (additional != null && !userValidationService.isValidUser(loggedInUserId, additional, promptId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(403, "You are not authorized to update this data", SERVICE_NAME, false, null));
            }

            UserBotResponse userBotResponse = userBotService.update(promptId, loggedInUserId, request, additional);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Record updated successfully", SERVICE_NAME, true, userBotResponse));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse(403, "Invalid Principal object", SERVICE_NAME, false, null));
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse> handleCreate(
            @RequestParam int promptId,
            @Valid @RequestBody Map<String, Object> request,
            Authentication authentication) {

        //Object principal = authentication.getPrincipal();

        //if (principal instanceof CustomUserDetails userDetails) {// Simplified pattern matching
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long loggedInUserId = userDetails.getUserId();

            if (!userValidationService.isValidUser(loggedInUserId, null, promptId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(403, "You are not authorized to create this data", SERVICE_NAME, false, null));
            }

            UserBotResponse userBotResponse = userBotService.create(promptId, loggedInUserId, request);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Record created successfully", SERVICE_NAME, true, userBotResponse));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse(403, "Invalid Principal object", SERVICE_NAME, false, null));
    }

    @PostMapping("/feedback")
    public ResponseEntity<ApiResponse> feedbackhandler(@RequestBody String feedback, Authentication authentication) {
        //Object principal = authentication.getPrincipal();

        Long loggedInUserId = null;
//        if (principal instanceof CustomUserDetails) {
//            CustomUserDetails userDetails = (CustomUserDetails) principal;
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            loggedInUserId = userDetails.getUserId();
        }
        feedbackService.feedback(loggedInUserId, feedback);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Feedback sent successfully", SERVICE_NAME, true,null));
    }

}