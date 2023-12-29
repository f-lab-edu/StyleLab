package com.stylelab.user.presentation.request;

import com.stylelab.user.exception.UsersError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequest {

    @NotBlank(message = "EMAIL_IS_REQUIRED", payload = UsersError.class)
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UsersError.class)
    private String email;

    @NotBlank(message = "PASSWORD_IS_REQUIRED", payload = UsersError.class)
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT", payload = UsersError.class)
    private String password;
}
