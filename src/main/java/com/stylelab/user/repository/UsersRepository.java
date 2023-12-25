package com.stylelab.user.repository;

import com.stylelab.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
