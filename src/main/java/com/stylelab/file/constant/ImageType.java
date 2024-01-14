package com.stylelab.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {

    PRODUCT_ENTRY_MAIN("상품 항복 대표 이미지", 1),
    PRODUCT_ENTRY_SUB("상품 항목 서브 이미지", 5),
    PRODUCT_DESCRIPTION("상품 상세 이미지", 10);

    private final String description;
    private final int maxImageCount;
}
