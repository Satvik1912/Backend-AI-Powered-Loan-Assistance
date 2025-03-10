package com.cars24.ai_loan_assistance.data.responses;

import com.cars24.ai_loan_assistance.data.entities.UserBot;
import com.cars24.ai_loan_assistance.data.entities.enums.ChatbotIntent;
import lombok.Data;

import java.util.List;

@Data
public class UserBotResponse {
    private String mainPromptText;
    private String responseText;
    private List<FollowUpPrompt> followups;
    private Object extraAction;

    public UserBotResponse(UserBot mainPrompt, List<UserBot> followupBots, Object extraAction) {
        this.mainPromptText = mainPrompt.getText();
        this.responseText = mainPrompt.getResponseText();
        this.followups = followupBots.stream()
                .map(bot -> new FollowUpPrompt(bot.getPromptId(), bot.getText()))
                .toList();
        this.extraAction = extraAction;
    }

    @Data
    public static class FollowUpPrompt {
        private final int promptId;
        private final String text;
    }
}
