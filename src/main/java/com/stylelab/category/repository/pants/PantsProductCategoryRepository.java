package com.stylelab.category.repository.pants;

import com.stylelab.category.domain.pants.PantsProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantsProductCategoryRepository extends JpaRepository<PantsProductCategory, Long> {
}
