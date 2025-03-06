package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
import com.cars24.ai_loan_assistance.services.UserBotService;
import com.cars24.ai_loan_assistance.services.impl.UserBotServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/userbot")
@RequiredArgsConstructor
@Validated
public class UserBotController {

    private final UserBotServiceImpl userBotService;

    @GetMapping("/query")
    public ResponseEntity<ApiResponse> handleQuery(@RequestParam int prompt_id, @RequestParam String email, @RequestParam(defaultValue = "0") Long additional){
        UserBotResponse userBotResponse = userBotService.interact(prompt_id, email, additional);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "Prompt(s) retrieved successfully",
                "USERBOT_SERVICE-" + HttpStatus.OK.value(),
                true,
                userBotResponse);

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/query")
    public ResponseEntity<ApiResponse> handleUpdate(@RequestParam int prompt_id, @RequestParam String email, @Valid @RequestBody Map<String, Object> request, @RequestParam(defaultValue = "0") Long additional){
        UserBotResponse userBotResponse = userBotService.update(prompt_id, email, request, additional);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "Record updated successfully",
                "USERBOT_SERVICE-" + HttpStatus.OK.value(),
                true,
                userBotResponse);

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse> handleCreate(@RequestParam int prompt_id, @RequestParam String email, @Valid @RequestBody Map<String, Object> request){
        UserBotResponse userBotResponse = userBotService.create(prompt_id, email, request);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(),
                "Record created successfully",
                "USERBOT_SERVICE-" + HttpStatus.OK.value(),
                true,
                userBotResponse);

        return ResponseEntity.ok().body(apiResponse);
    }
}
