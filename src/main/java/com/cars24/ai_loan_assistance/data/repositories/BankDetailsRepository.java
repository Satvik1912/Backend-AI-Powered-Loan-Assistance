package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankEntity,Long> {


 long countByBankId(Long bankId);
 List<BankEntity> findByUserId(long userId);
 Optional<BankEntity> findById(Long bankId);


}
