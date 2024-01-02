package com.stylelab.category.repository;

import com.stylelab.category.domain.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, Long> {
}
