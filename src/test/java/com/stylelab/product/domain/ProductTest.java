package com.stylelab.product.domain;

import com.stylelab.file.constant.ImageType;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {

    @Test
    @DisplayName("상품 할인 가격 측정 테스트 - 상품 가격 100,000, 할인율이 10%인 경우 할인가는 90,000원")
    public void productCalculateDiscountTest_01() throws Exception {
        //given
        Product product = Product.builder()
                .productCategoryPath("001001001")
                .name("coby 맨투맨")
                .price(100000)
                .discountRate(10)
                .useOption(false)
                .optionDepth(0)
                .quantity(1000)
                .soldOut(false)
                .deleted(false)
                .build();

        //when
        product.calculateDiscountPrice();

        //then
        assertEquals(90_000, product.getDiscountPrice());
    }

    @Test
    @DisplayName("상품 할인 가격 측정 테스트 - 상품 가격 100,000, 할인율이 99인 경우 할인가는 3,000원")
    public void productCalculateDiscountTest_02() throws Exception {
        //given
        Product product = Product.builder()
                .productCategoryPath("001001001")
                .name("coby 맨투맨")
                .price(100000)
                .discountRate(99)
                .useOption(false)
                .optionDepth(0)
                .quantity(1000)
                .soldOut(false)
                .deleted(false)
                .build();

        //when
        product.calculateDiscountPrice();

        //then
        assertEquals(3_000, product.getDiscountPrice());
    }

    @Test
    @DisplayName("상품 옵션 추가")
    public void additionalProductOption1OfProduct() throws Exception {
        //given
        Product product = Product.builder()
                .productCategoryPath("001001001")
                .name("coby 맨투맨")
                .price(100000)
                .discountRate(99)
                .useOption(false)
                .optionDepth(0)
                .quantity(1000)
                .soldOut(false)
                .deleted(false)
                .build();

        List<ProductOption1> productOption1s = Collections.singletonList(
                ProductOption1.builder()
                        .option1Name("XL")
                        .additionalPrice(10000)
                        .quantity(1000)
                        .soldOut(false)
                        .deleted(false)
                        .build()
        );

        //when
        product.additionalProductOption1(productOption1s);

        //then
        assertEquals(1, product.getProductOption1s().size());
        for (ProductOption1 productOption1 : productOption1s) {
            assertEquals(product, productOption1.getProduct());
        }
    }

    @Test
    @DisplayName("상품 옵션 추가 실패 - 상품 옵션1 객체가 null인 경우 옵션1 추가에 실패한다.")
    public void failureAdditionalProductOption1OfProduct() throws Exception {
        //given
        Product product = Product.builder()
                .productCategoryPath("001001001")
                .name("coby 맨투맨")
                .price(100000)
                .discountRate(99)
                .useOption(false)
                .optionDepth(0)
                .quantity(1000)
                .soldOut(false)
                .deleted(false)
                .build();

        List<ProductOption1> productOption1s = null;

        //when
        ProductException productException = assertThrows(ProductException.class,
                () -> product.additionalProductOption1(productOption1s));

        //then
        assertEquals(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE, productException.getServiceError());
    }

    @Test
    @DisplayName("상품 이미지 추가")
    public void productImagesOfProduct() throws Exception {
        //given
        Product product = Product.builder()
                .productCategoryPath("001001001")
                .name("coby 맨투맨")
                .price(100000)
                .discountRate(99)
                .useOption(false)
                .optionDepth(0)
                .quantity(1000)
                .soldOut(false)
                .deleted(false)
                .build();

        List<ProductImage> productImages = Arrays.asList(
                ProductImage.builder()
                        .imageOrder(0)
                        .imageUrl("https://image1")
                        .imageType(ImageType.PRODUCT_ENTRY_MAIN)
                        .build(),
                ProductImage.builder()
                        .imageOrder(0)
                        .imageUrl("https://image2")
                        .imageType(ImageType.PRODUCT_ENTRY_SUB)
                        .build(),
                ProductImage.builder()
                        .imageOrder(0)
                        .imageUrl("https://image3")
                        .imageType(ImageType.PRODUCT_ENTRY_SUB)
                        .build(),
                ProductImage.builder()
                        .imageOrder(0)
                        .imageUrl("https://image4")
                        .imageType(ImageType.PRODUCT_DESCRIPTION)
                        .build(),
                ProductImage.builder()
                        .imageOrder(0)
                        .imageUrl("https://image5")
                        .imageType(ImageType.PRODUCT_DESCRIPTION)
                        .build()
        );

        //when
        product.addProductImages(productImages);

        //then
        assertEquals(5, productImages.size());
    }

    @Test
    @DisplayName("상품 이미지 추가 실패 - 상품 이미지 객체가 null인 경우 이미지 추가에 실패한다.")
    public void failureProductImagesOfProduct() throws Exception {
        //given
        Product product = Product.builder()
                .productCategoryPath("001001001")
                .name("coby 맨투맨")
                .price(100000)
                .discountRate(99)
                .useOption(false)
                .optionDepth(0)
                .quantity(1000)
                .soldOut(false)
                .deleted(false)
                .build();

        List<ProductImage> productImages = null;

        //when
        ProductException productException = assertThrows(ProductException.class,
                () -> product.addProductImages(productImages));

        //then
        assertEquals(ProductError.PRODUCT_IMAGES_REQUIRE, productException.getServiceError());
    }
}
