package com.stylelab.product.domain;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.store.domain.Store;
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
import org.springframework.util.ObjectUtils;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductImage> productImages = new ArrayList<>();

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

    public void calculateDiscountPrice() {
        if (discountRate < 0 || discountRate > 100) {
            throw new ProductException(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE, ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE.getMessage());
        }

        int discountedPrice = (int) (this.price * (double) this.discountRate / 100);
        int totalDiscountedPrice = (int) (Math.floor((double) (this.price - discountedPrice) / 1000) * 1000);
        this.discountPrice = Math.max(totalDiscountedPrice, 3000);
    }

    public void addStore(Store store) {
        if (store == null) {
            throw new ProductException(ProductError.STORE_ID_REQUIRE, ProductError.STORE_ID_REQUIRE.getMessage());
        }

        this.store = store;
    }

    public void additionalProductOption1(List<ProductOption1> productOption1s) {
        if (ObjectUtils.isEmpty(productOption1s)) {
            throw new ProductException(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE, ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE.getMessage());
        }

        for (ProductOption1 productOption1 : productOption1s) {
            productOption1.addProduct(this);
            this.productOption1s.add(productOption1);
        }
    }

    public void addProductImages(List<ProductImage> productImages) {
        if (ObjectUtils.isEmpty(productImages)) {
            throw new ProductException(ProductError.PRODUCT_IMAGES_REQUIRE, ProductError.PRODUCT_IMAGES_REQUIRE.getMessage());
        }

        for (ProductImage productImage : productImages) {
            productImage.addProduct(this);
            this.productImages.add(productImage);
        }
    }
}
