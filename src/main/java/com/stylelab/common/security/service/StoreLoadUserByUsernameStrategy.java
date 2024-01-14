package com.stylelab.common.security.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.repository.StoreStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class StoreLoadUserByUsernameStrategy implements LoadUserByUsernameStrategy {

    private final StoreStaffRepository storeStaffRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StoreStaff storeStaff = storeStaffRepository.findByEmail(username)
                .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()));
        Store store = storeStaff.getStore();

        return StorePrincipal.create(store, storeStaff);
    }
}
