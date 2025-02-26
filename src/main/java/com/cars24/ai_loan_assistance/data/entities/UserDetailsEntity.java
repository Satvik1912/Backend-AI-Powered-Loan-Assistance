package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.IncomeType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "pan", nullable = false)
    private String pan;

    @Column(name = "aadhar", nullable = false)
    private String aadhar;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "cibil")
    private Integer cibil;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_type", nullable = false)
    private IncomeType incomeType;
}
