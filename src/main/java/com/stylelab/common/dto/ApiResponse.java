package com.stylelab.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.stylelab.common.exception.CommonError;
import com.stylelab.common.exception.ServiceError;
import lombok.*;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    @Builder.Default
    @JsonIgnore
    private HttpStatus status = HttpStatus.OK;

    @Builder.Default
    private String code = ServiceError.OK.getCode();

    @Builder.Default
    private String message = ServiceError.OK.getMessage();

    @JsonUnwrapped
    @JsonInclude(value= NON_NULL)
    private T body;

    public static <T> ApiResponse<T> createEmptyApiResponse() {
        return ApiResponse.<T>builder()
                .build();
    }

    public static <T> ApiResponse<T> createApiResponse(T body) {
        return ApiResponse.<T>builder()
                .body(body)
                .build();
    }

    public static <T> ApiResponse<T> createApiResponseFromCommonError(CommonError serviceCommonError) {
        return ApiResponse.<T>builder()
                .status(serviceCommonError.getHttpStatus())
                .code(serviceCommonError.getCode())
                .message(serviceCommonError.getMessage())
                .build();
    }
}
