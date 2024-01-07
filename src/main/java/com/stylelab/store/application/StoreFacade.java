package com.stylelab.store.application;

import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import com.stylelab.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreFacade {

    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;

    public void applyStore(ApplyStoreRequest applyStoreRequest) {
        ApplyStoreRequest.StoreRequest storeRequest = applyStoreRequest.store();
        ApplyStoreRequest.StoreStaffRequest storeStaffRequest = applyStoreRequest.storeStaff();
        if (!Objects.equals(storeStaffRequest.password(), storeStaffRequest.confirmPassword())) {
            throw new StoreException(StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT, StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage());
        }

        Store store = ApplyStoreRequest.StoreRequest.toEntity(storeRequest);
        String encodePassword = passwordEncoder.encode(storeStaffRequest.password());
        StoreStaff storeStaff = ApplyStoreRequest.StoreStaffRequest.toEntity(storeStaffRequest, encodePassword);

        store.addStoreStaff(storeStaff);
        storeService.applyStore(store);
    }
}
