package com.stylelab.category.dto;

import com.stylelab.category.domain.ProductCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoriesDto {

    private Long productCategoryId;
    private String categoryName;
    private String categoryPath;
    private String parentCategory;

    public static ProductCategoriesDto toDto(ProductCategories productCategories) {
        return ProductCategoriesDto.builder()
                .productCategoryId(productCategories.getProductCategoryId())
                .categoryName(productCategories.getCategoryName())
                .categoryPath(productCategories.getCategoryPath())
                .parentCategory(productCategories.getParentCategory())
                .build();
    }
}