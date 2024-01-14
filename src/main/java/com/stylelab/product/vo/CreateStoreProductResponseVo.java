package com.stylelab.product.vo;

public record CreateStoreProductResponseVo(Long productId) {

    public static CreateStoreProductResponseVo createResponse(Long productId) {
        return new CreateStoreProductResponseVo(productId);
    }
}
