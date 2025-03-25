package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.requests.LoginRequest;
import com.cars24.ai_loan_assistance.data.requests.SignupRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.UserService;
import com.cars24.ai_loan_assistance.services.impl.UserServiceImpl;
import com.cars24.ai_loan_assistance.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8000"}, allowCredentials = "true")
@Slf4j
public class UserController {

    private final JwtUtil jwtUtil;
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
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            httpServletResponse.addCookie(cookie);
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

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse httpServletResponse, @CookieValue(name = "token") String token) {
        // Optionally, you could validate or log the token/claims here if needed.

        // Clear the token cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // need to set true for HTTPS, currently using http
        cookie.setPath("/");
        cookie.setMaxAge(0); // Immediately expire the cookie
        httpServletResponse.addCookie(cookie);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                "Logout successful.",
                "APPUSER",
                true,
                null
        );
        return ResponseEntity.ok(response);
    }



    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getCurrentUser(@CookieValue(name = "token", required = false) String token) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Missing token.", "APPUSER", false, null));
            }

            Long userId = Long.valueOf(jwtUtil.extractUserId(token));
//            String role = jwtUtil.extractRole(token);

            Map<String, Object> data = new HashMap<>();
            data.put("userId", userId);
//



            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "User fetched successfully.", "APPUSER", true, data));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid token: " + ex.getMessage(), "APPUSER", false, null));
        }
    }
}
