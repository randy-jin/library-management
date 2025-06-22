package com.library.controller;

import com.library.config.EnvironmentConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class HealthController {

    private final EnvironmentConfig environmentConfig;

    public HealthController(EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        String env = environmentConfig.getAppEnvironment();
        Map<String, String> response = Map.of(
                "status", "UP",
                "environment", env
        );
        log.info("Health check: {}", response);
        return response;
    }
}
