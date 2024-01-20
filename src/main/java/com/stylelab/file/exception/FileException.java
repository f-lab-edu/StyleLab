package com.stylelab.file.exception;

import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceException;
import lombok.Getter;

@Getter
public class FileException extends ServiceException {

    private final CommonError serviceError;

    public FileException(CommonError serviceError) {
        super(serviceError, serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public FileException(CommonError serviceError, String message) {
        super(serviceError, message);
        this.serviceError = serviceError;
    }

    public FileException(CommonError serviceError, Throwable cause) {
        super(serviceError, cause);
        this.serviceError = serviceError;
    }

    public FileException(CommonError serviceError, String message, Throwable cause) {
        super(serviceError, message, cause);
        this.serviceError = serviceError;
    }
}
