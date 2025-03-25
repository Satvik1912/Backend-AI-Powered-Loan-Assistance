package com.cars24.ai_loan_assistance.controllers;

import com.cars24.ai_loan_assistance.data.requests.OllamaRequest;
import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
import com.cars24.ai_loan_assistance.services.FastAPIOllamaService;
import com.cars24.ai_loan_assistance.services.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8000","http://localhost:5500"})
public class UserAllDetailsController {
    private final UserDetailsMapper userService;
    private final FastAPIOllamaService fastAPIOllamaService;

    @PostMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getUserDetailsStreaming(
            @RequestParam Long userId,
            @RequestBody OllamaRequest ollamaRequest
    ) {
        String userData = userService.getUserDetailsAsParagraph(userId);

        return fastAPIOllamaService.streamResponseFromFastAPI(
                ollamaRequest.getUserQuery(),
                userData
        );
    }

    // Optional: If you still want the complete response as before
    @PostMapping("/all-complete")
    public Mono<ApiResponse> getUserDetailsComplete(
            @RequestParam Long userId,
            @RequestBody OllamaRequest ollamaRequest
    ) {
        String userData = userService.getUserDetailsAsParagraph(userId);

        return fastAPIOllamaService.streamResponseFromFastAPI(
                        ollamaRequest.getUserQuery(),
                        userData
                )
                .collectList()
                .map(tokens -> {
                    String completeResponse = String.join("", tokens);
                    return new ApiResponse(
                            HttpStatus.OK.value(),
                            "Response generated successfully!",
                            "Ollama_Service",
                            true,
                            completeResponse
                    );
                });
    }
}