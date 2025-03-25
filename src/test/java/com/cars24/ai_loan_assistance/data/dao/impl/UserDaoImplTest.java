package com.cars24.ai_loan_assistance.data.dao.impl;


import com.cars24.ai_loan_assistance.data.entities.UserEntity;
import com.cars24.ai_loan_assistance.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDaoImpl userDao;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setEmail("john.doe@example.com");
    }

    @Test
    void testSaveUser() {
        userDao.saveUser(userEntity);
        verify(userRepository, times(1)).save(userEntity);
    }
}
