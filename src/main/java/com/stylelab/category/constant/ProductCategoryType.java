package com.stylelab.category.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ProductCategoryType {

    // TOP
    TOP("product_category_001001", "001001", "상의", null),
    SWEATSHIRT_AND_HOODIE("product_category_001001001", "001001001", "맨투맨/후드", TOP),
    SWEATSHIRT("product_category_001001001001", "001001001001", "맨투맨", SWEATSHIRT_AND_HOODIE),
    HOODIE("product_category_001001001002", "001001001002", "후드", SWEATSHIRT_AND_HOODIE),
    KNIT_AND_SWEATER("product_category_001001002", "001001002", "니트/스웨터", TOP),
    KNIT("product_category_001001002001", "001001002001", "니트", KNIT_AND_SWEATER),
    SWEATER("product_category_001001002002", "001001002002", "스웨터", KNIT_AND_SWEATER),
    SHORT_SLEEVE_T_SHIRT("product_category_001001003", "001001003", "반소매 티셔츠", TOP),
    SHIRT("product_category_001001004", "001001004", "셔츠", TOP),
    SLEEVE_T_SHIRT("product_category_001001005", "001001005", "민소매 티셔츠", TOP),

    // PANTS
    PANTS("product_category_001002", "001002", "바지", null),
    DENIM_PANTS("product_category_001002001", "001002001", "데님 팬츠", PANTS),
    COTTON_PANTS("product_category_001002002", "001002002", "코튼 팬츠", PANTS),
    SLACKS("product_category_001002003", "001002003", "슬랙스", PANTS),

    // OUTER
    OUTER("product_category_001001", "001003", "아우터", null),
    HOODED_ZIP_UP("product_category_001003001", "001003001", "후드 집업", OUTER),
    CARDIGAN("product_category_001003002", "001003002", "카디건", OUTER),
    BUBBLE_JACKET("product_category_001003003", "001003003", "패딩", OUTER),
    COAT("product_category_001003004", "001003004", "코트", OUTER),

    // SHOES
    SHOES("product_category_001004", "001004", "신발", null),
    DRESS_SHOES("product_category_001004001", "001004001", "구두", SHOES),
    SANDAL("product_category_001004002", "001004002", "샌들", SHOES),
    SLIPPER("product_category_001004003", "001004003", "슬리퍼", SHOES),
    CANVAS_AND_SNEAKERS("product_category_001004005", "001004005", "캔버스/단화", SHOES),
    CANVAS("product_category_001004005001", "001004005001", "캔버스", CANVAS_AND_SNEAKERS),
    SNEAKERS("product_category_001004005002", "001004005002", "단화", CANVAS_AND_SNEAKERS),
    ;

    private final String tableName;
    private final String productCategoryPath;
    private final String productCategoryName;
    private final ProductCategoryType productCategoryType;

    public static Optional<ProductCategoryType> of(String productCategoryPath) {
        return Arrays.stream(ProductCategoryType.values())
                .filter(productCategoryType -> productCategoryType.productCategoryPath.equalsIgnoreCase(productCategoryPath))
                .findAny();
    }
}
