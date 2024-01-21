package com.stylelab.store.exception;

import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceException;

public class StoreException extends ServiceException {

    private final CommonError serviceError;

    public StoreException(CommonError serviceError) {
        super(serviceError, serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public StoreException(CommonError serviceError, String message) {
        super(serviceError, message);
        this.serviceError = serviceError;
    }

    public StoreException(CommonError serviceError, Throwable cause) {
        super(serviceError, serviceError.getMessage(), cause);
        this.serviceError = serviceError;
    }

    public StoreException(CommonError serviceError, String message, Throwable cause) {
        super(serviceError, message, cause);
        this.serviceError = serviceError;
    }
}
