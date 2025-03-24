package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "emi")
public class EmiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emi_id")
    private Long emiId;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_emi_loan"))
    @JsonBackReference("loan-emis")
    private LoanEntity loan;

    @Column(name = "emi_amount", nullable = false)
    private Double emiAmount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmiStatus status;

    @Column(name = "late_fee")
    private Double lateFee;
}
