package com.stylelab.category.repository.shoes;

import com.stylelab.category.domain.shoes.ShoesProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesProductCategoryRepository extends JpaRepository<ShoesProductCategory, Long> {
}
