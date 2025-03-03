package com.cars24.ai_loan_assistance.data.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Service;
@Entity
@Data
@Table(name="BankDetailsss")
public class BankDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Full_Name")
    private String full_name;

    @Column(name ="account_no")
    private String account_no;

    @Column(name = "bank_acc_type")
    private  String bank_acc_type;

    @Column(name = "bank_name")
    private  String bank_name;

    @Column(name = "ifsc_code")
    private  String ifsc_code;

    @Column(name = "uid")
    private  Long uid;

}
