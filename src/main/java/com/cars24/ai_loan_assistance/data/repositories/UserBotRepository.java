package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.UserBot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBotRepository extends MongoRepository<UserBot, String> {
    UserBot findByPromptId(int promptId);
}

