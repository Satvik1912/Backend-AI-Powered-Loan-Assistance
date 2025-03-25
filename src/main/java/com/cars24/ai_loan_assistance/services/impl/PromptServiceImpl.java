package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.Prompt;
import com.cars24.ai_loan_assistance.data.responses.NextPromptResponse;
import com.cars24.ai_loan_assistance.exceptions.PromptNotFoundException;
import com.cars24.ai_loan_assistance.services.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromptServiceImpl implements PromptService {

    private final MongoTemplate mongoTemplate;

    public PromptServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<NextPromptResponse> getInitialPrompts() {
        Query query = new Query(Criteria
                .where("category").is("main_category"));
        query.with(Sort.by(Sort.Order.asc("displayOrder")));
        List<Prompt> prompts = mongoTemplate.find(query, Prompt.class);

             return prompts.stream()
                .map(this::convertToNextResponse)
                .toList();
    }

    @Override
    public List<NextPromptResponse> getNextPrompts(String promptId) {
        Query findPromptQuery = new Query(Criteria.where("prompt_id").is(promptId));
        Prompt prompt = mongoTemplate.findOne(findPromptQuery, Prompt.class);
        if (prompt == null) {
            throw new PromptNotFoundException("Prompt not found :" + promptId);
        }

        Query query = new Query(Criteria.where("prompt_id").in(prompt.getNextPromptIds()));
        List<Prompt> nextPrompts = mongoTemplate.find(query, Prompt.class);

        return nextPrompts.stream()
                .map(this::convertToNextResponse)
                .toList();
    }

    private NextPromptResponse convertToNextResponse(Prompt prompt) {
        NextPromptResponse nextPromptResponse = new NextPromptResponse();
        nextPromptResponse.setPromptId(prompt.getPrompt_id());
        nextPromptResponse.setText(prompt.getText());
        nextPromptResponse.setCategory(prompt.getCategory());
        return nextPromptResponse;
    }
}
