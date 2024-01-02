package com.stylelab.category.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stylelab.category.dto.ProductCategoriesDto;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ProductCategoriesResponse(List<Categories> categories) {

    @Builder
    public record Categories(
            String categoryName,
            String categoryPath,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String parentCategory,
            List<Categories> childCategories
    ) {

        public static Categories of(ProductCategoriesDto productCategoriesDto) {
            return Categories.builder()
                    .categoryName(productCategoriesDto.categoryName())
                    .categoryPath(productCategoriesDto.categoryPath())
                    .parentCategory(productCategoriesDto.parentCategory())
                    .childCategories(new ArrayList<>())
                    .build();
        }

        public void addAllChildCategories(List<Categories> childCategories) {
            this.childCategories.addAll(childCategories);
        }
    }

    public static ProductCategoriesResponse createResponse(List<Categories> categories) {
        return ProductCategoriesResponse.builder()
                .categories(categories)
                .build();
    }
}
