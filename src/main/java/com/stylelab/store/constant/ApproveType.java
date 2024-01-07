package com.stylelab.store.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApproveType {

    APPROVE("approve"),
    REJECT("reject");

    private final String description;
}
