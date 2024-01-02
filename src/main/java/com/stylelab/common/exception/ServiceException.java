package com.stylelab.common.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final CommonError serviceError;

    public ServiceException(CommonError serviceError) {
        super();
        this.serviceError = serviceError;
    }

    public ServiceException(CommonError serviceError, String message) {
        super(message);
        this.serviceError = serviceError;
    }

    public ServiceException(CommonError serviceError, Throwable cause) {
        super(serviceError.getMessage(), cause);
        this.serviceError = serviceError;
    }

    public ServiceException(CommonError serviceError, String message, Throwable cause) {
        super(message, cause);
        this.serviceError = serviceError;
    }
}
