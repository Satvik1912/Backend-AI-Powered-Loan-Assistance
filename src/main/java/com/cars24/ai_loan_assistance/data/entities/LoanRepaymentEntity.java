package com.cars24.ai_loan_assistance.data.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Data
@Document("Loan-Replacement")
public class LoanRepaymentEntity {

    @Id
    private String lrId;
    @Field("lId")
    private String lId;
    @Field("amountPaid")
    private double amountPaid;
    @Field("paymentDate")
    private Date paymentDate;
}
