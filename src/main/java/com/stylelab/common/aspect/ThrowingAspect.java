package com.stylelab.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class ThrowingAspect {

    private final static String HOST_HEADER = "host";
    private final static String DEFAULT_HOST = "Unknown Host";
    private final static String NO_PARAMETERS = "No parameters";

    @AfterThrowing(value = "execution(* com.stylelab..presentation..*(..))", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String queryString = httpServletRequest.getQueryString();
        String requestURI =
                String.format("%s%s", httpServletRequest.getRequestURI(), StringUtils.hasText(queryString) ? "?" + queryString : "");
        Map<String, ArrayList<String>> headersMap = Collections.list(httpServletRequest.getHeaderNames()).stream()
                .collect(Collectors.toMap(Function.identity(), header -> Collections.list(httpServletRequest.getHeaders(header))));

        String host = headersMap.containsKey(HOST_HEADER) ? headersMap.get(HOST_HEADER).get(0) : DEFAULT_HOST;
        log.error("request url: {}{}", host, requestURI);
        log.error("[where the exception occurred]: {}.{}", exception.getStackTrace()[0].getClassName(), exception.getStackTrace()[0].getMethodName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.error("[parameter]: {}", arg);
        }
    }
}