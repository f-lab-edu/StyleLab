package com.stylelab.common.security.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.UserPrincipal;
import com.stylelab.user.domain.Users;
import com.stylelab.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username)
                .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()));

        return UserPrincipal.create(users);
    }
}
