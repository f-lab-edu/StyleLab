package com.stylelab.category.domain.outer;

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
@Entity(name = "product_category_001003003")
public class BubbleJacketProductCategory extends ProductCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_001003003_id", nullable = false)
    private Long id;

}
