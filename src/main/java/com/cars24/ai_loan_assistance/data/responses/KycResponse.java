package com.cars24.ai_loan_assistance.data.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KycResponse {
    String PAN;
    String Aadhar;
}
