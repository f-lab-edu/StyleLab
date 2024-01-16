package com.stylelab.product.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.stylelab.file.constant.ImageType;

public record ProductCollection(
        Long productId,
        String name,
        String productCategoryPath,
        String productCategoryName,
        int price,
        int discountPrice,
        int discountRate,
        String imageUrl,
        ImageType imageType
) {

    @QueryProjection
    public ProductCollection(
            Long productId, String name, String productCategoryPath, String productCategoryName, int price,
            int discountPrice, int discountRate, String imageUrl, ImageType imageType) {
        this.productId = productId;
        this.name = name;
        this.productCategoryPath = productCategoryPath;
        this.productCategoryName = productCategoryName;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }
}
