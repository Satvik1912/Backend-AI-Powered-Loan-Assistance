package com.cars24.ai_loan_assistance.data.entities;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "Feedback")
public class FeedBackEntity {
    @Id
    private String id;
    @Field(name = "user id")
    private Long userId;
    @Field(name = "Feedback")
    private String feedback;
}
