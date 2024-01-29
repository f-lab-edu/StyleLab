package com.stylelab.category.repository;

import com.stylelab.category.repository.dto.ProductCategoryCollection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductCategoryQueryDslRepository {

    Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable);

    default boolean isLast(Pageable pageable, List<ProductCategoryCollection> results) {
        boolean isLast = false;
        if (results.size() > pageable.getPageSize()) {
            isLast = true;
            results.remove(pageable.getPageSize());
        }

        return isLast;
    }
}
