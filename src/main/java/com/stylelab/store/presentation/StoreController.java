package com.stylelab.store.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.file.constant.ImageType;
import com.stylelab.file.exception.FileError;
import com.stylelab.store.application.StoreFacade;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import com.stylelab.store.presentation.request.CreateStoreProductRequest;
import com.stylelab.store.presentation.request.SignInRequest;
import com.stylelab.store.presentation.response.CreateStoreProductResponse;
import com.stylelab.store.presentation.response.ImageUploadResponse;
import com.stylelab.store.presentation.response.SignInResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreFacade storeFacade;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Void>> applyStore(@RequestBody @Valid final ApplyStoreRequest applyStoreRequest) {
        storeFacade.applyStore(applyStoreRequest);
        return new ResponseEntity<>(ApiResponse.createEmptyApiResponse(), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SignInResponse>> sigIn(@RequestBody @Valid final SignInRequest signInRequest) {
        return ResponseEntity.ok(ApiResponse.createApiResponse(storeFacade.signIn(signInRequest)));
    }

    @PostMapping("/{storeId}/products/images/{imageType}")
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadMultipartFiles(
            @AuthenticationPrincipal StorePrincipal storePrincipal,
            @NotNull(message = "STORE_ID_REQUIRE", payload = StoreError.class)
            @PathVariable(name = "storeId") final Long storeId,
            @NotNull(message = "IMAGE_TYPE_REQUIRE", payload = FileError.class)
            @PathVariable(name = "imageType") final ImageType imageType,
            @RequestPart(name = "files", required = false) final List<MultipartFile> multipartFiles) {
        return new ResponseEntity<>(
                ApiResponse.createApiResponse(storeFacade.uploadMultipartFiles(storePrincipal, storeId, imageType, multipartFiles)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{storeId}/products")
    public ResponseEntity<ApiResponse<CreateStoreProductResponse>> createStoreProduct(
            @AuthenticationPrincipal StorePrincipal storePrincipal,
            @NotNull(message = "STORE_ID_REQUIRE", payload = StoreError.class)
            @PathVariable(name = "storeId") final Long storeId,
            @RequestBody final CreateStoreProductRequest createStoreProductRequest) {
        return new ResponseEntity<>(
                ApiResponse.createApiResponse(storeFacade.createStoreProduct(storePrincipal, storeId, createStoreProductRequest)),
                HttpStatus.CREATED
        );
    }
}
