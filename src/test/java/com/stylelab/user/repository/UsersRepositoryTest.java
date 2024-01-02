package com.stylelab.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DisplayName("회원 테이블에 이메일이 존재하는지 확인하는 쿼리")
    public void existsByEmail() {
        usersRepository.existsByEmail("test@gmail.com");
    }

    @Test
    @DisplayName("회원 테이블에 닉네임이 존재하는지 확인하는 쿼리")
    public void existsByNickname() {
        usersRepository.existsByNickname("coby");
    }
}
