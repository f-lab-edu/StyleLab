package com.stylelab.common.exception;

import com.stylelab.common.dto.ApiResponse;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ServiceExceptionHandler {

    private final ServiceErrorHandlerMap serviceErrorHandlerMap;

    /**
     * Exception Handler
     *
     * @param ex Exception
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(ServiceError.INTERNAL_SERVER_ERROR.getHttpStatus())
                .code(ServiceError.INTERNAL_SERVER_ERROR.getCode())
                .message("내부 서버 에러입니다. 관리자에게 문의해 주십시오.")
                .build();
        log.error(response.getMessage(), ex.getCause());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * ServiceException Handler
     *
     * @param ex ServiceException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleServiceException(ServiceException ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ex.getServiceError());
        response.setMessage(
                String.format("%s", ObjectUtils.isEmpty(ex.getMessage()) ? response.getMessage() : ex.getMessage()));
        log.error(response.getMessage(), ex.getCause());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * TransactionSystemException Handler
     *
     * @param ex TransactionSystemException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponse<Void>> handleTransactionSystemException(TransactionSystemException ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.INTERNAL_SERVER_ERROR);
        response.setMessage(
                String.format(
                        "%s",
                        ObjectUtils.isEmpty(ex.getApplicationException()) ? response.getMessage() : ex.getApplicationException().getMessage()));
        log.error(response.getMessage(), ex.getCause());
        return new ResponseEntity<>(response, response.getStatus());
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
                commonError  = serviceErrorHandlerMap.getErrorHandlerMap().get(payload.getName())
                        .of(fieldError.getDefaultMessage());
                break;
            }
        }

        ApiResponse<Void> response;
        if (commonError == null) {
            response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST);
            response.setMessage(String.format("%s", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
        } else {
            response = ApiResponse.<Void>builder()
                    .status(commonError.getHttpStatus())
                    .code(commonError.getCode())
                    .message(commonError.getMessage())
                    .build();
        }

        log.error(response.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * BindException Handler
     *
     * @param ex BindException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST);
        response.setMessage(String.format("%s", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
        log.error(response.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * HttpMediaTypeNotSupportedException Handler
     *
     * @param ex HttpMediaTypeNotSupportedException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.UNSUPPORTED_MEDIA_TYPE);
        response.setMessage(String.format("%s media type은 지원하지 않습니다.", ex.getContentType()));
        log.error(response.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * MethodArgumentTypeMismatchException Handler
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.UNSUPPORTED_PARAMETER_TYPE);
        response.setMessage(String.format(ServiceError.UNSUPPORTED_PARAMETER_TYPE.getMessage(), ex.getName(), ex.getValue()));
        log.error(response.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * MethodArgumentTypeMismatchException Handler
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return ResponseEntity<ApiResDto<Void>>
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.BAD_REQUEST);
        response.setMessage(String.format("%s 입력은 필수입니다.", ex.getRequestPartName()));
        log.error(response.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
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
        ApiResponse<Void> response = ApiResponse.createApiResponseFromCommonError(ServiceError.METHOD_NOT_ALLOWED);
        response.setMessage(
                String.format("requestUri : %s, requestHttpMethod : %s, supportHttpMethod : %s",
                        requestUri, Objects.requireNonNull(ex.getSupportedMethods())[0], ex.getMethod()));
        log.error(response.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    private Set<Class<? extends Payload>> getPayloads(FieldError error) {
        ConstraintDescriptor<?> constraintDescriptor = error.unwrap(ConstraintViolation.class)
                .getConstraintDescriptor();
        return constraintDescriptor.getPayload();
    }
}
