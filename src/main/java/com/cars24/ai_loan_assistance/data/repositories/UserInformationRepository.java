package com.cars24.ai_loan_assistance.data.repositories;

import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.entities.UserInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformationEntity,Long> {
    Optional<UserInformationEntity> findByUserId(Long userId);
}
