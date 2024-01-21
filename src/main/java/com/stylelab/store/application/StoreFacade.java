package com.stylelab.store.application;

import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.jwt.JwtTokenProvider;
import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.common.security.service.StoreLoadUserByUsernameStrategy;
import com.stylelab.file.application.FileFacade;
import com.stylelab.file.constant.ImageType;
import com.stylelab.product.application.StoreProductFacade;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import com.stylelab.store.presentation.request.CreateStoreProductRequest;
import com.stylelab.store.presentation.request.SignInRequest;
import com.stylelab.store.presentation.response.CreateStoreProductResponse;
import com.stylelab.store.presentation.response.ImageUploadResponse;
import com.stylelab.store.presentation.response.SignInResponse;
import com.stylelab.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreFacade {

    private final FileFacade fileFacade;
    private final StoreProductFacade storeProductFacade;
    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;
    private final StoreLoadUserByUsernameStrategy storeLoadUserByUsernameStrategy;
    private final JwtTokenProvider jwtTokenProvider;
    
    public void applyStore(final ApplyStoreRequest applyStoreRequest) {
        ApplyStoreRequest.StoreRequest storeRequest = applyStoreRequest.store();
        ApplyStoreRequest.StoreStaffRequest storeStaffRequest = applyStoreRequest.storeStaff();
        if (!Objects.equals(storeStaffRequest.password(), storeStaffRequest.confirmPassword())) {
            throw new StoreException(StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT);
        }

        Store store = ApplyStoreRequest.StoreRequest.toEntity(storeRequest);
        String encodePassword = passwordEncoder.encode(storeStaffRequest.password());
        StoreStaff storeStaff = ApplyStoreRequest.StoreStaffRequest.toEntity(storeStaffRequest, encodePassword);

        store.addStoreStaff(storeStaff);
        storeService.applyStore(store);
    }

    public SignInResponse signIn(final SignInRequest signInRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(signInRequest.email(), signInRequest.password());
        StorePrincipal storePrincipal = (StorePrincipal) storeLoadUserByUsernameStrategy.loadUserByUsername((String) authenticationRequest.getPrincipal());
        return SignInResponse.createResponse(jwtTokenProvider.createAuthToken(storePrincipal.getEmail(), storePrincipal.getStoreStaffRole().name(), UserType.STORE));
    }

    public ImageUploadResponse uploadMultipartFiles(
            StorePrincipal storePrincipal, final Long storeId, final ImageType imageType, final List<MultipartFile> multipartFiles) {
        validationStoreId(storePrincipal.getStore().getStoreId(), storeId);

        return ImageUploadResponse.createResponse(fileFacade.uploadMultipartFiles(imageType, multipartFiles));
    }

    public CreateStoreProductResponse createStoreProduct(
            StorePrincipal storePrincipal, final Long storeId, final CreateStoreProductRequest createStoreProductRequest) {
        validationStoreId(storePrincipal.getStore().getStoreId(), storeId);

        return CreateStoreProductResponse.createResponse(storeProductFacade.createStoreProduct(storeId, CreateStoreProductRequest.createStoreProductRequestVo(createStoreProductRequest)));
    }

    private void validationStoreId(Long storePrincipalId, Long requestStoreId) {
        if (!Objects.equals(storePrincipalId, requestStoreId)) {
            throw new StoreException(StoreError.FORBIDDEN_STORE);
        }
    }
}
