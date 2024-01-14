package com.stylelab.product.domain;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductOption2Test {

    @Test
    @DisplayName("상품 옵션2에 상품 옵션1 추가 실패 - 상품 옵션1이 null인 경우 상품 옵션1 추가에 실패한다.")
    public void failureAddProduct() throws Exception {
        //given
        ProductOption2 productOption2 = ProductOption2.builder()
                .option2Name("화이트")
                .additionalPrice(0)
                .quantity(0)
                .soldOut(false)
                .deleted(false)
                .build();

        //when
        ProductException productException = assertThrows(ProductException.class,
                () -> productOption2.addProductOption1(null));

        //then
        assertEquals(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE, productException.getServiceError());
    }
}
