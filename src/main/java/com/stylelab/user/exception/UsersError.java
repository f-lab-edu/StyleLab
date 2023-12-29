package com.stylelab.user.exception;

import com.stylelab.common.exception.CommonError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UsersError implements CommonError {
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "61000", "중복된 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "61001", "중복된 닉네임입니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "61002", "중복된 핸드폰 번호입니다."),
    EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "61003", "올바르지 않은 형식의 이메일입니다."),
    PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "61004", "올바르지 않은 형식의 핸드폰 번호입니다."),
    NAME_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "61005", "올바르지 않은 형식의 이름입니다." ),
    EMAIL_IS_REQUIRED(HttpStatus.BAD_REQUEST, "61006", "이메일은 필수입니다."),
    NAME_IS_REQUIRED(HttpStatus.BAD_REQUEST, "61007", "이름은 필수입니다."),
    NICKNAME_IS_REQUIRED(HttpStatus.BAD_REQUEST, "61008", "닉네임은 필수입니다."),
    PASSWORD_IS_REQUIRED(HttpStatus.BAD_REQUEST, "61009", "비밀번호는 필수입니다."),
    PHONE_NUMBER_IS_REQUIRED(HttpStatus.BAD_REQUEST, "61010", "핸드폰 번호는 필수입니다."),
    CONFIRM_PASSWORD_IS_REQUIRED(HttpStatus.BAD_REQUEST, "61011", "비밀번호 확인 값은 필수입니다."),
    PASSWORD_VERIFICATION_NOT_MATCH(HttpStatus.BAD_REQUEST, "61012", "비밀번호와 비밀번호 확인값이 일치하지 않습니다."),
    PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "61013", "올바르지 않은 형식의 비밀번호입니다." ),
    NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "61014", "올바르지 않은 형식의 닉네임입니다." ),
    DELIVERY_ADDRESS_REQUIRE(HttpStatus.BAD_REQUEST, "61015", "배송지 주소는 필수입니다." ),
    DELIVERY_ADDRESS_DETAIL_REQUIRE(HttpStatus.BAD_REQUEST, "61016", "배송지 상세 주소는 필수입니다." ),
    DELIVERY_POSTAL_CODE_REQUIRE(HttpStatus.BAD_REQUEST, "61017", "배송지 우편 주소는 필수입니다." ),
    DELIVERY_ADDRESS_ALIASES_REQUIRE(HttpStatus.BAD_REQUEST, "61018", "배송지 별칭은 필수입니다." ),
    DELIVERY_DEFAULT_ADDRESS_REQUIRE(HttpStatus.BAD_REQUEST, "61019", "기본 배송지 값은 필수입니다." ),

    USERS_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "61050", "회원 등록에 실패하였습니다. 관리자에가 문의해 주십시오."),
    DELIVERY_ADDRESS_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "61051", "회원 배송지 등록에 실패하였습니다. 관리자에가 문의해 주십시오.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public static UsersError of(String error) {
        return Arrays.stream(UsersError.values())
                .filter(usersError -> usersError.name().equalsIgnoreCase(error))
                .findAny()
                .orElse(null);
    }
}
