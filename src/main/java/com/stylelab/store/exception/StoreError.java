package com.stylelab.store.exception;

import com.stylelab.common.exception.CommonError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum StoreError implements CommonError {
    INVALID_BUSINESS_LICENSE_NUMBER(HttpStatus.BAD_REQUEST, "64000", "유효하지 않은 사업자 등록번호 입니다."),
    BRAND_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "64001", "브랜드명은 필수입니다."),
    BUSINESS_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "64002", "사업자명은 필수입니다."),
    BUSINESS_ADDRESS_REQUIRE(HttpStatus.BAD_REQUEST, "64003", "사업자 주소는 필수입니다."),
    BUSINESS_ADDRESS_DETAIL_REQUIRE(HttpStatus.BAD_REQUEST, "64004", "사업자 상세 주소는 필수입니다."),
    BUSINESS_POSTAL_CODE_REQUIRE(HttpStatus.BAD_REQUEST, "64005", "사업자 우편주소는 필수입니다."),
    BUSINESS_LICENSE_NUMBER_REQUIRE(HttpStatus.BAD_REQUEST, "64006", "사업자 등록번호는 필수입니다."),
    STORE_STAFF_EMAIL_REQUIRE(HttpStatus.BAD_REQUEST, "64007", "스토어 스태프 이메일은 필수입니다."),
    STORE_STAFF_PASSWORD_REQUIRE(HttpStatus.BAD_REQUEST, "64008", "스토어 스태프 비밀번호는 필수입니다."),
    STORE_STAFF_CONFIRM_PASSWORD_REQUIRE(HttpStatus.BAD_REQUEST, "64009", "스토어 스태프 비밀번호 확인은 필수입니다."),
    STORE_STAFF_PASSWORD_VERIFICATION_NOT_MATCH(HttpStatus.BAD_REQUEST, "64010", "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
    STORE_STAFF_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "64011", "스토어 스태프 이름은 필수입니다."),
    STORE_STAFF_NICKNAME_REQUIRE(HttpStatus.BAD_REQUEST, "64012", "스토어 스태프 닉네임은 필수입니다."),
    STORE_STAFF_PHONE_NUMBER_REQUIRE(HttpStatus.BAD_REQUEST, "64013", "스토어 스태프 핸드폰 번호은 필수입니다."),
    STORE_REQUIRE(HttpStatus.BAD_REQUEST, "64014", "스토어 요청 값은 필수입니다."),
    STORE_STAFF_REQUIRE(HttpStatus.BAD_REQUEST, "64015", "스토어 요청 값은 필수입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "64016", "중복된 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "64017", "중복된 닉네임입니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "64018", "중복된 핸드폰 번호입니다."),
    EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64019", "올바르지 않은 형식의 이메일입니다."),
    PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64020", "올바르지 않은 형식의 핸드폰 번호입니다."),
    NAME_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64021", "올바르지 않은 형식의 이름입니다."),
    PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64022", "올바르지 않은 형식의 비밀번호입니다."),
    NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64023", "올바르지 않은 형식의 닉네임입니다."),
    BRAND_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64024", "올바르지 않은 형식의 브랜드 이름입니다."),
    BUSINESS_NAME_IS_NOT_IN_THE_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "64025", "올바르지 않은 형식의 사업장 명입니다."),

    STORE_AND_STORE_STAFF_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "64500", "스토어, 스토어 스태프 등록에 실패하였습니다. 관리자에가 문의해 주십시오.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public static StoreError of(String error) {
        return Arrays.stream(StoreError.values())
                .filter(storeError -> storeError.name().equalsIgnoreCase(error))
                .findAny()
                .orElse(null);
    }
}
