package com.stylelab.category.repository;

import com.stylelab.category.dto.ProductCategoryCondition;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductCategoryJdbcRepository {

    Slice<ProductCategoryCollection> findAllProductCategoryConditions(ProductCategoryCondition productCategoryCondition);

    default boolean isLast(Pageable pageable, List<ProductCategoryCollection> results) {
        boolean isLast = false;
        if (results.size() > pageable.getPageSize()) {
            isLast = true;
            results.remove(pageable.getPageSize());
        }

        return isLast;
    }
}
