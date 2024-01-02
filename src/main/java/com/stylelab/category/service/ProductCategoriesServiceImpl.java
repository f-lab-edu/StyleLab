package com.stylelab.category.service;

import com.stylelab.category.dto.ProductCategoriesDto;
import com.stylelab.category.repository.ProductCategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public List<ProductCategoriesDto> findAllCategories() {
        return productCategoriesRepository.findAll().stream()
                .map(ProductCategoriesDto::toDto)
                .collect(Collectors.toList());
    }
}
