package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.IncomeType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_information")
public class UserInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_user_information_user"))
    @JsonBackReference("user-info")
    private UserEntity user;

    @Column(name = "pan", nullable = false)
    private String pan;

    @Column(name = "aadhar", nullable = false)
    private String aadhar;

    @Column(name = "address")
    private String address;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "cibil", nullable = false)
    private Integer cibil;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_type", nullable = false)
    private IncomeType incomeType;
}