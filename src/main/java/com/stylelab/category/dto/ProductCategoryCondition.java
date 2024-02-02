package com.stylelab.category.dto;

import com.stylelab.category.constant.ProductCategoryType;
import org.springframework.data.domain.Pageable;

public record ProductCategoryCondition(
        ProductCategoryType productCategoryType,
        Long productId,
        String productName,
        String productCategoryPath,
        Integer price1,
        Integer price2,
        Integer discountRate,
        Pageable pageable
) {

    public static ProductCategoryCondition createProductCategoryCondition(
            ProductCategoryType productCategoryType, Long productId, String productName, String productCategoryPath,
            Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        return new ProductCategoryCondition(
                productCategoryType, productId, productName, productCategoryPath, price1, price2, discountRate, pageable
        );
    }
}
