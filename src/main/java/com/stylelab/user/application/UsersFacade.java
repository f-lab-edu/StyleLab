package com.stylelab.user.application;

import com.stylelab.user.exception.UsersException;
import com.stylelab.user.presentation.request.SignupRequestDto;
import com.stylelab.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.stylelab.user.exception.UsersError.PASSWORD_VERIFICATION_NOT_MATCH;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsersFacade {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    public void signup(final SignupRequestDto signupRequestDto) {
        if (!Objects.equals(signupRequestDto.getPassword(), signupRequestDto.getConfirmPassword())) {
            log.error(PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
            throw new UsersException(PASSWORD_VERIFICATION_NOT_MATCH, PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
        }

        String encodePassword = passwordEncoder.encode(signupRequestDto.getPassword());
        usersService.signup(SignupRequestDto.toEntity(signupRequestDto, encodePassword));
    }

    public boolean existsByEmail(final String email) {
        return usersService.existsByEmail(email);
    }

    public boolean existsByNickname(final String nickname) {
        return usersService.existsByNickname(nickname);
    }
}
