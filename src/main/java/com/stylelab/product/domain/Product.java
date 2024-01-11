package com.stylelab.product.domain;

import com.stylelab.common.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "products")
public class Product extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productCategoryPath;

    @Column(nullable = false)
    private String name;

    private int price;

    private int discountPrice;

    private int discountRate;

    private boolean useOption;

    private int optionDepth;

    private String option1;

    private String option2;

    private int quantity;

    private boolean soldOut;

    private boolean deleted;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductOption1> productOption1s = new ArrayList<>();

    @Builder
    public Product(
            String productCategoryPath, String name, int price, int discountPrice, int discountRate,
            boolean useOption, int optionDepth, String option1, String option2, int quantity, boolean soldOut, boolean deleted) {
        this.productCategoryPath = productCategoryPath;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.useOption = useOption;
        this.optionDepth = optionDepth;
        this.option1 = option1;
        this.option2 = option2;
        this.quantity = quantity;
        this.soldOut = soldOut;
        this.deleted = deleted;
    }

    public void additionalProductOption1(ProductOption1 productOption1) {
        productOption1.addProduct(this);
        this.productOption1s.add(productOption1);
    }

    public void additionalProductOption1(List<ProductOption1> productOption1s) {
        productOption1s.forEach(productOption1 -> {
            productOption1.addProduct(this);
            this.productOption1s.add(productOption1);
        });
    }
}
