package com.stylelab.store.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStaffRole {

    STORE_OWNER("STORE_OWNER"),
    STORE_STAFF("STORE_STAFF");

    private final String role;
}
