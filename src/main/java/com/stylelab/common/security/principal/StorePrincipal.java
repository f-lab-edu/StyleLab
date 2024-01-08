package com.stylelab.common.security.principal;

import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
public class StorePrincipal implements UserDetails {

    private Store store;
    private final String email;
    private final String password;
    private final StoreStaffRole storeStaffRole;
    private final Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static StorePrincipal create(Store store, StoreStaff storeStaff) {
        return new StorePrincipal(
                Store.createStore(store.getStoreId()),
                storeStaff.getEmail(),
                storeStaff.getPassword(),
                storeStaff.getStoreStaffRole(),
                Collections.singletonList(new SimpleGrantedAuthority(storeStaff.getStoreStaffRole().name()))
        );
    }
}
