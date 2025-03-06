package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.UserService;
import com.cars24.ai_loan_assistance.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignupRequest user) {
        try{
            ApiResponse response = userService.registerUser(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.CONFLICT.value(),
                    e.getMessage(),
                    "APPUSER",
                    false,
                    null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest user) {
        try{
            ApiResponse response = userService.login(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    "APPUSER",
                    false,
                    null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
