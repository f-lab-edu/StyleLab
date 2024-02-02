package com.stylelab.category.domain;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.file.constant.ImageType;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory extends BaseEntity {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String productCategoryPath;

    @Column(nullable = false)
    private String productCategoryName;

    @Column(nullable = false)
    private String productMainImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType productMainImageType;

    @Column(nullable = false)
    private String name;

    private int price;

    private int discountPrice;

    private int discountRate;

    private boolean soldOut;

    private boolean deleted;
}
