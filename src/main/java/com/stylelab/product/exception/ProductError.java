package com.stylelab.product.exception;

import com.stylelab.common.exception.CommonError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProductError implements CommonError {

    STORE_ID_REQUIRE(HttpStatus.BAD_REQUEST, "67000", "상품을 등록하기 위해서 스토어 아이디는 필수입니다."),
    PRODUCT_CATEGORY_PATH_REQUIRE(HttpStatus.BAD_REQUEST, "67001", "상품 카테고리는 필수입니다."),
    PRODUCT_PRICE_REQUIRE(HttpStatus.BAD_REQUEST, "67002", "상품 가격은 필수입니다."),
    PRODUCT_DISCOUNT_RATE_REQUIRE(HttpStatus.BAD_REQUEST, "67003", "상품 할인율은 필수입니다."),
    PRODUCT_ENTRY_MAIN_REQUIRE(HttpStatus.BAD_REQUEST, "67004", "상품 대표 이미지는 필수입니다."),
    PRODUCT_ENTRY_SUB_REQUIRE(HttpStatus.BAD_REQUEST, "67005", "상품 서브 이미지는 필수입니다."),
    PRODUCT_DESCRIPTION_REQUIRE(HttpStatus.BAD_REQUEST, "67006", "상품 상세 이미지는 필수입니다."),
    PRODUCT_QUANTITY_LESS_THEN_ZERO(HttpStatus.BAD_REQUEST, "67007", "상품의 수량이 0보다 작을 수 없습니다."),
    OPTION1_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO(HttpStatus.BAD_REQUEST, "67008", "상품 옵션1이 존재 시 상품의 수량이 0보다 클 수 없습니다."),
    OPTION1_QUANTITY_LESS_THEN_ZERO(HttpStatus.BAD_REQUEST, "67009", "상품 옵션1이 존재 시 옵션1의 수량이 0보다 작을 수 없습니다."),
    OPTION1_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "67010", "상품 옵션1이 존재 시 옵션1의 이름은 필수입니다."),
    OPTION2_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO(HttpStatus.BAD_REQUEST, "67011", "상품 옵션2이 존재 시 상품 또는 옵션1의 수량이 0보다 클 수 없습니다."),
    OPTION2_QUANTITY_LESS_THEN_ZERO(HttpStatus.BAD_REQUEST, "67012", "상품 옵션2이 존재 시 옵션2의 수량이 0보다 작을 수 없습니다."),
    OPTION2_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "67013", "상품 옵션2이 존재 시 옵션2의 이름은 필수입니다."),
    PRODUCT_REQUIRE(HttpStatus.BAD_REQUEST, "67014", "상품 등록 정보는 필수입니다."),
    EXCEED_MAX_IMAGE_COUNT(HttpStatus.BAD_REQUEST, "67015", "%s 타입은 최대 %s개의 이미지를 등록하실 수 있습니다."),
    PRODUCT_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "67016", "상품 이름은 필수입니다."),
    PRODUCT_PRICE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "67017", "상품 가격은 3,000 이상 10억 이하의 값만 사용할 수 있습니다."),
    PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "67018", "상품 할인율은 0 이상 100 이하의 값만 사용할 수 있습니다."),
    PRODUCT_OPTION1_REQUEST_REQUIRE(HttpStatus.BAD_REQUEST, "67019", "상품 옵션 1 객체는 필수입니다."),
    PRODUCT_OPTION_DEPTH_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "67020", "상품 옵션 개수는 1 이상 2이하의 값만 사용할 수 있습니다."),
    OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "67021", "옵션1 추가 가격은 0 이상 1억 이하의 값만 사용할 수 있습니다."),
    PRODUCT_OPTION2_REQUEST_REQUIRE(HttpStatus.BAD_REQUEST, "67022", "상품 옵션 2 객체는 필수입니다."),
    OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "67023", "옵션2 추가 가격은 0 이상 1억 이하의 값만 사용할 수 있습니다."),
    PRODUCT_OPTION1_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "67024", "상품 옵션1이 존재 시 옵션1의 이름 정보는 값은 필수입니다."),
    PRODUCT_OPTION2_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "67025", "상품 옵션2이 존재 시 옵션2의 이름 정보는 값은 필수입니다."),
    PRODUCT_IMAGES_REQUIRE(HttpStatus.BAD_REQUEST, "67026", "상품 이미지는 필수입니다."),

    PRODUCT_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "67500", "스토어, 스토어 스태프 등록에 실패하였습니다. 관리자에가 문의해 주십시오.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public static ProductError of(String error) {
        return Arrays.stream(ProductError.values())
                .filter(productError -> productError.name().equalsIgnoreCase(error))
                .findAny()
                .orElse(null);
    }
}
