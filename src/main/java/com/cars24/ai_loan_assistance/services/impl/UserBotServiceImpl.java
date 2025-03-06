package com.cars24.ai_loan_assistance.services.impl;

import com.cars24.ai_loan_assistance.data.entities.UserBot;
import com.cars24.ai_loan_assistance.data.repositories.UserBotRepository;
import com.cars24.ai_loan_assistance.data.responses.UserBotResponse;
import com.cars24.ai_loan_assistance.exceptions.NotFoundException;
import com.cars24.ai_loan_assistance.services.UserBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBotServiceImpl implements UserBotService {
    private final UserBotRepository userBotRepository;
    private final ChatbotServiceImpl chatbotService;
    @Override
    public UserBotResponse interact(int prompt_id, String email, Long additional) {
        UserBot userBot = userBotRepository.findByPromptId(prompt_id);

        if (userBot == null){
            throw new NotFoundException("PromptID does not exist!");
        }

        List<UserBot> followUpBots = userBot.getFollowups().stream()
                .map(userBotRepository::findByPromptId)
                .filter(Objects::nonNull)
                .toList();

        Object extraAction = null;
        if(userBot.getIntent() != null){
            extraAction = chatbotService.processQuery(email, userBot.getIntent(), additional);
        }

        return new UserBotResponse(userBot, followUpBots, extraAction);
    }

    @Override
    public UserBotResponse update(int prompt_id, String email, Map<String, Object> request, Long additional) {
        UserBot userBot = userBotRepository.findByPromptId(prompt_id);

        if (userBot == null){
            throw new NotFoundException("PromptID does not exist!");
        }

        List<UserBot> followUpBots = userBot.getFollowups().stream()
                .map(userBotRepository::findByPromptId)
                .filter(Objects::nonNull)
                .toList();

        Object extraAction = null;
        if(userBot.getIntent() != null){
            extraAction = chatbotService.processUpdate(email, userBot.getIntent(), request, additional);
        }

        return new UserBotResponse(userBot, followUpBots, extraAction);
    }

    @Override
    public UserBotResponse create(int prompt_id, String email, Map<String, Object> request) {
        UserBot userBot = userBotRepository.findByPromptId(prompt_id);

        if (userBot == null){
            throw new NotFoundException("PromptID does not exist!");
        }

        List<UserBot> followUpBots = userBot.getFollowups().stream()
                .map(userBotRepository::findByPromptId)
                .filter(Objects::nonNull)
                .toList();

        Object extraAction = null;
        if(userBot.getIntent() != null){
            extraAction = chatbotService.processCreate(email, userBot.getIntent(), request);
        }

        return new UserBotResponse(userBot, followUpBots, extraAction);
    }
}
