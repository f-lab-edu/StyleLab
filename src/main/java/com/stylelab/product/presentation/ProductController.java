package com.stylelab.product.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.common.dto.PagingResponse;
import com.stylelab.product.application.ProductFacade;
import com.stylelab.product.presentation.response.ProductCollectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<ProductCollectionResponse>>> findByProductByConditions(
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "productCategoryPath", required = false) String productCategoryPath,
            @RequestParam(name = "price1", required = false) Integer price1,
            @RequestParam(name = "price2", required = false) Integer price2,
            @RequestParam(name = "discountRate", required = false) Integer discountRate,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.createApiResponse(productFacade.findByProductByConditions(productName, productCategoryPath, price1, price2, discountRate, pageable)));
    }
}
