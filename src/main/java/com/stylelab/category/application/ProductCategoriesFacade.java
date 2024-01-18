package com.stylelab.category.application;

import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.presentation.response.ProductCategoriesResponse;
import com.stylelab.category.service.ProductCategoriesService;
import com.stylelab.category.service.ProductCategoryTree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCategoriesFacade {

    private final ProductCategoriesService productCategoriesService;
    private final ProductCategoryTree productCategoryTree;

    public ProductCategoriesResponse findAllCategories() {
        List<ProductCategoriesDto> productCategoryDtos = productCategoriesService.findAllCategories();
        return ProductCategoriesResponse.createResponse(productCategoryTree.generateCategoryTreeRecursively(productCategoryDtos));
    }
}
