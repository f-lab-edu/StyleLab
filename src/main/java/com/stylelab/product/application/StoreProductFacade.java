package com.stylelab.product.application;

import com.stylelab.file.constant.ImageType;
import com.stylelab.product.domain.Product;
import com.stylelab.product.domain.ProductImage;
import com.stylelab.product.domain.ProductOption1;
import com.stylelab.product.domain.ProductOption2;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.service.StoreProductService;
import com.stylelab.product.vo.CreateStoreProductRequestVo;
import com.stylelab.product.vo.CreateStoreProductResponseVo;
import com.stylelab.store.domain.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreProductFacade {

    private final StoreProductService storeProductService;

    public CreateStoreProductResponseVo createStoreProduct(final Long storeId, final CreateStoreProductRequestVo createStoreProductRequestVo) {
        validationCreateStoreProductRequest(storeId, createStoreProductRequestVo);

        CreateStoreProductRequestVo.ProductRequest productRequest = createStoreProductRequestVo.productRequest();
        Product product = CreateStoreProductRequestVo.ProductRequest.createProduct(productRequest);
        product.calculateDiscountPrice();
        product.addStore(Store.createStore(storeId));

        List<ProductImage> productImages = new ArrayList<>();
        productImages.add(CreateStoreProductRequestVo.EntryMain.createEntryMainProductImage(createStoreProductRequestVo.entryMain()));
        List<CreateStoreProductRequestVo.EntrySub> entrySubs = createStoreProductRequestVo.entrySubs();
        for (CreateStoreProductRequestVo.EntrySub entrySub : entrySubs) {
            productImages.add(CreateStoreProductRequestVo.EntrySub.createEntrySubProductImage(entrySub));
        }
        List<CreateStoreProductRequestVo.Description> descriptions = createStoreProductRequestVo.descriptions();
        for (CreateStoreProductRequestVo.Description description : descriptions) {
            productImages.add(CreateStoreProductRequestVo.Description.createDescriptionProductImage(description));
        }
        product.addProductImages(productImages);

        boolean useOption = productRequest.useOption();
        if (useOption) {
            int optionDepth = productRequest.optionDepth();
            List<ProductOption1> productOption1s = new ArrayList<>();
            List<CreateStoreProductRequestVo.ProductOption1sRequest> productOption1sRequests = createStoreProductRequestVo.productOption1SRequest();
            for (CreateStoreProductRequestVo.ProductOption1sRequest productOption1sRequest : productOption1sRequests) {
                ProductOption1 productOption1 = CreateStoreProductRequestVo.ProductOption1sRequest.createProductOption1(productOption1sRequest);
                productOption1s.add(productOption1);

                if (optionDepth == 2) {
                    List<ProductOption2> productOption2s = new ArrayList<>();
                    List<CreateStoreProductRequestVo.ProductOption2sRequest> productOption2sRequests = productOption1sRequest.productOption2sRequest();
                    for (CreateStoreProductRequestVo.ProductOption2sRequest productOption2sRequest : productOption2sRequests) {
                        ProductOption2 productOption2 = CreateStoreProductRequestVo.ProductOption2sRequest.createProductOption2(productOption2sRequest);
                        productOption2s.add(productOption2);
                    }

                    productOption1.additionalProductOption2(productOption2s);
                }
            }

            product.additionalProductOption1(productOption1s);
        }

        return CreateStoreProductResponseVo.createResponse(storeProductService.createStoreProduct(product));
    }

    private void validationCreateStoreProductRequest(Long storeId, CreateStoreProductRequestVo createStoreProductRequestVo) {
        if (storeId == null) {
            throw new ProductException(ProductError.STORE_ID_REQUIRE);
        }

        CreateStoreProductRequestVo.EntryMain entryMain = createStoreProductRequestVo.entryMain();
        if (entryMain == null || !StringUtils.hasText(entryMain.entryMain())) {
            throw new ProductException(ProductError.PRODUCT_ENTRY_MAIN_REQUIRE);
        }

        List<CreateStoreProductRequestVo.EntrySub> entrySubs = createStoreProductRequestVo.entrySubs();
        if (ObjectUtils.isEmpty(entrySubs)) {
            throw new ProductException(ProductError.PRODUCT_ENTRY_SUB_REQUIRE);
        }
        if (entrySubs.size() > ImageType.PRODUCT_ENTRY_SUB.getMaxImageCount()) {
            throw new ProductException(
                    ProductError.EXCEED_MAX_IMAGE_COUNT,
                    String.format(ProductError.EXCEED_MAX_IMAGE_COUNT.getMessage(), ImageType.PRODUCT_ENTRY_SUB, ImageType.PRODUCT_ENTRY_SUB.getMaxImageCount())
            );
        }
        entrySubs.stream()
                .filter(entrySub -> !StringUtils.hasText(entrySub.entrySub()))
                .findAny()
                .ifPresent(entrySub -> {
                    throw new ProductException(ProductError.PRODUCT_ENTRY_SUB_REQUIRE);
                });

        List<CreateStoreProductRequestVo.Description> descriptions = createStoreProductRequestVo.descriptions();
        if (ObjectUtils.isEmpty(descriptions)) {
            throw new ProductException(ProductError.PRODUCT_DESCRIPTION_REQUIRE);
        }
        if (descriptions.size() > ImageType.PRODUCT_DESCRIPTION.getMaxImageCount()) {
            throw new ProductException(
                    ProductError.EXCEED_MAX_IMAGE_COUNT,
                    String.format(ProductError.EXCEED_MAX_IMAGE_COUNT.getMessage(), ImageType.PRODUCT_DESCRIPTION, ImageType.PRODUCT_DESCRIPTION.getMaxImageCount())
            );
        }
        descriptions.stream()
                .filter(description -> !StringUtils.hasText(description.description()))
                .findAny()
                .ifPresent(description -> {
                    throw new ProductException(ProductError.PRODUCT_DESCRIPTION_REQUIRE);
                });

        CreateStoreProductRequestVo.ProductRequest productRequest = createStoreProductRequestVo.productRequest();
        if (productRequest == null) {
            throw new ProductException(ProductError.PRODUCT_REQUIRE);
        }

        if (!StringUtils.hasText(productRequest.productCategoryPath())) {
            throw new ProductException(ProductError.PRODUCT_CATEGORY_PATH_REQUIRE);
        }

        if (!StringUtils.hasText(productRequest.name())) {
            throw new ProductException(ProductError.PRODUCT_NAME_REQUIRE);
        }

        int price = productRequest.price();
        if (price < 3_000 || price > 1_000_000_000) {
            throw new ProductException(ProductError.PRODUCT_PRICE_OUT_OF_RANGE);
        }

        int discountRate = productRequest.discountRate();
        if (discountRate < 0 || discountRate > 100) {
            throw new ProductException(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE);
        }

        boolean useOption = productRequest.useOption();
        if (!useOption) {
            int quantity = productRequest.quantity();
            if (quantity < 0) {
                throw new ProductException(ProductError.PRODUCT_QUANTITY_LESS_THEN_ZERO);
            }
        } else {
            int optionDepth = productRequest.optionDepth();

            if (optionDepth <= 0 || optionDepth > 2) {
                throw new ProductException(ProductError.PRODUCT_OPTION_DEPTH_OUT_OF_RANGE);
            }

            String option1 = productRequest.option1();
            if (!StringUtils.hasText(option1)) {
                throw new ProductException(ProductError.PRODUCT_OPTION1_NAME_REQUIRE);
            }

            if (optionDepth == 2) {
                String option2 = productRequest.option2();
                if (!StringUtils.hasText(option2)) {
                    throw new ProductException(ProductError.PRODUCT_OPTION2_NAME_REQUIRE);
                }
            }

            List<CreateStoreProductRequestVo.ProductOption1sRequest> productOption1sRequests = createStoreProductRequestVo.productOption1SRequest();
            if (ObjectUtils.isEmpty(productOption1sRequests)) {
                throw new ProductException(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE);
            }

            for (CreateStoreProductRequestVo.ProductOption1sRequest productOption1sRequest : productOption1sRequests) {
                String option1Name = productOption1sRequest.option1Name();
                if (!StringUtils.hasText(option1Name)) {
                    throw new ProductException(ProductError.OPTION1_NAME_REQUIRE);
                }

                int option1AdditionalPrice = productOption1sRequest.additionalPrice();
                if (option1AdditionalPrice < 0 || option1AdditionalPrice > 100_000_000) {
                    throw new ProductException(ProductError.OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE);
                }

                if (optionDepth == 1) {
                    int option1Quantity = productOption1sRequest.quantity();
                    if (option1Quantity < 0) {
                        throw new ProductException(ProductError.OPTION1_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO);
                    }
                } else {
                    List<CreateStoreProductRequestVo.ProductOption2sRequest> productOption2sRequests = productOption1sRequest.productOption2sRequest();
                    if (ObjectUtils.isEmpty(productOption2sRequests)) {
                        throw new ProductException(ProductError.PRODUCT_OPTION2_REQUEST_REQUIRE);
                    }

                    for (CreateStoreProductRequestVo.ProductOption2sRequest productOption2sRequest : productOption2sRequests) {
                        String option2Name = productOption2sRequest.option2Name();
                        if (!StringUtils.hasText(option2Name)) {
                            throw new ProductException(ProductError.OPTION2_NAME_REQUIRE);
                        }

                        int option2AdditionalPrice = productOption2sRequest.additionalPrice();
                        if(option2AdditionalPrice < 0 || option2AdditionalPrice > 100_000_000) {
                            throw new ProductException(ProductError.OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE);
                        }

                        int option2Quantity = productOption2sRequest.quantity();
                        if (option2Quantity < 0) {
                            throw new ProductException(ProductError.OPTION2_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO);
                        }
                    }
                }
            }
        }
    }
}
