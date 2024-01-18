package com.stylelab.category.service;

import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.presentation.response.ProductCategoriesResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductCategoryTree {

    @Cacheable("productCategoryTree")
    public List<ProductCategoriesResponse.Categories> generateCategoryTreeRecursively(List<ProductCategoriesDto> productCategoryDtos) {
        List<ProductCategoriesResponse.Categories> parentCategories = productCategoryDtos.stream()
                .filter(productCategoriesDto -> !StringUtils.hasText(productCategoriesDto.parentCategory()))
                .map(ProductCategoriesResponse.Categories::of)
                .collect(Collectors.toList());

        generateCategoryTreeRecursively(productCategoryDtos, parentCategories);

        return parentCategories;
    }

    private void generateCategoryTreeRecursively(
            List<ProductCategoriesDto> productCategoryDtos, List<ProductCategoriesResponse.Categories> categories) {
        for (ProductCategoriesResponse.Categories category : categories) {
            List<ProductCategoriesResponse.Categories> childCategories = productCategoryDtos.stream()
                    .filter(productCategoriesDto -> Objects.equals(productCategoriesDto.parentCategory(), category.categoryPath()))
                    .map(ProductCategoriesResponse.Categories::of)
                    .collect(Collectors.toList());

            category.addAllChildCategories(childCategories);

            generateCategoryTreeRecursively(productCategoryDtos, childCategories);
        }
    }
}
