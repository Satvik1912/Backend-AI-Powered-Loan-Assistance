package com.cars24.ai_loan_assistance.data.dao;
import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import org.springframework.stereotype.Service;


@Service
public interface UserDao {

    void saveUser(UserEntity user);
}
