package com.stylelab.product.repository;

import com.stylelab.product.repository.dto.ProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryRepository {

    Page<ProductCollection> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable);
}
