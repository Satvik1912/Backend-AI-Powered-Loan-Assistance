package com.cars24.ai_loan_assistance.services;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//
//import java.util.Map;
//
//@Slf4j
//@Service
//public class FastAPIOllamaService {
//
//    private final WebClient webClient;
//    private static final String FASTAPI_URL = "http://localhost:8000/generate-response/";
//
//    public FastAPIOllamaService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder
//                .baseUrl(FASTAPI_URL)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//    }
//
//    public Flux<String> streamResponseFromFastAPI(String userQuery, String userData) {
//        log.info("Making FastAPI Stream Call with user_data: {} and user_query: {}", userData, userQuery);
//
//        // Prepare request body
//        Map<String, Object> requestBody = Map.of(
//                "user_query", userQuery,
//                "user_data", userData
//        );
//
//        return webClient.post()
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToFlux(String.class)
//                .map(jsonChunk -> {
//                    log.info("Received JSON chunk: {}", jsonChunk);
//                    return extractTokenFromJson(jsonChunk);
//                })
//                .doOnError(e -> log.error("Error in streaming response", e));
//    }
//
//    private String extractTokenFromJson(String jsonChunk) {
//        try {
//            // Parse JSON and extract token
//            Map<String, Object> chunk = objectMapper.readValue(jsonChunk, Map.class);
//            String token = (String) chunk.get("token");
//            Boolean finished = (Boolean) chunk.get("finished");
//
//            if (Boolean.TRUE.equals(finished)) {
//                log.info("Stream finished");
//            }
//
//            return token;
//        } catch (Exception e) {
//            log.error("Error parsing JSON chunk", e);
//            return "";
//        }
//    }
//
//    // Add Jackson ObjectMapper for JSON parsing
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//}

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class FastAPIOllamaService {

    private final WebClient webClient;
    private static final String FASTAPI_URL = "http://localhost:8000/generate-response/";

    public FastAPIOllamaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(FASTAPI_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Flux<String> streamResponseFromFastAPI(String userQuery, String userData) {
        log.info("Making FastAPI Stream Call with user_data: {} and user_query: {}", userData, userQuery);

        Map<String, Object> requestBody = Map.of(
                "user_query", userQuery,
                "user_data", userData
        );

        AtomicReference<StringBuilder> responseCollector = new AtomicReference<>(new StringBuilder());

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class)
                .map(jsonChunk -> {
//                    log.debug("Received JSON chunk: {}", jsonChunk);
                    String token = extractTokenFromJson(jsonChunk);

                    // Append token to the response collector
                    responseCollector.get().append(token).append(" ");

                    return token;
                })
                .doOnNext(token -> log.info("Streaming Response: {}", responseCollector.get().toString().trim()))
                .doOnComplete(() -> log.info("Final Response: {}", responseCollector.get().toString().trim()))
                .doOnError(e -> log.error("Error in streaming response", e));
    }

    private String extractTokenFromJson(String jsonChunk) {
        try {
            Map<String, Object> chunk = objectMapper.readValue(jsonChunk, Map.class);
            return (String) chunk.getOrDefault("token", "");
        } catch (Exception e) {
            log.error("Error parsing JSON chunk", e);
            return "";
        }
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();
}
