package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.services.UserDetailsMapper;
import com.cars24.ai_loan_assistance.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAllDetailsController {
    private final UserDetailsMapper userService;

    @GetMapping("/all")
    public ResponseEntity<String> getUserDetails(@RequestParam Long userId) {
        String response = userService.getUserDetailsAsParagraph(userId);
        return ResponseEntity.ok(response);
    }
}
