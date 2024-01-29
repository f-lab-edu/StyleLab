package com.stylelab.category.service;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.repository.ProductCategoriesRepository;
import com.stylelab.category.repository.ProductCategoryQueryDslRepository;
import com.stylelab.category.repository.ProductCategoryQueryDslRepositoryStrategyMap;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCategoriesServiceImpl implements ProductCategoriesService {

    private final ProductCategoriesRepository productCategoriesRepository;
    private final ProductCategoryQueryDslRepositoryStrategyMap productCategoryRepositoryMap;

    @Override
    @Cacheable("productCategoriesDtos")
    public List<ProductCategoriesDto> findAllCategories() {
        return productCategoriesRepository.findAll().stream()
                .map(ProductCategoriesDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            ProductCategoryType productCategoryType, Long productId, String productName, String productCategoryPath,
            Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        ProductCategoryQueryDslRepository productCategoryRepository =
                productCategoryRepositoryMap.getProductCategoryRepository(productCategoryType);

        return productCategoryRepository.findAllProductCategoryConditions(
                productId, productName, productCategoryPath, price1, price2, discountRate, pageable
        );
    }

}
