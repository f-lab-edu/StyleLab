package com.stylelab.user.application;

import com.stylelab.user.presentation.request.SignupRequestDto;
import com.stylelab.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsersFacade {

    private final UsersService usersService;

    public void signup(SignupRequestDto signupRequestDto) {
        usersService.signup(SignupRequestDto.toEntity(signupRequestDto));
    }
}
