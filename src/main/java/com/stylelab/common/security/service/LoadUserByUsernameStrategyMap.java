package com.stylelab.common.security.service;

import com.stylelab.common.security.constant.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadUserByUsernameStrategyMap {

    private Map<UserType, LoadUserByUsernameStrategy> loadUserByUsernameStrategyMap;
}
