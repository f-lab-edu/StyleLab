package com.stylelab.category.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.stylelab.file.constant.ImageType;

import java.time.LocalDateTime;

public record ProductCategoryCollection(
        Long productCategoryId,
        Long productId,
        Long storeId,
        String storeName,
        String productCategoryPath,
        String productCategoryName,
        String productMainImage,
        ImageType productMainImageType,
        String name,
        int price,
        int discountPrice,
        int discountRate,
        boolean soldOut,
        boolean deleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    @QueryProjection
    public ProductCategoryCollection(
            Long productCategoryId, Long productId, Long storeId, String storeName, String productCategoryPath,
            String productCategoryName, String productMainImage, ImageType productMainImageType, String name,
            int price, int discountPrice, int discountRate, boolean soldOut, boolean deleted,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productCategoryId = productCategoryId;
        this.productId = productId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.productCategoryPath = productCategoryPath;
        this.productCategoryName = productCategoryName;
        this.productMainImage = productMainImage;
        this.productMainImageType = productMainImageType;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.soldOut = soldOut;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
