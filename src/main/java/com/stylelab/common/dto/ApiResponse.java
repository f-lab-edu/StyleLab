package com.stylelab.common.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.stylelab.common.exception.CommonError;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder
public record ApiResponse<T>(
        String code,
        String message,
        @JsonUnwrapped
        T body
) {

    private static final String SUCCESS_CODE = "20000";
    private static final String SUCCESS_MESSAGE = "success";

    public static ApiResponse<Void> createEmptyApiResponse() {
        return ApiResponse.<Void>builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    public static <T> ApiResponse<T> createApiResponse(T body) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .body(body)
                .build();
    }

    public static  ApiResponse<Void> createApiResponseFromCommonError(CommonError serviceCommonError) {
        return ApiResponse.<Void>builder()
                .code(serviceCommonError.getCode())
                .message(serviceCommonError.getMessage())
                .build();
    }

    public static  ApiResponse<Void> createApiResponseFromCommonError(CommonError serviceCommonError, String message) {
        return ApiResponse.<Void>builder()
                .code(serviceCommonError.getCode())
                .message(StringUtils.hasText(message) ? message: serviceCommonError.getMessage())
                .build();
    }
}
