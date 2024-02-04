package com.stylelab.category.application;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.exception.ProductCategoryError;
import com.stylelab.category.exception.ProductCategoryException;
import com.stylelab.category.presentation.response.ProductCategoriesResponse;
import com.stylelab.category.presentation.response.ProductCategoryCollectionResponse;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import com.stylelab.category.service.ProductCategoriesService;
import com.stylelab.category.service.ProductCategoryTree;
import com.stylelab.common.dto.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCategoriesFacade {

    private final ProductCategoryTree productCategoryTree;
    private final ProductCategoriesService productCategoriesService;

    public ProductCategoriesResponse findAllCategories() {
        List<ProductCategoriesDto> productCategoryDtos = productCategoriesService.findAllCategories();
        return ProductCategoriesResponse.createResponse(productCategoryTree.generateCategoryTreeRecursively(productCategoryDtos));
    }

    public PagingResponse<ProductCategoryCollectionResponse> findAllProductCategoryConditions(
            final Long productId, final String productName, final String productCategoryPath,
            final Integer price1, final Integer price2, final Integer discountRate, final Pageable pageable) {

        ProductCategoryType productCategoryType = ProductCategoryType.of(productCategoryPath)
                .orElseThrow(() ->  new ProductCategoryException(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH));

        Slice<ProductCategoryCollection> productCategoryConditions =
                productCategoriesService.findAllProductCategoryConditions(
                        productCategoryType, productId, productName, productCategoryPath, price1, price2, discountRate, pageable
                );

        int contentSize = productCategoryConditions.getSize();
        List<ProductCategoryCollection> content = productCategoryConditions.getContent();
        Long nextToken = !productCategoryConditions.isLast() ? content.get(contentSize - 1).productId() : null;
        return PagingResponse.createCursorPagingResponse(
                productCategoryConditions
                        .map(ProductCategoryCollectionResponse::createProductCategoryCollectionResponse),
                nextToken
        );
    }
}
