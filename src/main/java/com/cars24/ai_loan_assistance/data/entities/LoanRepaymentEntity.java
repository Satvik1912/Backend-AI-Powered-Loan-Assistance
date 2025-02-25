package com.cars24.ai_loan_assistance.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "loan_repayment") // Maps to MySQL table "loan_repayment"
@Data
public class LoanRepaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    @Column(name = "lr_id")
    private Long lrId;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false) // Foreign key referencing LoanEntity
    private LoanEntity loan;

    @Column(name = "amount_paid", nullable = false)
    private double amountPaid;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;
}
