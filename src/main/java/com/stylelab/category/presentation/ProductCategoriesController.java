package com.stylelab.category.presentation;

import com.stylelab.category.application.ProductCategoriesFacade;
import com.stylelab.category.presentation.response.ProductCategoriesResponse;
import com.stylelab.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class ProductCategoriesController {

    private final ProductCategoriesFacade productCategoriesFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<ProductCategoriesResponse>> findAll() {
        return ResponseEntity.ok(ApiResponse.createApiResponse(productCategoriesFacade.findAll()));
    }
}
