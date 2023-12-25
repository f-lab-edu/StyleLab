package com.stylelab.common.configuration;

import com.stylelab.common.exception.ErrorHandler;
import com.stylelab.common.exception.ServiceErrorHandlerMap;
import com.stylelab.user.exception.UsersError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.stylelab.common.exception.ErrorHandler.*;

@Configuration
public class BeanConfiguration {

    @Bean
    public ServiceErrorHandlerMap serviceErrorMap() {
        Map<String, ErrorHandler> errorHandlerMap = Map.of(
                UsersError.class.getName(), USERS_ERROR
        );

        return ServiceErrorHandlerMap.builder()
                .errorHandlerMap(errorHandlerMap)
                .build();
    }
}
