package com.stylelab.store.presentation.response;

import com.stylelab.product.vo.CreateStoreProductResponseVo;

public record CreateStoreProductResponse(Long productId) {

    public static CreateStoreProductResponse createResponse(CreateStoreProductResponseVo createStoreProductResponseVo) {
        return new CreateStoreProductResponse(createStoreProductResponseVo.productId());
    }
}
