package com.stylelab.product.domain;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductOption1Test {

    @Test
    @DisplayName("상품 옵션1에 상품 추가 실패 - 상품이 null인 경우 상품 추가에 실패한다.")
    public void failureAddProduct() throws Exception {
        //given
        ProductOption1 productOption1 = ProductOption1.builder()
                .option1Name("화이트")
                .additionalPrice(0)
                .quantity(0)
                .soldOut(false)
                .deleted(false)
                .build();

        //when
        ProductException productException = assertThrows(ProductException.class,
                () -> productOption1.addProduct(null));

        //then
        assertEquals(ProductError.PRODUCT_REQUIRE, productException.getServiceError());
    }

    @Test
    @DisplayName("상품 옵션2 추가")
    public void additionalProductOption2OfProductOption1() throws Exception {
        //given
        ProductOption1 productOption1 = ProductOption1.builder()
                .option1Name("화이트")
                .additionalPrice(0)
                .quantity(0)
                .soldOut(false)
                .deleted(false)
                .build();
        List<ProductOption2> productOption2s = Collections.singletonList(
                ProductOption2.builder()
                        .option2Name("XL")
                        .additionalPrice(10000)
                        .quantity(1000)
                        .soldOut(false)
                        .deleted(false)
                        .build()
        );

        //when
        productOption1.additionalProductOption2(productOption2s);

        //then
        assertEquals(1, productOption1.getProductOption2s().size());
        for (ProductOption2 productOption2 : productOption2s) {
            assertEquals(productOption1, productOption2.getProductOption1());
        }
    }

    @Test
    @DisplayName("상품 옵션2 추가 실패 - 상품 옵션2 객체가 null인 경우 옵션2 추가에 실패한다.")
    public void failureAdditionalProductOption2OfProductOption1() throws Exception {
        //given
        ProductOption1 productOption1 = ProductOption1.builder()
                .option1Name("화이트")
                .additionalPrice(0)
                .quantity(0)
                .soldOut(false)
                .deleted(false)
                .build();
        List<ProductOption2> productOption2s = null;

        //when
        ProductException productException = assertThrows(ProductException.class,
                () -> productOption1.additionalProductOption2(productOption2s));

        //then
        assertEquals(ProductError.PRODUCT_OPTION2_REQUEST_REQUIRE, productException.getServiceError());
    }
}
