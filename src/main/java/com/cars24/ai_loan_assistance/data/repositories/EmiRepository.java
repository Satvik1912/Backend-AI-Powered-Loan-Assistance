package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmiRepository extends JpaRepository<EmiEntity, Long> {
    @Query("SELECT e FROM EmiEntity e WHERE e.loan.user.email = :email")
    List<EmiEntity> findByUserEmail(@Param("email") String email);
}

