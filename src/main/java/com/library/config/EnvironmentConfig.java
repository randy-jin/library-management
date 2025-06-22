package com.library.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class EnvironmentConfig {

    @Value("${APP_ENV:dev}")
    private String appEnvironment;

    @PostConstruct
    public void logEnvironment() {
//        System.out.println("ENVIRONMENT CHECK: Application is running in " + appEnvironment + " environment");
        log.info("Application is running in {} environment", appEnvironment);
    }

    public String getAppEnvironment() {
        return appEnvironment;
    }

    // development environment configuration
    @Configuration
    @Profile("dev")
    static class DevelopmentConfig {

        @Bean
        public String databaseConfig() {
            log.info("Configuring H2 in-memory database for development environment");
            return "Using H2 in-memory database for development";
        }

        @Bean
        public boolean enableDebugLogging() {
            return true;
        }
    }

    // development environment configuration
    @Configuration
    @Profile("test")
    static class TestingConfig {

        @Bean
        public String databaseConfig() {
            log.info("Configuring H2 in-memory database for testing environment");
            return "Using H2 in-memory database for testing";
        }

        @Bean
        public boolean enableDebugLogging() {
            return true;
        }
    }

    // production environment configuration
    @Configuration
    @Profile("prod")
    static class ProductionConfig {

        @Bean
        public String databaseConfig() {
            log.info("Configuring MySQL database for production environment");
            return "Using MySQL database for production";
        }

        @Bean
        public boolean enableDebugLogging() {
            return false;
        }
    }
}