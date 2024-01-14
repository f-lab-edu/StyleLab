package com.stylelab.product.domain;

import com.stylelab.file.constant.ImageType;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductImageTest {

    @Test
    @DisplayName("상품 이미지에 상품 추가 실패 - 상품이 null인 경우 상품 추가에 실패한다.")
    public void failureAddProduct() throws Exception {
        //given
        ProductImage productImage = ProductImage.builder()
                .imageOrder(0)
                .imageUrl("https://image1")
                .imageType(ImageType.PRODUCT_ENTRY_MAIN)
                .build();

        //when
        ProductException productException = assertThrows(ProductException.class,
                () -> productImage.addProduct(null));

        //then
        assertEquals(ProductError.PRODUCT_REQUIRE, productException.getServiceError());
    }
}
