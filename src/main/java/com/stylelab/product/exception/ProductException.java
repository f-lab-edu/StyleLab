package com.stylelab.product.exception;

import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceException;

public class ProductException extends ServiceException {

    private final CommonError serviceError;

    public ProductException(CommonError serviceError) {
        super(serviceError);
        this.serviceError = serviceError;
    }

    public ProductException(CommonError serviceError, String message) {
        super(serviceError, message);
        this.serviceError = serviceError;
    }

    public ProductException(CommonError serviceError, Throwable cause) {
        super(serviceError, cause);
        this.serviceError = serviceError;
    }

    public ProductException(CommonError serviceError, String message, Throwable cause) {
        super(serviceError, message, cause);
        this.serviceError = serviceError;
    }
}
