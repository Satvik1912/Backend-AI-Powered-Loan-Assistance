package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
    Optional<LoanEntity> findById(Long loanId);
    List<LoanEntity> findByUserIdAndStatus(long userId, LoanStatus status);
    List<LoanEntity> findByUserId(long userId);
}
