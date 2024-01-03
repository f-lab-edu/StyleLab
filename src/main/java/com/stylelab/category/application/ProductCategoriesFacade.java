package com.stylelab.category.application;

import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.presentation.response.ProductCategoriesResponse;
import com.stylelab.category.service.ProductCategoriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.stylelab.category.presentation.response.ProductCategoriesResponse.Categories;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCategoriesFacade {

    private final ProductCategoriesService productCategoriesService;

    public ProductCategoriesResponse findAllCategories() {
        List<ProductCategoriesDto> productCategoryDtos = productCategoriesService.findAllCategories();

        List<Categories> parentCategories = productCategoryDtos.stream()
                .filter(productCategoriesDto -> !StringUtils.hasText(productCategoriesDto.parentCategory()))
                .map(Categories::of)
                .collect(Collectors.toList());

        generateCategoryTreeRecursively(productCategoryDtos, parentCategories);

        return ProductCategoriesResponse.createResponse(parentCategories);
    }

    private void generateCategoryTreeRecursively(
            List<ProductCategoriesDto> productCategoryDtos, List<Categories> categories) {
        for (Categories category : categories) {
            List<Categories> childCategories = productCategoryDtos.stream()
                    .filter(productCategoriesDto -> Objects.equals(productCategoriesDto.parentCategory(), category.categoryPath()))
                    .map(Categories::of)
                    .collect(Collectors.toList());

            category.addAllChildCategories(childCategories);

            generateCategoryTreeRecursively(productCategoryDtos, childCategories);
        }
    }
}
