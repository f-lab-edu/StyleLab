package com.stylelab.common.configuration;

import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.service.LoadUserByUsernameStrategy;
import com.stylelab.common.security.service.LoadUserByUsernameStrategyMap;
import com.stylelab.common.security.service.StoreLoadUserByUsernameStrategy;
import com.stylelab.common.security.service.UsersLoadUserByUsernameStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UsersDstailsServiceConfiguration {

    private final UsersLoadUserByUsernameStrategy usersLoadUserByUsernameStrategy;
    private final StoreLoadUserByUsernameStrategy storeLoadUserByUsernameStrategy;

    @Bean
    public LoadUserByUsernameStrategyMap loadUserByUsernameStrategyMap() {
        Map<UserType, LoadUserByUsernameStrategy> loadUserByUsernameStrategyMap = Map.of(
                UserType.USER, usersLoadUserByUsernameStrategy,
                UserType.STORE, storeLoadUserByUsernameStrategy
        );

        return LoadUserByUsernameStrategyMap.builder()
                .loadUserByUsernameStrategyMap(loadUserByUsernameStrategyMap)
                .build();
    }
}
