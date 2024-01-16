package com.stylelab.product.presentation.response;

import com.stylelab.file.constant.ImageType;
import com.stylelab.product.repository.dto.ProductCollection;

public record ProductCollectionResponse(
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

    public static ProductCollectionResponse createProductCollectionResponse(ProductCollection productCollection) {
        return new ProductCollectionResponse(
                productCollection.productId(),
                productCollection.name(),
                productCollection.productCategoryPath(),
                productCollection.productCategoryName(),
                productCollection.price(),
                productCollection.discountPrice(),
                productCollection.discountRate(),
                productCollection.imageUrl(),
                productCollection.imageType()
        );
    }
}
