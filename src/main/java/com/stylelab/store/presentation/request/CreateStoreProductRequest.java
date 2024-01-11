package com.stylelab.store.presentation.request;

import com.stylelab.product.exception.ProductError;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateStoreProductRequest(
    @Valid
    @NotNull(message = "PRODUCT_REQUIRE", payload = ProductError.class)
    CreateStoreProductRequest.ProductRequest productRequest,

    @Valid
    @NotNull(message = "PRODUCT_ENTRY_MAIN_REQUIRE", payload = ProductError.class)
    EntryMain entryMain,

    @Valid
    @NotNull(message = "PRODUCT_ENTRY_SUB_REQUIRE", payload = ProductError.class)
    List<EntrySub> entrySubs,

    @Valid
    @NotNull(message = "PRODUCT_DESCRIPTION_REQUIRE", payload = ProductError.class)
    List<Description> descriptions,

    List<CreateStoreProductRequest.ProductOption1sRequest> productOption1SRequest
) {

    @Builder
    public record ProductRequest(
            @NotNull
            Long storeId,

            @NotBlank
            String productCategoryPath,

            @NotBlank
            String name,

            @Min(value = 3000)
            int price,

            @Min(value = 0)
            @Max(value = 100)
            int discountRate,

            boolean useOption,

            @Min(value = 0)
            @Max(value = 2)
            int optionDepth,

            String option1,

            String option2,

            int quantity
    ) {

    }

    @Builder
    public record EntryMain(
            @NotBlank
            String entryMain) {
    }

    @Builder
    public record EntrySub(
            @NotBlank
            String entrySub
    ) {

    }

    @Builder
    public record Description(
            @NotBlank
            String description
    ) {

    }

    @Builder
    public record ProductOption1sRequest(
            String option1Name,

            int quantity,

            int additionalPrice,

            List<ProductOption2sRequest> productOption2sRequest
    ) {

    }

    @Builder
    public record ProductOption2sRequest(
            String option2Name,

            int quantity,

            int additionalPrice
    ) {

    }
}
