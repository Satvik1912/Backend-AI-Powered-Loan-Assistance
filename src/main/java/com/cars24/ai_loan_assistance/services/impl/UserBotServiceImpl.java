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
    public UserBotResponse interact(int promptId, String email, Long additional) {
        return processUserBotRequest(promptId, () -> {
            if (additional != null) {
                return chatbotService.processQuery(email, getUserBot(promptId).getIntent(), additional);
            }
            return null;
        });
    }

    @Override
    public UserBotResponse update(int promptId, String email, Map<String, Object> request, Long additional) {
        return processUserBotRequest(promptId, () -> {
            if (request != null) {
                return chatbotService.processUpdate(email, getUserBot(promptId).getIntent(), request, additional);
            }
            return null;
        });
    }

    @Override
    public UserBotResponse create(int promptId, String email, Map<String, Object> request) {
        return processUserBotRequest(promptId, () -> {
            if (request != null) {
                return chatbotService.processCreate(email, getUserBot(promptId).getIntent(), request);
            }
            return null;
        });
    }

    /**
     * Common method to process UserBot requests and create responses
     * @param promptId the prompt ID to look up
     * @param actionSupplier supplier function for the specific action to perform
     * @return standardized UserBotResponse
     */
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

    /**
     * Helper method to get a UserBot by promptId and throw a standardized exception if not found
     * @param promptId the prompt ID to look up
     * @return the UserBot
     * @throws NotFoundException if the promptId doesn't exist
     */
    private UserBot getUserBot(int promptId) {
        UserBot userBot = userBotRepository.findByPromptId(promptId);
        if (userBot == null) {
            throw new NotFoundException("PromptID does not exist!");
        }
        return userBot;
    }
}