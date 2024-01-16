package com.stylelab.product.service;

import com.stylelab.product.repository.ProductQueryRepository;
import com.stylelab.product.repository.dto.ProductCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductQueryRepository productQueryRepository;

    @Override
    public Page<ProductCollection> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        return productQueryRepository.findByProductByConditions(productName, productCategoryPath, price1, price2, discountRate, pageable);
    }
}
