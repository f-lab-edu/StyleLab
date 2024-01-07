package com.stylelab.store.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.store.application.StoreFacade;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreFacade storeFacade;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Void>> applyStore(@RequestBody @Validated final ApplyStoreRequest applyStoreRequest) {
        storeFacade.applyStore(applyStoreRequest);
        return new ResponseEntity<>(ApiResponse.createEmptyApiResponse(), HttpStatus.CREATED);
    }
}
