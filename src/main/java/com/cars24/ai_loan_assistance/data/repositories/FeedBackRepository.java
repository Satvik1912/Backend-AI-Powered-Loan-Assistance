package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.FeedBackEntity;
import com.cars24.ai_loan_assistance.data.entities.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedBackRepository extends MongoRepository<FeedBackEntity, String> {
}
