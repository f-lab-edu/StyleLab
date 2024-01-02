package com.stylelab.category.dto;

import com.stylelab.category.domain.ProductCategories;
import lombok.Builder;

@Builder
public record ProductCategoriesDto(
        Long productCategoryId,
        String categoryName,
        String categoryPath,
        String parentCategory
) {


    public static ProductCategoriesDto toDto(ProductCategories productCategories) {
        return ProductCategoriesDto.builder()
                .productCategoryId(productCategories.getProductCategoryId())
                .categoryName(productCategories.getCategoryName())
                .categoryPath(productCategories.getCategoryPath())
                .parentCategory(productCategories.getParentCategory())
                .build();
    }
}