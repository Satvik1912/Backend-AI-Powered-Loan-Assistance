package com.cars24.ai_loan_assistance.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loans") // Maps to MySQL table "loans"
@Data
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long lId;

    @Column(name = "user_id")
    private String userId;  // Changed to Long assuming it references UserEntity's id

    @Column(name = "loan_amount")
    private double loanAmount;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "status")
    private String status;

    @Column(name = "disbursal_date")
    private String disbursalDate; // Consider changing to `LocalDate` for better date handling
}
