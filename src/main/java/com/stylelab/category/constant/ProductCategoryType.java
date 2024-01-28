package com.stylelab.category.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategoryType {

    // TOP
    TOP("001001", "상의", null),
    SWEATSHIRT_AND_HOODIE("001001001", "맨투맨/후드", TOP),
    SWEATSHIRT("001001001001", "맨투맨", SWEATSHIRT_AND_HOODIE),
    HOODIE("001001001002", "후드", SWEATSHIRT_AND_HOODIE),
    KNIT_AND_SWEATER("001001002", "니트/스웨터", TOP),
    KNIT("001001002001", "니트", KNIT_AND_SWEATER),
    SWEATER("001001002001", "스웨터", KNIT_AND_SWEATER),
    SHORT_SLEEVE_T_SHIRT("001001003", "반소매 티셔츠", TOP),
    SHIRT("001001004", "셔츠", TOP),
    SLEEVE_T_SHIRT("001001005", "민소매 티셔츠", TOP),

    // PANTS
    PANTS("001002", "바지", null),
    DENIM_PANTS("001002001", "데님 팬츠", PANTS),
    COTTON_PANTS("001002002", "코튼 팬츠", PANTS),
    SLACKS("001002002", "슬랙스", PANTS),

    // OUTER
    OUTER("001003", "아우터", null),
    HOODED_ZIP_UP("001003001", "후드 집업", OUTER),
    CARDIGAN("001003002", "카디건", OUTER),
    BUBBLE_JACKET("001003003", "패딩", OUTER),
    COAT("001003004", "코트", OUTER),

    // SHOES
    SHOES("001004", "신발", null),
    DRESS_SHOES("001004001", "구두", SHOES),
    SANDAL("001004002", "샌들", SHOES),
    SLIPPER("001004003", "슬리퍼", SHOES),
    CANVAS_AND_SNEAKERS("001004005", "캔버스/단화", SHOES),
    CANVAS("001004005001", "캔버스", CANVAS_AND_SNEAKERS),
    SNEAKERS("001004005002", "단화", CANVAS_AND_SNEAKERS),
    ;

    private final String productCategoryPath;
    private final String productCategoryName;
    private final ProductCategoryType productCategoryType;
}
