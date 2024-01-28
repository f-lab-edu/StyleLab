package com.stylelab.category.repository.top;

import com.stylelab.category.domain.top.HoodieProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoodieProductCategoryRepository extends JpaRepository<HoodieProductCategory, Long> {
}
