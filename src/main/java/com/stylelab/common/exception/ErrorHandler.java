package com.stylelab.common.exception;

import com.stylelab.store.exception.StoreError;
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
    },

    STORE_ERROR {
        @Override
        public StoreError of(String error) {
            return StoreError.of(error);
        }
    };

    public abstract CommonError of(String error);
}
