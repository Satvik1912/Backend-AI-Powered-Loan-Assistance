package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.entities.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long userId);
    @Query("SELECT u.name FROM UserEntity u WHERE u.id = :userId")
    String findUserNameById(@Param("userId") Long userId);

    @Query("""
    SELECT COUNT(u) > 0 
    FROM UserEntity u
    WHERE u.id = :userId
    AND (
        (:promptId IN (18, 19) AND EXISTS (
            SELECT 1 FROM BankEntity b 
            WHERE b.user.id = u.id 
            AND b.id = :additionalId
        )) OR
        (:promptId IN (14, 15) AND EXISTS (
            SELECT 1 FROM LoanEntity l 
            WHERE l.user.id = u.id 
            AND l.id = :additionalId
        )) OR
        (:promptId NOT IN (14, 15, 18, 19) AND (
            :additionalId IS NULL OR 
            EXISTS (
                SELECT 1 FROM LoanEntity l 
                WHERE l.user.id = u.id 
                AND l.id = :additionalId
            ) OR EXISTS (
                SELECT 1 FROM EmiEntity e 
                WHERE e.loan.user.id = u.id 
                AND e.loan.id = :additionalId
            ) OR EXISTS (
                SELECT 1 FROM BankEntity b 
                WHERE b.user.id = u.id 
                AND b.id = :additionalId
            )
        ))
    )
    """)
    Boolean existsByUserIdAndAdditionalId(
            @Param("userId") Long userId,
            @Param("additionalId") Long additionalId,
            @Param("promptId") int promptId
    );


}