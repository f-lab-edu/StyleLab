package com.stylelab.product.domain;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.file.constant.ImageType;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_images")
public class ProductImage extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int imageOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType imageType;

    @Builder
    public ProductImage(Product product, String imageUrl, int imageOrder, ImageType imageType) {
        this.product = product;
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
        this.imageType = imageType;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new ProductException(ProductError.PRODUCT_REQUIRE, ProductError.PRODUCT_REQUIRE.getMessage());
        }

        this.product = product;
    }
}
