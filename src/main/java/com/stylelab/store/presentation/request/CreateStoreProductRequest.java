package com.stylelab.store.presentation.request;

import com.stylelab.product.vo.CreateStoreProductRequestVo;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CreateStoreProductRequest(
    CreateStoreProductRequest.ProductRequest productRequest,
    EntryMain entryMain,
    List<EntrySub> entrySubs,
    List<Description> descriptions,
    List<CreateStoreProductRequest.ProductOption1sRequest> productOption1sRequests
) {

    @Builder
    public record ProductRequest(
            Long storeId,
            String productCategoryPath,
            String name,
            int price,
            int discountRate,
            boolean useOption,
            int optionDepth,
            String option1,
            String option2,
            int quantity
    ) {

        public static CreateStoreProductRequestVo.ProductRequest createProductRequestVo(ProductRequest productRequest) {
            return new CreateStoreProductRequestVo.ProductRequest(
                    productRequest.storeId,
                    productRequest.productCategoryPath,
                    productRequest.name,
                    productRequest.price,
                    productRequest.discountRate,
                    productRequest.useOption,
                    productRequest.optionDepth,
                    productRequest.option1,
                    productRequest.option2,
                    productRequest.quantity
            );
        }

        @Override
        public String toString() {
            return """
                    ProductRequest {
                        storeId= %s,
                        productCategoryPath= %s,
                        name= %s,
                        price= %s,
                        discountRate= %s,
                        useOption= %s,
                        optionDepth= %s,
                        option1= %s,
                        option2= %s,
                        quantity= %s
                    }
                    """.formatted(storeId, productCategoryPath, name, productCategoryPath, discountRate, useOption, optionDepth, option1, option2, quantity);
        }
    }

    @Builder
    public record EntryMain(
            @NotBlank
            String entryMain
    ) {

        public static CreateStoreProductRequestVo.EntryMain createEntryMainVo(EntryMain entryMain) {
            return new CreateStoreProductRequestVo.EntryMain(entryMain.entryMain);
        }

        @Override
        public String toString() {
            return """
                    EntryMain {
                        entryMain= %s
                    }
                    """.formatted(entryMain);
        }
    }

    @Builder
    public record EntrySub(
            @NotBlank
            String entrySub
    ) {

        public static CreateStoreProductRequestVo.EntrySub createEntrySubVo(EntrySub entrySub) {
            return new CreateStoreProductRequestVo.EntrySub(entrySub.entrySub);
        }

        @Override
        public String toString() {
            return """
                    EntrySub {
                        entrySub= %s
                    }
                    """.formatted(entrySub);
        }
    }

    @Builder
    public record Description(
            @NotBlank
            String description
    ) {

        public static CreateStoreProductRequestVo.Description createDescriptionVo(Description description) {
            return new CreateStoreProductRequestVo.Description(description.description);
        }

        @Override
        public String toString() {
            return """
                    Description {
                        description= %s
                    }
                    """.formatted(description);
        }
    }

    @Builder
    public record ProductOption1sRequest(
            String option1Name,

            int quantity,

            int additionalPrice,

            List<ProductOption2sRequest> productOption2sRequests
    ) {

        public static CreateStoreProductRequestVo.ProductOption1sRequest createProductOption1sRequestVo(ProductOption1sRequest productOption1sRequest) {
            return new CreateStoreProductRequestVo.ProductOption1sRequest(
                    productOption1sRequest.option1Name,
                    productOption1sRequest.quantity,
                    productOption1sRequest.additionalPrice(),
                    !ObjectUtils.isEmpty(productOption1sRequest.productOption2sRequests) ?
                            productOption1sRequest.productOption2sRequests.stream()
                                    .map(ProductOption2sRequest::createProductOption2sRequestVo)
                                    .collect(Collectors.toList())
                            : null
            );
        }

        @Override
        public String toString() {
            return """
                    ProductOption1sRequest {
                        option1Name= %s,
                        quantity= %s,
                        additionalPrice= %s,
                        productOption2sRequests= %s
                    }
                    """.formatted(option1Name, quantity, additionalPrice, productOption2sRequests);
        }
    }

    @Builder
    public record ProductOption2sRequest(
            String option2Name,

            int quantity,

            int additionalPrice
    ) {

        public static CreateStoreProductRequestVo.ProductOption2sRequest createProductOption2sRequestVo(ProductOption2sRequest productOption2sRequest) {
            return new CreateStoreProductRequestVo.ProductOption2sRequest(
                    productOption2sRequest.option2Name(),
                    productOption2sRequest.quantity(),
                    productOption2sRequest.additionalPrice
            );
        }

        @Override
        public String toString() {
            return """
                    ProductOption2sRequest {
                        option2Name= %s,
                        quantity= %s,
                        additionalPrice= %s
                    }
                    """.formatted(option2Name, quantity, additionalPrice);
        }
    }

    public static CreateStoreProductRequestVo createStoreProductRequestVo(CreateStoreProductRequest createStoreProductRequest) {

        return new CreateStoreProductRequestVo(
                CreateStoreProductRequest.ProductRequest.createProductRequestVo(createStoreProductRequest.productRequest),
                CreateStoreProductRequest.EntryMain.createEntryMainVo(createStoreProductRequest.entryMain),
                createStoreProductRequest.entrySubs.stream()
                        .map(CreateStoreProductRequest.EntrySub::createEntrySubVo)
                        .collect(Collectors.toList()),
                createStoreProductRequest.descriptions.stream()
                        .map(CreateStoreProductRequest.Description::createDescriptionVo)
                        .collect(Collectors.toList()),
                !ObjectUtils.isEmpty(createStoreProductRequest.productOption1sRequests) ?
                        createStoreProductRequest.productOption1sRequests.stream()
                                .map(CreateStoreProductRequest.ProductOption1sRequest::createProductOption1sRequestVo)
                                .collect(Collectors.toList())
                        : null
        );
    }

    @Override
    public String toString() {
        return """
                    CreateStoreProductRequest {
                        productRequest= %s,
                        entryMain= %s,
                        entrySubs= %s,
                        descriptions= %s,
                        productOption1sRequests= %s
                    }
                    """.formatted(productRequest, entryMain, entrySubs, descriptions, productOption1sRequests);
    }
}
