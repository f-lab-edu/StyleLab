package com.stylelab.category.presentation.response;

import com.stylelab.category.repository.dto.ProductCategoryCollection;
import com.stylelab.file.constant.ImageType;

import java.time.format.DateTimeFormatter;

public record ProductCategoryCollectionResponse(
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
        String createdAt
) {

    public static ProductCategoryCollectionResponse createProductCategoryCollectionResponse(ProductCategoryCollection productCategoryCollection) {
        return new ProductCategoryCollectionResponse(
                productCategoryCollection.productId(),
                productCategoryCollection.storeId(),
                productCategoryCollection.name(),
                productCategoryCollection.productCategoryPath(),
                productCategoryCollection.productCategoryName(),
                productCategoryCollection.productMainImage(),
                productCategoryCollection.productMainImageType(),
                productCategoryCollection.name(),
                productCategoryCollection.price(),
                productCategoryCollection.discountPrice(),
                productCategoryCollection.discountRate(),
                productCategoryCollection.soldOut(),
                productCategoryCollection.deleted(),
                productCategoryCollection.createdAt().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
    }
}
