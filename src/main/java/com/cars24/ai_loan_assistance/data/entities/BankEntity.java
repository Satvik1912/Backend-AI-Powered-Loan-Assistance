package com.cars24.ai_loan_assistance.data.entities;


import com.cars24.ai_loan_assistance.data.entities.enums.BankAccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "bank_details")
public class BankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long bankId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bank_details_user"))
    @JsonBackReference("bank-details")
    @NotNull(message = "User cannot be null")  // Add this
    private UserEntity user;

    @Column(name = "account_no", nullable = false)
    @NotBlank(message = "Account number cannot be blank")  // Add this
    private String accountNumber;

    @Column(name = "account_holder_name", nullable = false)
    @NotBlank(message = "Account holder name cannot be blank")  // Add this
    private String accountHolderName;

    @Column(name = "ifsc_code", nullable = false)
    @NotBlank(message = "IFSC code cannot be blank")  // Add this
    private String ifscCode;

    @Column(name = "bank_name", nullable = false)
    @NotBlank(message = "Bank name cannot be blank")  // Add this
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_account_type", nullable = false)
    @NotNull(message = "Bank account type cannot be null")  // Add this
    private BankAccountType bankAccountType;
}
