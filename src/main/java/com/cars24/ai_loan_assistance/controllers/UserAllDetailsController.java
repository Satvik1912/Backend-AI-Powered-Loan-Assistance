package com.cars24.ai_loan_assistance.controllers;
import com.cars24.ai_loan_assistance.data.requests.OllamaRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.FastAPIOllamaService;
import com.cars24.ai_loan_assistance.services.UserDetailsMapper;
import com.cars24.ai_loan_assistance.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000")
public class UserAllDetailsController {
    private final UserDetailsMapper userService;
    private final FastAPIOllamaService fastAPIOllamaService;

    @PostMapping("/all")
    public ResponseEntity<ApiResponse> getUserDetails(@RequestParam Long userId, @RequestBody OllamaRequest ollamaRequest) {
        String user_data = userService.getUserDetailsAsParagraph(userId);
        String response = fastAPIOllamaService.callFastAPI(ollamaRequest.getUserQuery(), user_data);
        return ResponseEntity.ok(new ApiResponse((HttpStatus.OK.value()),"Response generated successfully!","Ollama_Service",true, response));
    }
}
