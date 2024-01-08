package com.stylelab.common.configuration;

import com.stylelab.common.exception.ErrorHandler;
import com.stylelab.common.exception.ServiceErrorHandlerMap;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.service.LoadUserByUsernameStrategy;
import com.stylelab.common.security.service.LoadUserByUsernameStrategyMap;
import com.stylelab.common.security.service.StoreLoadUserByUsernameStrategy;
import com.stylelab.common.security.service.UsersLoadUserByUsernameStrategy;
import com.stylelab.store.exception.StoreError;
import com.stylelab.user.exception.UsersError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.stylelab.common.exception.ErrorHandler.STORE_ERROR;
import static com.stylelab.common.exception.ErrorHandler.USERS_ERROR;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UsersLoadUserByUsernameStrategy usersLoadUserByUsernameStrategy;
    private final StoreLoadUserByUsernameStrategy storeLoadUserByUsernameStrategy;

    @Bean
    public ServiceErrorHandlerMap serviceErrorMap() {
        Map<String, ErrorHandler> errorHandlerMap = Map.of(
                UsersError.class.getName(), USERS_ERROR,
                StoreError.class.getName(), STORE_ERROR
        );

        return ServiceErrorHandlerMap.builder()
                .errorHandlerMap(errorHandlerMap)
                .build();
    }

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
