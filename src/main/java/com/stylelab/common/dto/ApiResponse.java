package com.stylelab.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceError;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
public record ApiResponse<T>(
        @JsonIgnore
        HttpStatus status,
        String code,
        String message,
        @JsonUnwrapped
        @JsonInclude(value= NON_NULL)
        T body
) {



    public static ApiResponse<Void> createEmptyApiResponse() {
        return ApiResponse.<Void>builder()
                .status(HttpStatus.OK)
                .code(ServiceError.OK.getCode())
                .message(ServiceError.OK.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> createApiResponse(T body) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(ServiceError.OK.getCode())
                .message(ServiceError.OK.getMessage())
                .body(body)
                .build();
    }

    public static  ApiResponse<Void> createApiResponseFromCommonError(CommonError serviceCommonError) {
        return ApiResponse.<Void>builder()
                .status(serviceCommonError.getHttpStatus())
                .code(serviceCommonError.getCode())
                .message(serviceCommonError.getMessage())
                .build();
    }

    public static  ApiResponse<Void> createApiResponseFromCommonError(CommonError serviceCommonError, String message) {
        return ApiResponse.<Void>builder()
                .status(serviceCommonError.getHttpStatus())
                .code(serviceCommonError.getCode())
                .message(StringUtils.hasText(message) ? message: serviceCommonError.getMessage())
                .build();
    }
}
