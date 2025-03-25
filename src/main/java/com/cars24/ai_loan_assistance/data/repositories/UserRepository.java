package com.cars24.ai_loan_assistance.data.repositories;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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


//    @Query("SELECT u FROM UserEntity u " +
//            "LEFT JOIN FETCH u.userDetails " +
//            "LEFT JOIN FETCH u.bankEntities " +
//            "LEFT JOIN FETCH u.loans l " +
//            "LEFT JOIN FETCH l.emis " +
//            "WHERE u.id = :userId")
//    Optional<UserEntity> findByIdWithAllDetails(@Param("userId") Long userId);
@Query(value = "WITH LoanEmiStatus AS (\n" +
        "    SELECT\n" +
        "        l.user_id,\n" +
        "        l.status AS loan_status,\n" +
        "        l.type AS loan_type,\n" +
        "        l.disbursed_date,\n" +
        "        l.principal,\n" +
        "        l.tenure,\n" +
        "        l.interest,\n" +
        "        COALESCE(e.emi_amount, 0) AS emi_amount, \n" +  // Fix NULL issue
        "        SUM(CASE WHEN e.status = 'OVERDUE' THEN 1 ELSE 0 END) AS overdue_count,\n" +
        "        SUM(CASE WHEN e.status = 'PENDING' THEN 1 ELSE 0 END) AS pending_count,\n" +
        "        SUM(CASE WHEN e.status = 'PAID' THEN 1 ELSE 0 END) AS paid_count,\n" +
        "        MIN(CASE WHEN e.status = 'PENDING' THEN e.due_date ELSE NULL END) AS next_due_date,\n" +
        "        COALESCE(SUM(e.late_fee), 0) AS total_late_fee \n" + // Fix NULL issue
        "    FROM loan l\n" +
        "    LEFT JOIN emi e ON l.loan_id = e.loan_id\n" +
        "    GROUP BY l.user_id, l.loan_id, e.emi_amount\n" +
        ")\n" +
        "SELECT \n" +
        "    u.name, \n" +
        "    u.phone_number AS phone, \n" +
        "    u.address, \n" +
        "    u.email,\n" +
        "    ui.pan, \n" +
        "    ui.aadhar, \n" +
        "    COALESCE(ui.salary, 0) AS salary, \n" +  // Fix NULL issue
        "    COALESCE(ui.cibil, 0) AS cibil, \n" +  // Fix NULL issue
        "    ui.income_type,\n" +
        "    les.loan_status, \n" +
        "    les.loan_type, \n" +
        "    les.disbursed_date, \n" +
        "    COALESCE(les.principal, 0) AS principal, \n" +  // Fix NULL issue
        "    COALESCE(les.tenure, 0) AS tenure, \n" +  // Fix NULL issue
        "    COALESCE(les.interest, 0) AS interest, \n" +  // Fix NULL issue
        "    les.emi_amount, \n" +
        "    les.overdue_count, \n" +
        "    les.pending_count, \n" +
        "    les.paid_count, \n" +
        "    les.next_due_date, \n" +
        "    les.total_late_fee,\n" +
        "    COALESCE(b.account_no, '') AS account_number,\n" +  // Fix NULL issue
        "    COALESCE(b.account_holder_name, '') AS account_holder_name,\n" +  // Fix NULL issue
        "    COALESCE(b.ifsc_code, '') AS ifsc_code,\n" +  // Fix NULL issue
        "    COALESCE(b.bank_name, '') AS bank_name,\n" +  // Fix NULL issue
        "    COALESCE(b.bank_account_type, '') AS bank_account_type\n" +  // Fix NULL issue
        "FROM users u\n" +
        "LEFT JOIN user_information ui ON u.user_id = ui.user_id\n" +
        "LEFT JOIN LoanEmiStatus les ON u.user_id = les.user_id\n" +
        "LEFT JOIN bank_details b ON u.user_id = b.user_id\n" +
        "WHERE u.user_id = ?;\n", nativeQuery = true)
List<Object[]> getUserDetails(Long userId);

}