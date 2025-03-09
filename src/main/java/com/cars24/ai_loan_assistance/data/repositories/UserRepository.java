package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("""
    SELECT CASE 
        WHEN COUNT(u) > 0 THEN true 
        ELSE false 
    END 
    FROM UserEntity u
    WHERE u.email = :email 
    AND (
        :additionalId IS NULL OR 
        EXISTS (
            SELECT 1 FROM LoanEntity l 
            WHERE l.user.id = u.id 
            AND l.id = :additionalId
        )
    )
""")

    boolean existsByEmailAndAdditionalId(
            @Param("email") String email,
            @Param("additionalId") Long additionalId
    );



}
