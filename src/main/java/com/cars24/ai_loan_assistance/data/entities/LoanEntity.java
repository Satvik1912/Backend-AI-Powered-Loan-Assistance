package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "loan")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "loan_amount", nullable = false)
    private Double loanAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LoanType type;

    @Column(name = "disbursed_date")
    private String disbursedDate;

    @Column(name = "principal")
    private Double principal;

    @Column(name = "tenure")
    private Double tenure;

    @Column(name = "interest")
    private Double interest;
}
