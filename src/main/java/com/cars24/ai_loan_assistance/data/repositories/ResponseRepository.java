package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ResponseRepository extends MongoRepository<Response, String> {

    @Query("{ 'promptId' : ?0 }")
    Optional<Response> findByPromptId(String promptId);
}

