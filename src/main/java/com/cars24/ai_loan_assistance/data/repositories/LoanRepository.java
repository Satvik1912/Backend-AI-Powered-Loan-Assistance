package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
    LoanEntity findByUserId(long user_id);
}
