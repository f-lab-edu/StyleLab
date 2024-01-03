package com.stylelab.category.dto;

public record ProductCategoriesDto(
        Long productCategoryId,
        String categoryName,
        String categoryPath,
        String parentCategory
) {

    public ProductCategoriesDto(
            String categoryName,
            String categoryPath) {
        this(null, categoryName, categoryPath, null);
    }

    public ProductCategoriesDto(
            String categoryName,
            String categoryPath,
            String parentCategory) {
        this(null, categoryName, categoryPath, parentCategory);
    }

    public static ProductCategoriesDto toDto(com.stylelab.category.domain.ProductCategories productCategories) {
        return new ProductCategoriesDto(
                productCategories.getProductCategoryId(),
                productCategories.getCategoryName(),
                productCategories.getCategoryPath(),
                productCategories.getParentCategory()
        );
    }
}