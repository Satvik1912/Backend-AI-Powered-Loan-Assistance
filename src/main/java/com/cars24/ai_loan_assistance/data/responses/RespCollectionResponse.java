package com.cars24.ai_loan_assistance.data.responses;

import lombok.Data;

@Data
public class RespCollectionResponse {
    private String responseId;
    private String promptId;
    private String text;
    private boolean hasAttachments;
}
