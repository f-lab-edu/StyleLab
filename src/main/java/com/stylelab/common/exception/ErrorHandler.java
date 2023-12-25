package com.stylelab.common.exception;

import com.stylelab.user.exception.UsersError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorHandler {

    USERS_ERROR {
        @Override
        public UsersError of(String error) {
            return UsersError.of(error);
        }
    };

    public abstract CommonError of(String error);
}
