package com.stylelab.category.exception;

import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceException;

public class ProductCategoryException extends ServiceException {

    private final CommonError serviceError;

    public ProductCategoryException(CommonError serviceError) {
        super(serviceError, serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public ProductCategoryException(CommonError serviceError, String message) {
        super(serviceError, message);
        this.serviceError = serviceError;
    }

    public ProductCategoryException(CommonError serviceError, Throwable cause) {
        super(serviceError, serviceError.getMessage(), cause);
        this.serviceError = serviceError;
    }

    public ProductCategoryException(CommonError serviceError, String message, Throwable cause) {
        super(serviceError, message, cause);
        this.serviceError = serviceError;
    }
}
