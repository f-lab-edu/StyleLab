package com.stylelab.product.vo;

import com.stylelab.file.constant.ImageType;
import com.stylelab.product.domain.Product;
import com.stylelab.product.domain.ProductImage;
import com.stylelab.product.domain.ProductOption1;
import com.stylelab.product.domain.ProductOption2;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateStoreProductRequestVo(
    CreateStoreProductRequestVo.ProductRequest productRequest,
    EntryMain entryMain,
    List<EntrySub> entrySubs,
    List<Description> descriptions,
    List<ProductOption1sRequest> productOption1SRequest
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

        public static Product createProduct(ProductRequest productRequest) {
            return Product.builder()
                    .productCategoryPath(productRequest.productCategoryPath)
                    .name(productRequest.name)
                    .price(productRequest.price)
                    .discountRate(productRequest.discountRate)
                    .useOption(productRequest.useOption)
                    .optionDepth(productRequest.optionDepth)
                    .option1(productRequest.option1)
                    .option2(productRequest.option2)
                    .quantity(productRequest.quantity)
                    .soldOut(false)
                    .deleted(false)
                    .build();
        }
    }

    @Builder
    public record EntryMain(
            String entryMain
    ) {

        public static ProductImage createEntryMainProductImage(EntryMain entryMain) {
            return ProductImage.builder()
                    .imageUrl(entryMain.entryMain)
                    .imageType(ImageType.PRODUCT_ENTRY_MAIN)
                    .imageOrder(0)
                    .build();
        }
    }

    @Builder
    public record EntrySub(
            String entrySub
    ) {
        public static ProductImage createEntrySubProductImage(EntrySub entrySub) {
            return ProductImage.builder()
                    .imageUrl(entrySub.entrySub)
                    .imageType(ImageType.PRODUCT_ENTRY_SUB)
                    .imageOrder(0)
                    .build();
        }
    }

    @Builder
    public record Description(
            String description
    ) {
        public static ProductImage createDescriptionProductImage(Description description) {
            return ProductImage.builder()
                    .imageUrl(description.description)
                    .imageType(ImageType.PRODUCT_DESCRIPTION)
                    .imageOrder(0)
                    .build();
        }
    }

    @Builder
    public record ProductOption1sRequest(
            String option1Name,

            int quantity,

            int additionalPrice,

            List<ProductOption2sRequest> productOption2sRequest
    ) {

        public static ProductOption1 createProductOption1(ProductOption1sRequest productOption1sRequest) {
            return ProductOption1.builder()
                    .option1Name(productOption1sRequest.option1Name)
                    .additionalPrice(productOption1sRequest.additionalPrice)
                    .quantity(productOption1sRequest.quantity)
                    .soldOut(false)
                    .deleted(false)
                    .build();
        }
    }

    @Builder
    public record ProductOption2sRequest(
            String option2Name,

            int quantity,

            int additionalPrice
    ) {

        public static ProductOption2 createProductOption2(ProductOption2sRequest productOption2sRequest) {
            return ProductOption2.builder()
                    .option2Name(productOption2sRequest.option2Name)
                    .additionalPrice(productOption2sRequest.additionalPrice)
                    .quantity(productOption2sRequest.quantity)
                    .soldOut(false)
                    .deleted(false)
                    .build();
        }
    }

    public boolean getUseOption() {
        return this.productRequest.useOption;
    }

    public int getQuantity() {
       return this.productRequest.quantity;
    }

    public int getOptionDepth() {
        return this.productRequest.optionDepth;
    }

    public String getOption1Name() {
        return this.productRequest.option1;
    }

    public String getOption2Name() {
        return this.productRequest.option2;
    }
}
