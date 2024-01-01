package com.stylelab.user.presentation.response;

import com.stylelab.user.exception.UsersError;
import com.stylelab.user.exception.UsersException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

    private String token;

    public static SignInResponse createResponse(String token) {
        if (!StringUtils.hasText(token)) {
            throw new UsersException(UsersError.EMAIL_IS_REQUIRED, UsersError.EMAIL_IS_REQUIRED.getMessage());
        }

        return new SignInResponse(token);
    }
}
