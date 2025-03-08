//package com.cars24.ai_loan_assistance.data.repositories;
//
//import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
//import com.cars24.ai_loan_assistance.data.responses.ApiResponse;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface LoanDetailRepository extends JpaRepository<LoanEntity, Long> {
//
//    @Query("SELECT l FROM LoanDetailEntity l WHERE l.loan.user.email = :email AND l.loan.loanId = :loanId")
//    List<LoanEntity> getLoanDetailsByEmail(@Param("email") String email, @Param("loanId") Long additional);
//
//}
//
