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
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBotServiceImpl implements UserBotService {
    private final UserBotRepository userBotRepository;
    private final ChatbotServiceImpl chatbotService;

    @Override
    public UserBotResponse interact(int promptId, long userId, Long additional) {
        log.info("userBotService: [userId] {}",userId);
        return processUserBotRequest(promptId, () -> {
                log.info("userBotService: [userId] {}",userId);
                return chatbotService.processQuery(userId, getUserBot(promptId).getIntent(), additional);
        });
    }

    @Override
    public UserBotResponse update(int promptId, long userId, Map<String, Object> request, Long additional) {
        return processUserBotRequest(promptId, () -> {
            if (request != null) {
                return chatbotService.processUpdate(userId, getUserBot(promptId).getIntent(), request, additional);
            }
            return null;
        });
    }

    @Override
    public UserBotResponse create(int promptId, long userId, Map<String, Object> request) {
        return processUserBotRequest(promptId, () -> {
            if (request != null) {
                return chatbotService.processCreate(userId, getUserBot(promptId).getIntent(), request);
            }
            return null;
        });
    }

    private UserBotResponse processUserBotRequest(int promptId, Supplier<Object> actionSupplier) {
        UserBot userBot = getUserBot(promptId);

        List<UserBot> followUpBots = userBot.getFollowups().stream()
                .map(userBotRepository::findByPromptId)
                .filter(Objects::nonNull)
                .toList();

        Object extraAction = null;
        if (userBot.getIntent() != null) {
            extraAction = actionSupplier.get();
        }

        return new UserBotResponse(userBot, followUpBots, extraAction);
    }

    private UserBot getUserBot(int promptId) {
        UserBot userBot = userBotRepository.findByPromptId(promptId);
        if (userBot == null) {
            throw new NotFoundException("PromptID does not exist!");
        }
        return userBot;
    }


}