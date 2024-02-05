package com.stylelab.healthcheck;

import com.stylelab.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/healthCheck")
public class HealthCheck {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> healthCheck() {
        log.info("L7 LB health check");
        return ResponseEntity.ok(ApiResponse.createEmptyApiResponse());
    }
}
