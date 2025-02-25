package com.cars24.ai_loan_assistance.data.entities;

import com.cars24.ai_loan_assistance.data.entities.enums.BankAccType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bank_details")
public class BankDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bd_id")
    private Long bdId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "acc_holder_name", nullable = false)
    private String accHolderName;

    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_acc_type", nullable = false)
    private BankAccType bankAccType;
}
