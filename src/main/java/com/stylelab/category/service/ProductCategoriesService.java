package com.stylelab.category.service;

import com.stylelab.category.dto.ProductCategoriesDto;

import java.util.List;

public interface ProductCategoriesService {

    List<ProductCategoriesDto> findAllCategories();
}
