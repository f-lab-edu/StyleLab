package com.stylelab.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UsersRole {
    ROLE_USER("일반 회원");

    private final String role;
    
    public static UsersRole of(String role) {
        return Arrays.stream(UsersRole.values())
                .filter(usersRole -> usersRole.name().equalsIgnoreCase(role))
                .findAny()
                .orElse(null);
    }
}
