package com.cars24.ai_loan_assistance.data.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "prompts")
public class Prompt {
    @Id
    private String id;
    private String prompt_id;
    private String text;
    private String category;
    private int displayOrder;
    private List<String> nextPromptIds;
    private Date createdAt;
    private Date updatedAt;
}