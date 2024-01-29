package com.stylelab.category.repository;

import com.stylelab.category.constant.ProductCategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Map;

@AllArgsConstructor
@Builder
public class ProductCategoryQueryDslRepositoryStrategyMap {

    private final Map<ProductCategoryType, ProductCategoryQueryDslRepository> productCategoryRepositoryMap;

    public ProductCategoryQueryDslRepository getProductCategoryRepository(ProductCategoryType productCategoryType) {
        return this.productCategoryRepositoryMap.get(productCategoryType);
    }
}
