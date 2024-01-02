package com.stylelab.common.exception;

import jakarta.validation.Payload;
import org.springframework.http.HttpStatus;

public interface CommonError extends Payload {

    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
