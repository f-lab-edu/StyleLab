package com.stylelab.user.service;

import com.stylelab.user.domain.Users;

public interface UsersService {
    void signup(final Users users);
    boolean existsByEmail(final String email);
    boolean existsByNickname(final String nickname);
}
