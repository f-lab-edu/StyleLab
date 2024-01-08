package com.stylelab.common.security.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserType {

    USER,
    STORE;

    public static UserType of(String type) {
        return Arrays.stream(UserType.values())
                .filter(userType -> userType.name().equalsIgnoreCase(type))
                .findAny()
                .orElse(null);
    }
}
