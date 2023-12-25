package com.stylelab.common.exception;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceErrorHandlerMap {

    private Map<String, ErrorHandler> errorHandlerMap;
}
