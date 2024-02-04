package com.stylelab.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ServiceError implements CommonError {

    // common error
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "40000", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "40001", "인증되지 않은 유저입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "40003", "권한이 없는 유저입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"40004", "자원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"40005", "지원하지 않는 Http Method 입니다."),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "40006", "허용되지 않은 요청입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "40015", "지원하지 않는 미디어 타입입니다."),
    UNSUPPORTED_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, "40030", "%s의 %s 타입은 지원하지 않는 타입입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50000", "내부 서버 에러입니다. 관리자에게 문의해 주십시오."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "50003", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
