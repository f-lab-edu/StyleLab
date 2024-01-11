package com.stylelab.product.domain;

import com.stylelab.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "product_option_2")
public class ProductOption2 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productOption2Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option1_id", nullable = false)
    private ProductOption1 productOption1;

    @Column(nullable = false)
    private String option2Name;

    private int quantity;

    private int additionalPrice;

    private boolean soldOut;

    private boolean deleted;

    @Builder
    public ProductOption2(ProductOption1 productOption1, String option2Name, int quantity, int additionalPrice, boolean soldOut, boolean deleted) {
        this.productOption1 = productOption1;
        this.option2Name = option2Name;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
        this.soldOut = soldOut;
        this.deleted = deleted;
    }

    public void addProductOption1(ProductOption1 productOption1) {
        this.productOption1 = productOption1;
    }
}
