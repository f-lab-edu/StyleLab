package com.stylelab.user.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.user.application.UsersFacade;
import com.stylelab.user.presentation.request.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersFacade usersFacade;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid final SignupRequestDto signupRequestDto) {
        usersFacade.signup(signupRequestDto);
        return new ResponseEntity<>(ApiResponse.createEmptyApiResponse(), HttpStatus.NO_CONTENT);
    }
}
