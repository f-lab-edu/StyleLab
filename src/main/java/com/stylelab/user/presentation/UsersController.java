package com.stylelab.user.presentation;

import com.stylelab.user.application.UsersFacade;
import com.stylelab.user.presentation.request.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersFacade usersFacade;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        usersFacade.signup(signupRequestDto);
    }
}
