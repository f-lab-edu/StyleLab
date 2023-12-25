package com.stylelab.user.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.user.application.UsersFacade;
import com.stylelab.user.exception.UsersError;
import com.stylelab.user.presentation.request.SignupRequestDto;
import com.stylelab.user.presentation.response.ExistsByEmailResponse;
import com.stylelab.user.presentation.response.ExistsByNicknameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Slf4j
@Validated
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

    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<ExistsByEmailResponse>> existsByEmail(
            @RequestParam(name = "email", required = false)
            @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UsersError.class)
            @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UsersError.class)
            final String email) {
        return ResponseEntity.ok(ApiResponse.createApiResponse(new ExistsByEmailResponse(usersFacade.existsByEmail(email))));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<ApiResponse<ExistsByNicknameResponse>> existsByNickname(
            @RequestParam(name = "nickname", required = false)
            @NotBlank(message = "NICKNAME_IS_REQUIRED", payload = UsersError.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UsersError.class)
            final String nickname) {
        return ResponseEntity.ok(ApiResponse.createApiResponse(new ExistsByNicknameResponse(usersFacade.existsByNickname(nickname))));
    }
}
