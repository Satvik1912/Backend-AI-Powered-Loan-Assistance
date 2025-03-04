package com.cars24.ai_loan_assistance.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "loan_detail")
public class LoanDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "loan_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_loan_details_loan"))
    @JsonBackReference("loan-detail")
    private LoanEntity loan;

    @Column(name = "principal", nullable = false)
    private Double principal;

    @Column(name = "tenure", nullable = false)
    private Double tenure;

    @Column(name = "interest", nullable = false)
    private Double interest;
}
