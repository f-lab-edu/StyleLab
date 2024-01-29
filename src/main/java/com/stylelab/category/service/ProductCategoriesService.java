package com.stylelab.category.service;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductCategoriesService {

    List<ProductCategoriesDto> findAllCategories();

    Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            ProductCategoryType productCategoryType, Long productId, String productName, String productCategoryPath,
            Integer price1, Integer price2, Integer discountRate, Pageable pageable);
}
