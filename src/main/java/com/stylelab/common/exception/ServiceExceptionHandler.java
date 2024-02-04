package com.stylelab.common.exception;

import com.stylelab.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Payload;
import jakarta.validation.metadata.ConstraintDescriptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Objects;
import java.util.Set;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ServiceExceptionHandler {

    private final ConstraintViolationErrorMap constraintViolationErrorMap;

    /**
     * Exception Handler
     *
     * @param ex Exception
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.INTERNAL_SERVER_ERROR);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    /**
     * ServiceException Handler
     *
     * @param ex ServiceException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleServiceException(ServiceException ex) {
        CommonError serviceError = ex.getServiceError();
        String message = String.format("%s", ObjectUtils.isEmpty(ex.getMessage()) ? serviceError.getMessage() : ex.getMessage());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(serviceError, message);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, serviceError.getHttpStatus());
    }

    /**
     * TransactionSystemException Handler
     *
     * @param ex TransactionSystemException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponse<Void>> handleTransactionSystemException(TransactionSystemException ex) {
        String message = String.format(
                "%s",
                ObjectUtils.isEmpty(ex.getApplicationException()) ?
                        ServiceError.INTERNAL_SERVER_ERROR.getMessage() : ex.getApplicationException().getMessage());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.INTERNAL_SERVER_ERROR, message);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    /**
     * ConstraintViolationException Handler
     *
     * @param ex ConstraintViolationException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        CommonError commonError = null;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            ConstraintDescriptor<?> constraintDescriptor = constraintViolation.unwrap(ConstraintViolation.class).getConstraintDescriptor();
            Set<Class<? extends Payload>> payloads = constraintDescriptor.getPayload();
            if (!payloads.isEmpty()) {
                Class<? extends Payload> payload = payloads.iterator().next();
                commonError = constraintViolationErrorMap.getErrorHandlerMap().get(payload.getName())
                        .of(constraintViolation.getMessage());
                break;
            }
        }

        ApiResponse<Void> response;
        if (commonError == null) {
            String message = String.format("%s", ex.getMessage());
            response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST, message);
        } else {
            response = ApiResponse.createApiResponseFromCommonError(commonError);
        }

        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.BAD_REQUEST.getHttpStatus());
    }

    /**
     * MethodArgumentNotValidException Handler
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        CommonError commonError = null;
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Set<Class<? extends Payload>> payloads = getPayloads(fieldError);
            if (!payloads.isEmpty()) {
                Class<? extends Payload> payload = payloads.iterator().next();
                commonError  = constraintViolationErrorMap.getErrorHandlerMap().get(payload.getName())
                        .of(fieldError.getDefaultMessage());
                break;
            }
        }

        ApiResponse<Void> response;
        if (commonError == null) {
            String message = String.format("%s", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST, message);
        } else {
            response = ApiResponse.createApiResponseFromCommonError(commonError);
        }

        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.BAD_REQUEST.getHttpStatus());
    }

    /**
     * BindException Handler
     *
     * @param ex BindException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
        String message = String.format("%s", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST, message);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.BAD_REQUEST.getHttpStatus());
    }

    /**
     * HttpMediaTypeNotSupportedException Handler
     *
     * @param ex HttpMediaTypeNotSupportedException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String message = String.format("%s media type은 지원하지 않습니다.", ex.getContentType());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.UNSUPPORTED_MEDIA_TYPE, message);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.UNSUPPORTED_MEDIA_TYPE.getHttpStatus());
    }

    /**
     * MethodArgumentTypeMismatchException Handler
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = String.format(ServiceError.UNSUPPORTED_PARAMETER_TYPE.getMessage(), ex.getName(), ex.getValue());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.UNSUPPORTED_PARAMETER_TYPE, message);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.UNSUPPORTED_PARAMETER_TYPE.getHttpStatus());
    }

    /**
     * MethodArgumentTypeMismatchException Handler
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        String message = String.format("%s 입력은 필수입니다.", ex.getRequestPartName());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST, message);
        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.BAD_REQUEST.getHttpStatus());
    }

    /**
     * HttpRequestMethodNotSupportedException Handler
     *
     * @param ex HttpRequestMethodNotSupportedException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String message = String.format("requestUri : %s, requestHttpMethod : %s, supportHttpMethod : %s",
                requestUri, Objects.requireNonNull(ex.getSupportedMethods())[0], ex.getMethod());
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.METHOD_NOT_ALLOWED, message);

        log.error(response.message(), ex);
        return new ResponseEntity<>(response, ServiceError.METHOD_NOT_ALLOWED.getHttpStatus());
    }

    private Set<Class<? extends Payload>> getPayloads(FieldError error) {
        ConstraintDescriptor<?> constraintDescriptor = error.unwrap(ConstraintViolation.class)
                .getConstraintDescriptor();
        return constraintDescriptor.getPayload();
    }
}
