package com.stylelab.user.exception;

import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceException;
import lombok.Getter;

@Getter
public class UsersException extends ServiceException {

    private final CommonError serviceError;

    public UsersException(CommonError serviceError) {
        super(serviceError, serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public UsersException(CommonError serviceError, String message) {
        super(serviceError, message);
        this.serviceError = serviceError;
    }

    public UsersException(CommonError serviceError, Throwable cause) {
        super(serviceError, serviceError.getMessage(), cause);
        this.serviceError = serviceError;
    }

    public UsersException(CommonError serviceError, String message, Throwable cause) {
        super(serviceError, message, cause);
        this.serviceError = serviceError;
    }
}
