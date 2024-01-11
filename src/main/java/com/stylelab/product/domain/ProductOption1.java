package com.stylelab.product.domain;

import com.stylelab.common.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "product_option_1")
public class ProductOption1 extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productOption1Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String option1Name;

    private int quantity;

    private int additionalPrice;

    private boolean soldOut;

    private boolean deleted;

    @OneToMany(mappedBy = "productOption1", cascade = CascadeType.PERSIST)
    private List<ProductOption2> productOption2s = new ArrayList<>();

    @Builder
    public ProductOption1(Product product, String option1Name, int quantity, int additionalPrice, boolean soldOut, boolean deleted) {
        this.product = product;
        this.option1Name = option1Name;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
        this.soldOut = soldOut;
        this.deleted = deleted;
    }

    public void addProduct(Product product) {
        this.product = product;
    }

    public void additionalProductOption2(ProductOption2 productOption2) {
        productOption2.addProductOption1(this);
        this.productOption2s.add(productOption2);
    }

    public void additionalProductOption2(List<ProductOption2> productOption2s) {
        productOption2s.forEach(productOption2 -> {
            productOption2.addProductOption1(this);
            this.productOption2s.add(productOption2);
        });
    }
}
