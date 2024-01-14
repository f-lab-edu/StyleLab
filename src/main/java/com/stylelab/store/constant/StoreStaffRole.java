package com.stylelab.store.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStaffRole {

    ROLE_STORE_OWNER("STORE_OWNER"),
    ROLE_STORE_STAFF("STORE_STAFF");

    private final String role;
}
