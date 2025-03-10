package com.cars24.ai_loan_assistance.data.responses;

import lombok.Data;

//package com.cars24.Generic.data.responses;
@Data
public class RespCollectionResponse {
    private String id;
    private String promptId;
    private String text;
    private boolean hasAttachments;
}
