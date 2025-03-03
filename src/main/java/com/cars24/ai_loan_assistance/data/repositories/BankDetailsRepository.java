package com.gemini.gemini.Repository;

import com.gemini.gemini.data.entity.BankDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetailsEntity,Long> {

 BankDetailsEntity getByUid(Long uid);

}
