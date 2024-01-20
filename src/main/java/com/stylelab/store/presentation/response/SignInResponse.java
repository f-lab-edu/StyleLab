package com.stylelab.store.presentation.response;

import com.stylelab.user.exception.UsersError;
import com.stylelab.user.exception.UsersException;
import org.springframework.util.StringUtils;

public record SignInResponse(String token) {

    public static SignInResponse createResponse(String token) {
        if (!StringUtils.hasText(token)) {
            throw new UsersException(UsersError.EMAIL_IS_REQUIRED);
        }

        return new SignInResponse(token);
    }
}
