package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.UserService;
import com.cars24.ai_loan_assistance.services.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest user, HttpServletResponse httpServletResponse) {
        try{
            ApiResponse response = userService.login(user);

            Map<String, Object> dataMap = (Map<String, Object>) response.getData();
            String token = (String) dataMap.get("token");
            dataMap.remove("token");

            // Create an HttpOnly cookie to store the token.
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            // In production, set this to true (requires HTTPS). For development, you might keep it false.
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(86400); // Token expiration: 86400 seconds = 1 day
            httpServletResponse.addCookie(cookie);

            // Optionally, remove the token from the response body to avoid exposing it to JavaScript.
            response.setData(dataMap);
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
