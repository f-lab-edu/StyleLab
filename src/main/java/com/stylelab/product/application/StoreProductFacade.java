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

import static com.stylelab.product.vo.CreateStoreProductRequestVo.Description;
import static com.stylelab.product.vo.CreateStoreProductRequestVo.EntryMain;
import static com.stylelab.product.vo.CreateStoreProductRequestVo.EntrySub;
import static com.stylelab.product.vo.CreateStoreProductRequestVo.ProductOption1sRequest;
import static com.stylelab.product.vo.CreateStoreProductRequestVo.ProductOption2sRequest;
import static com.stylelab.product.vo.CreateStoreProductRequestVo.ProductRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreProductFacade {

    private final StoreProductService storeProductService;

    public CreateStoreProductResponseVo createStoreProduct(final Long storeId, final CreateStoreProductRequestVo createStoreProductRequestVo) {
        validationCreateStoreProductRequest(storeId, createStoreProductRequestVo);

        Product product = createProduct(storeId, createStoreProductRequestVo.productRequest());
        product.addProductImages(getProductImages(createStoreProductRequestVo));

        boolean useOption = createStoreProductRequestVo.getUseOption();
        if (useOption) {
            product.additionalProductOption1(getProductOption1s(createStoreProductRequestVo));
        }

        return CreateStoreProductResponseVo.createResponse(storeProductService.createStoreProduct(product));
    }

    private void validationCreateStoreProductRequest(Long storeId, CreateStoreProductRequestVo createStoreProductRequestVo) {
        if (storeId == null) {
            throw new ProductException(ProductError.STORE_ID_REQUIRE);
        }
        if (createStoreProductRequestVo == null) {
            throw new ProductException(ProductError.PRODUCT_REQUIRE);
        }

        validationProductMainImages(createStoreProductRequestVo.entryMain());
        validationProductSubImages(createStoreProductRequestVo.entrySubs());
        validationProductDescriptionImages(createStoreProductRequestVo.descriptions());
        validationProductInformation(createStoreProductRequestVo.productRequest());
        validationProductOptions(createStoreProductRequestVo);
    }

    private void validationProductMainImages(EntryMain entryMain) {
        if (entryMain == null || !StringUtils.hasText(entryMain.entryMain())) {
            throw new ProductException(ProductError.PRODUCT_ENTRY_MAIN_REQUIRE);
        }
    }

    private void validationProductSubImages(List<EntrySub> entrySubs) {
        if (ObjectUtils.isEmpty(entrySubs)) {
            throw new ProductException(ProductError.PRODUCT_ENTRY_SUB_REQUIRE);
        }
        if (entrySubs.size() > ImageType.PRODUCT_ENTRY_SUB.getMaxImageCount()) {
            throw new ProductException(
                    ProductError.EXCEED_MAX_IMAGE_COUNT,
                    String.format(
                            ProductError.EXCEED_MAX_IMAGE_COUNT.getMessage(),
                            ImageType.PRODUCT_ENTRY_SUB,
                            ImageType.PRODUCT_ENTRY_SUB.getMaxImageCount()
                    )
            );
        }
        entrySubs.stream()
                .filter(entrySub -> !StringUtils.hasText(entrySub.entrySub()))
                .findAny()
                .ifPresent(entrySub -> {
                    throw new ProductException(ProductError.PRODUCT_ENTRY_SUB_REQUIRE);
                });
    }

    private void validationProductDescriptionImages(List<Description> descriptions) {
        if (ObjectUtils.isEmpty(descriptions)) {
            throw new ProductException(ProductError.PRODUCT_DESCRIPTION_REQUIRE);
        }
        if (descriptions.size() > ImageType.PRODUCT_DESCRIPTION.getMaxImageCount()) {
            throw new ProductException(
                    ProductError.EXCEED_MAX_IMAGE_COUNT,
                    String.format(
                            ProductError.EXCEED_MAX_IMAGE_COUNT.getMessage(),
                            ImageType.PRODUCT_DESCRIPTION,
                            ImageType.PRODUCT_DESCRIPTION.getMaxImageCount()
                    )
            );
        }
        descriptions.stream()
                .filter(description -> !StringUtils.hasText(description.description()))
                .findAny()
                .ifPresent(description -> {
                    throw new ProductException(ProductError.PRODUCT_DESCRIPTION_REQUIRE);
                });
    }

    private void validationProductInformation(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new ProductException(ProductError.PRODUCT_REQUIRE);
        }

        if (!StringUtils.hasText(productRequest.productCategoryPath())) {
            throw new ProductException(ProductError.PRODUCT_CATEGORY_PATH_REQUIRE);
        }

        if (!StringUtils.hasText(productRequest.name())) {
            throw new ProductException(ProductError.PRODUCT_NAME_REQUIRE);
        }

        int minimumPrice  = 3_000;
        int maximumPrice = 1_000_000_000;
        int price = productRequest.price();
        if (price < minimumPrice || price > maximumPrice) {
            throw new ProductException(ProductError.PRODUCT_PRICE_OUT_OF_RANGE);
        }

        int minimumDiscountRate = 0;
        int maximumDiscountRate = 100;
        int discountRate = productRequest.discountRate();
        if (discountRate < minimumDiscountRate || discountRate > maximumDiscountRate) {
            throw new ProductException(ProductError.PRODUCT_DISCOUNT_RATE_OUT_OF_RANGE);
        }
    }

    private void validationProductOptions(CreateStoreProductRequestVo createStoreProductRequestVo) {
        int minimumQuantity = 0;
        boolean useOption = createStoreProductRequestVo.getUseOption();
        if (!useOption) {
            int productQuantity = createStoreProductRequestVo.getQuantity();
            if (productQuantity < minimumQuantity) {
                throw new ProductException(ProductError.PRODUCT_QUANTITY_LESS_THEN_ZERO);
            }

            return;
        }

        int minimumOptionDepth = 0;
        int maximumOptionDepth = 2;
        int minimumOptionAdditionalPrice = 0;
        int maximumOptionAdditionalPrice = 100_000_000;
        int optionDepth = createStoreProductRequestVo.getOptionDepth();

        if (optionDepth <= minimumOptionDepth || optionDepth > maximumOptionDepth) {
            throw new ProductException(ProductError.PRODUCT_OPTION_DEPTH_OUT_OF_RANGE);
        }

        String option1 = createStoreProductRequestVo.getOption1Name();
        if (!StringUtils.hasText(option1)) {
            throw new ProductException(ProductError.PRODUCT_OPTION1_NAME_REQUIRE);
        }

        if (optionDepth == maximumOptionDepth) {
            String option2 = createStoreProductRequestVo.getOption2Name();
            if (!StringUtils.hasText(option2)) {
                throw new ProductException(ProductError.PRODUCT_OPTION2_NAME_REQUIRE);
            }
        }

        List<ProductOption1sRequest> productOption1sRequests = createStoreProductRequestVo.productOption1SRequest();
        validationProductOption1(optionDepth, minimumQuantity, minimumOptionAdditionalPrice, maximumOptionAdditionalPrice, productOption1sRequests);
    }

    private void validationProductOption1(
            int optionDepth, int minimumQuantity, int minimumOptionAdditionalPrice, int maximumOptionAdditionalPrice,
            List<ProductOption1sRequest> productOption1sRequests) {
        if (ObjectUtils.isEmpty(productOption1sRequests)) {
            throw new ProductException(ProductError.PRODUCT_OPTION1_REQUEST_REQUIRE);
        }

        for (ProductOption1sRequest productOption1sRequest : productOption1sRequests) {
            String option1Name = productOption1sRequest.option1Name();
            if (!StringUtils.hasText(option1Name)) {
                throw new ProductException(ProductError.OPTION1_NAME_REQUIRE);
            }

            int option1AdditionalPrice = productOption1sRequest.additionalPrice();
            if (option1AdditionalPrice < minimumOptionAdditionalPrice || option1AdditionalPrice > maximumOptionAdditionalPrice) {
                throw new ProductException(ProductError.OPTION1_ADDITIONAL_PRICE_OUT_OF_RANGE);
            }

            if (optionDepth == 1) {
                int option1Quantity = productOption1sRequest.quantity();
                if (option1Quantity < minimumQuantity) {
                    throw new ProductException(ProductError.OPTION1_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO);
                }
                return;
            }

            List<ProductOption2sRequest> productOption2sRequests = productOption1sRequest.productOption2sRequest();
            validationProductOption2(minimumQuantity, minimumOptionAdditionalPrice, maximumOptionAdditionalPrice, productOption2sRequests);
        }
    }

    private void validationProductOption2(
            int minimumQuantity, int minimumOptionAdditionalPrice, int maximumOptionAdditionalPrice,
            List<ProductOption2sRequest> productOption2sRequests) {
        if (ObjectUtils.isEmpty(productOption2sRequests)) {
            throw new ProductException(ProductError.PRODUCT_OPTION2_REQUEST_REQUIRE);
        }

        for (ProductOption2sRequest productOption2sRequest : productOption2sRequests) {
            String option2Name = productOption2sRequest.option2Name();
            if (!StringUtils.hasText(option2Name)) {
                throw new ProductException(ProductError.OPTION2_NAME_REQUIRE);
            }

            int option2AdditionalPrice = productOption2sRequest.additionalPrice();
            if(option2AdditionalPrice < minimumOptionAdditionalPrice || option2AdditionalPrice > maximumOptionAdditionalPrice) {
                throw new ProductException(ProductError.OPTION2_ADDITIONAL_PRICE_OUT_OF_RANGE);
            }

            int option2Quantity = productOption2sRequest.quantity();
            if (option2Quantity < minimumQuantity) {
                throw new ProductException(ProductError.OPTION2_EXISTS_PRODUCT_QUANTITY_GRATE_THEN_ZERO);
            }
        }
    }

    private Product createProduct(Long storeId, ProductRequest productRequest) {
        Product product = ProductRequest.createProduct(productRequest);
        product.calculateDiscountPrice();
        product.addStore(Store.createStore(storeId));

        return product;
    }

    private List<ProductImage> getProductImages(CreateStoreProductRequestVo createStoreProductRequestVo) {
        List<ProductImage> productImages = new ArrayList<>();

        productImages.add(EntryMain.createEntryMainProductImage(createStoreProductRequestVo.entryMain()));

        List<EntrySub> entrySubs = createStoreProductRequestVo.entrySubs();
        for (EntrySub entrySub : entrySubs) {
            productImages.add(EntrySub.createEntrySubProductImage(entrySub));
        }

        List<Description> descriptions = createStoreProductRequestVo.descriptions();
        for (Description description : descriptions) {
            productImages.add(Description.createDescriptionProductImage(description));
        }

        return productImages;
    }

    private List<ProductOption1> getProductOption1s(CreateStoreProductRequestVo createStoreProductRequestVo) {
        int maximumOptionDepth = 2;
        int optionDepth = createStoreProductRequestVo.getOptionDepth();
        List<ProductOption1> productOption1s = new ArrayList<>();
        List<ProductOption1sRequest> productOption1sRequests = createStoreProductRequestVo.productOption1SRequest();
        for (ProductOption1sRequest productOption1sRequest : productOption1sRequests) {
            ProductOption1 productOption1 = ProductOption1sRequest.createProductOption1(productOption1sRequest);
            productOption1s.add(productOption1);

            if (optionDepth == maximumOptionDepth) {
                productOption1.additionalProductOption2(getProductOption2s(productOption1sRequest.productOption2sRequest()));
            }
        }

        return productOption1s;
    }

    private List<ProductOption2> getProductOption2s(List<ProductOption2sRequest> productOption2sRequests) {
        List<ProductOption2> productOption2s = new ArrayList<>();
        for (ProductOption2sRequest productOption2sRequest : productOption2sRequests) {
            ProductOption2 productOption2 = ProductOption2sRequest.createProductOption2(productOption2sRequest);
            productOption2s.add(productOption2);
        }

        return productOption2s;
    }
}
