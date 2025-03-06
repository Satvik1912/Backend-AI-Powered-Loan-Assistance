package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "userbot")
@Data
public class UserBot {

    @Id
    private String id;
    @Field("prompt_id")
    private int promptId;
    private List<Integer> followups;
    private String text;
    private ChatbotIntent intent;
    private String responseText;
}
