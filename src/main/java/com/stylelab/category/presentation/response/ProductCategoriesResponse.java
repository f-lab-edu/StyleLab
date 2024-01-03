package com.stylelab.category.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stylelab.category.dto.ProductCategoriesDto;

import java.util.ArrayList;
import java.util.List;

public record ProductCategoriesResponse(List<Categories> categories) {

    public record Categories(
            String categoryName,
            String categoryPath,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String parentCategory,
            List<Categories> childCategories
    ) {

        public static Categories of(ProductCategoriesDto productCategoriesDto) {
            return new Categories(
                    productCategoriesDto.categoryName(),
                    productCategoriesDto.categoryPath(),
                    productCategoriesDto.parentCategory(),
                    new ArrayList<>()
            );
        }

        public void addAllChildCategories(List<Categories> childCategories) {
            this.childCategories.addAll(childCategories);
        }
    }

    public static ProductCategoriesResponse createResponse(List<Categories> categories) {
        return new ProductCategoriesResponse(categories);
    }
}
