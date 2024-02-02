package com.stylelab.category.exception;

import com.stylelab.common.exception.CommonError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProductCategoryError implements CommonError {

    INVALID_PRODUCT_CATEGORY_PATH(HttpStatus.BAD_REQUEST, "70000", "유효하지 않은 상품 카테고리입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public static ProductCategoryError of(String error) {
        return Arrays.stream(ProductCategoryError.values())
                .filter(ProductCategoryError -> ProductCategoryError.name().equalsIgnoreCase(error))
                .findAny()
                .orElse(null);
    }
}
