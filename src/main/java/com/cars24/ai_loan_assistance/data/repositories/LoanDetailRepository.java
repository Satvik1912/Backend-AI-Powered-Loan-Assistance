package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.LoanDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDetailRepository extends JpaRepository<LoanDetailEntity, Long> {
    List<LoanDetailEntity> findByLoan_User_Email(String email);
}

