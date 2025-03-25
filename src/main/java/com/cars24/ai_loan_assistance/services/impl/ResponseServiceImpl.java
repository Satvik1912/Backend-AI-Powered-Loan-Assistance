package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.Prompt;
import com.cars24.ai_loan_assistance.data.entities.Response;
import com.cars24.ai_loan_assistance.data.repositories.ResponseRepository;
import com.cars24.ai_loan_assistance.data.responses.RespCollectionResponse;
import com.cars24.ai_loan_assistance.exceptions.PromptNotFoundException;
import com.cars24.ai_loan_assistance.exceptions.ResponseNotFoundException;
import com.cars24.ai_loan_assistance.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;

@Service
public class ResponseServiceImpl implements ResponseService {
    private final MongoTemplate mongoTemplate;


    public ResponseServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

    }

    @Override
    public RespCollectionResponse getResponseByPromptId(String promptId){
        Query query = new Query(Criteria.where("prompt_id").is(promptId));
        Prompt prompt = mongoTemplate.findOne(query, Prompt.class);
        if (prompt == null) {
            throw new PromptNotFoundException("Prompt not found: " + promptId);
        }
        Response response = mongoTemplate.findOne(query, Response.class);
        if (response == null) {
            throw new ResponseNotFoundException("Response not found for prompt: " + promptId);
        }
        return convertToResponse(response);
    }

    private RespCollectionResponse convertToResponse(Response response) {
        RespCollectionResponse respCollectionResponse = new RespCollectionResponse();
        respCollectionResponse.setResponseId(response.getResponse_id());
        respCollectionResponse.setPromptId(response.getPrompt_id());
        respCollectionResponse.setText(response.getText());
        respCollectionResponse.setHasAttachments(response.isHasAttachments());
        return respCollectionResponse;
    }
}

