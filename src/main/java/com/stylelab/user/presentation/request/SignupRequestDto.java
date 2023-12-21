package com.stylelab.user.presentation.request;

import com.stylelab.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String role;

    public static Users toEntity(SignupRequestDto signupRequestDto) {
        return Users.builder()
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .phoneNumber(signupRequestDto.getPhoneNumber())
                .role("ROLE_USER")
                .build();
    }
}
