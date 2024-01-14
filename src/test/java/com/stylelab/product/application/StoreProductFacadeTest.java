package com.stylelab.product.application;

import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.service.StoreProductService;
import com.stylelab.product.vo.CreateStoreProductRequestVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StoreProductFacadeTest {

    @Mock
    private StoreProductService storeProductService;

    @InjectMocks
    private StoreProductFacade storeProductFacade;

    @Nested
    @DisplayName("상품 등록 테스트")
    public class CreateStoreProductTest {

        @Test
        @DisplayName("상품 등록 실패 - 스토어 ID가 null 인 경우 ProductException(STORE_ID_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_01() throws Exception {
            //given
            Long storeId = null;

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, null));

            //then
            assertEquals(ProductError.STORE_ID_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 main 이미지가 null이면 ProductException(PRODUCT_ENTRY_MAIN_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_02() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_ENTRY_MAIN_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 main 이미지가 null이 아니고 이미지 url이 blank이면 ProductException(PRODUCT_ENTRY_MAIN_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_03() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("       ")
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_ENTRY_MAIN_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 sub 이미지가 null이면 ProductException(PRODUCT_ENTRY_SUB_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_04() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_ENTRY_SUB_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 sub 이미지가 null이 아니고 리스트의 사이즈가 0이면 ProductException(PRODUCT_ENTRY_SUB_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_05() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(Collections.emptyList())
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_ENTRY_SUB_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 sub 이미지 리스트의 사이즈가 5개보다 많으면 ProductException(EXCEED_MAX_IMAGE_COUNT)이 발생한다.")
        public void failureCreateStoreProduct_06() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image6").build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.EXCEED_MAX_IMAGE_COUNT, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 sub 이미지 중 한 개라도 이미지 url이 blank 이면 ProductException(PRODUCT_ENTRY_SUB_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_07() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("    ").build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_ENTRY_SUB_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 description 이미지가 null이면 ProductException(PRODUCT_DESCRIPTION_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_08() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_DESCRIPTION_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 description 이미지가 null이 아니고 리스트의 사이즈가 0이면 ProductException(PRODUCT_DESCRIPTION_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_09() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(Collections.emptyList())
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_DESCRIPTION_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 description 이미지 리스트의 사이즈가 10개보다 많으면 ProductException(EXCEED_MAX_IMAGE_COUNT)이 발생한다.")
        public void failureCreateStoreProduct_10() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image11").build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.EXCEED_MAX_IMAGE_COUNT, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 description 이미지 중 한 개라도 이미지 url이 blank 이면 ProductException(PRODUCT_DESCRIPTION_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_11() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("  ").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_DESCRIPTION_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 요청 객체가 null인 경우 ProductException(PRODUCT_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_12() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .build();

            // when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            // then
            assertEquals(ProductError.PRODUCT_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 카테고리가 blank인 경우 ProductException(PRODUCT_CATEGORY_PATH_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_13() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("   ")
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_CATEGORY_PATH_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 이름이 blank인 경우 ProductException(PRODUCT_CATEGORY_PATH_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_14() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_NAME_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 가격이 3,000원 보다 작은 경우 ProductException(PRODUCT_PRICE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_15() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(2999)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_PRICE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 가격이 10억 보다 큰 경우 ProductException(PRODUCT_PRICE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_16() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_001)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_PRICE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 할인율이 0 보다 작은 경우 ProductException(PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_17() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(3000)
                                    .discountRate(-1)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 할인율이 100 보다 큰 경우 ProductException(PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_18() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(101)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하지 않는 경우 상품 테이블의 수량 0 보다 작은 경우 ProductException(PRODUCT_QUANTITY_LESS_THEN_ZERO)이 발생한다.")
        public void failureCreateStoreProduct_19() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(false)
                                    .quantity(-1)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_QUANTITY_LESS_THEN_ZERO, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하는 경우 상품 테이블의 optionDepth가 0 보다 작은 경우 ProductException(PRODUCT_OPTION_DEPTH_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_20() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(-1)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_OPTION_DEPTH_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하는 경우 상품 테이블의 optionDepth가 2 보다 큰 경우 ProductException(PRODUCT_OPTION_DEPTH_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_21() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(3)
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_OPTION_DEPTH_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 1인 경우  productRequest의 option1이  null or empty인 경우 ProductException(PRODUCT_OPTION1_REQUEST_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_32() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(1)
                                    .option1("사이즈")
                                    .build()
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 1인 경우  ProductOption1sRequests가 null or empty인 경우 ProductException(PRODUCT_OPTION1_REQUEST)이 발생한다.")
        public void failureCreateStoreProduct_22() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(1)
                                    .option1("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("   ")
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION1_NAME_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 1인 경우  ProductOption1sRequest의 optionName이 blank인 경우 ProductException(OPTION1_NAME_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_23() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(1)
                                    .option1("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("   ")
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION1_NAME_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 1인 경우  ProductOption1sRequest의 additionalPrice가 0 보다 작은 경우 ProductException(OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_24() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(1)
                                    .option1("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(-1000)
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 1인 경우  ProductOption1sRequest의 additionalPrice가 1억 보다 큰 경우 ProductException(OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_25() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(1)
                                    .option1("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(100_000_100)
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 1인 경우  ProductOption1sRequest의 quantity가 0 보다 작은 경우 ProductException(OPTION1_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO)이 발생한다.")
        public void failureCreateStoreProduct_26() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(1)
                                    .option1("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(-11)
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION1_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 2인 경우  productRequest의 option2가 null or empty인 경우 ProductException(PRODUCT_OPTION2_REQUEST)이 발생한다.")
        public void failureCreateStoreProduct_33() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(2)
                                    .option1("색상")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(0)
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_OPTION2_NAME_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 2인 경우  ProductOption2sRequests가 null or empty인 경우 ProductException(PRODUCT_OPTION2_REQUEST)이 발생한다.")
        public void failureCreateStoreProduct_27() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(2)
                                    .option1("색상")
                                    .option2("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(0)
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.PRODUCT_OPTION2_REQUEST_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 2인 경우  ProductOption2sRequest의 optionName이 blank인 경우 ProductException(OPTION2_NAME_REQUIRE)이 발생한다.")
        public void failureCreateStoreProduct_28() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(2)
                                    .option1("색상")
                                    .option2("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(0)
                                            .productOption2sRequest(
                                                    Collections.singletonList(
                                                            CreateStoreProductRequestVo.ProductOption2sRequest.builder()
                                                                    .option2Name("  ")
                                                                    .build()
                                                    )
                                            )
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION2_NAME_REQUIRE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 2인 경우  ProductOption2sRequest의 additionalPrice가 0 보다 작은 경우 ProductException(OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_29() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(2)
                                    .option1("색상")
                                    .option2("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(0)
                                            .productOption2sRequest(
                                                    Collections.singletonList(
                                                            CreateStoreProductRequestVo.ProductOption2sRequest.builder()
                                                                    .option2Name("XL")
                                                                    .additionalPrice(-3000)
                                                                    .build()
                                                    )
                                            )
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 2인 경우  ProductOption2sRequest의 additionalPrice가 1억 보다 큰 경우 ProductException(OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE)이 발생한다.")
        public void failureCreateStoreProduct_30() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(2)
                                    .option1("색상")
                                    .option2("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(0)
                                            .productOption2sRequest(
                                                    Collections.singletonList(
                                                            CreateStoreProductRequestVo.ProductOption2sRequest.builder()
                                                                    .option2Name("XL")
                                                                    .additionalPrice(100_000_100)
                                                                    .build()
                                                    )
                                            )
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE, productException.getServiceError());
        }

        @Test
        @DisplayName("상품 등록 실패 - 상품 옵션을 사용하고 optionDepth가 2인 경우  ProductOption2sRequest의 quantity가 0 보다 작은 경우 ProductException(OPTION2_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO)이 발생한다.")
        public void failureCreateStoreProduct_31() throws Exception {
            //given
            Long storeId = 1L;
            CreateStoreProductRequestVo createStoreProductRequestVo = CreateStoreProductRequestVo.builder()
                    .entryMain(
                            CreateStoreProductRequestVo.EntryMain.builder()
                                    .entryMain("https://image1")
                                    .build()
                    )
                    .entrySubs(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image1").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image2").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image3").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image4").build(),
                                    CreateStoreProductRequestVo.EntrySub.builder().entrySub("https://image5").build()
                            )
                    )
                    .descriptions(
                            Arrays.asList(
                                    CreateStoreProductRequestVo.Description.builder().description("https://image1").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image2").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image3").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image4").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image5").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image6").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image7").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image8").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image9").build(),
                                    CreateStoreProductRequestVo.Description.builder().description("https://image10").build()
                            )
                    )
                    .productRequest(
                            CreateStoreProductRequestVo.ProductRequest.builder()
                                    .productCategoryPath("001001001")
                                    .name("coby 맨투맨")
                                    .price(1_000_000_000)
                                    .discountRate(100)
                                    .useOption(true)
                                    .quantity(0)
                                    .optionDepth(2)
                                    .option1("색상")
                                    .option2("사이즈")
                                    .build()
                    )
                    .productOption1SRequest(
                            Collections.singletonList(
                                    CreateStoreProductRequestVo.ProductOption1sRequest.builder()
                                            .option1Name("화이트")
                                            .additionalPrice(30000)
                                            .quantity(0)
                                            .productOption2sRequest(
                                                    Collections.singletonList(
                                                            CreateStoreProductRequestVo.ProductOption2sRequest.builder()
                                                                    .option2Name("XL")
                                                                    .additionalPrice(10000)
                                                                    .quantity(-3)
                                                                    .build()
                                                    )
                                            )
                                            .build()
                            )
                    )
                    .build();

            //when
            ProductException productException = assertThrows(ProductException.class,
                    () -> storeProductFacade.createStoreProduct(storeId, createStoreProductRequestVo));

            //then
            assertEquals(ProductError.OPTION2_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO, productException.getServiceError());
        }
    }
}
