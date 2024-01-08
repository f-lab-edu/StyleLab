package com.stylelab.common.security.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.principal.UserPrincipal;
import com.stylelab.user.domain.Users;
import com.stylelab.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class UsersLoadUserByUsernameStrategy implements LoadUserByUsernameStrategy {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users users = usersRepository.findByEmail(username)
                .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()));

        return UserPrincipal.create(users);
    }
}
