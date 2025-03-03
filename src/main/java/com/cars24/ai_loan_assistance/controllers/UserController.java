package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserServiceImpl userservice;
 @PostMapping("/signup")
public ResponseEntity<ApiResponse> signUp(@RequestBody SignupRequest user)
{
    ApiResponse response =userservice.registerUser(user);
    return ResponseEntity.status(response.getStatusCode()).body(response);
}
@PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest user) {
        try{
            ApiResponse response = userservice.login(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "APPUSER",false, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
