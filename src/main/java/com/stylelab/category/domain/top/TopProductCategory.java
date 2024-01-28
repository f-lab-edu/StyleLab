package com.stylelab.category.domain.top;

import com.stylelab.category.domain.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "product_category_001001")
public class TopProductCategory extends ProductCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_001001_id", nullable = false)
    private Long id;

}
