package com.stylelab.category.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stylelab.category.dto.ProductCategoriesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoriesResponse {

    private List<Categories> categories;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Categories {

        private String categoryName;
        private String categoryPath;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String parentCategory;
        private List<Categories> childCategories;

        public static Categories of(ProductCategoriesDto productCategoriesDto) {
            return Categories.builder()
                    .categoryName(productCategoriesDto.getCategoryName())
                    .categoryPath(productCategoriesDto.getCategoryPath())
                    .parentCategory(productCategoriesDto.getParentCategory())
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
