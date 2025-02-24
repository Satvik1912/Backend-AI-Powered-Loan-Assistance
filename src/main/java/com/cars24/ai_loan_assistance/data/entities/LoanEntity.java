package com.cars24.ai_loan_assistance.data.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "loans")
@Data
public class LoanEntity {

    @Id
    private String lId;
    @Field("userId")
    private String userId;
    @Field("loanAmount")
    private double loanAmount;
    @Field("loanType")
    private String loanType;
    @Field("status")
    private String status;
    @Field("disbursalDate")
    private String disbursalDate;
}
