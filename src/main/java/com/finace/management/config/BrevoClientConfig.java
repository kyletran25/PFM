package com.finace.management.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrevoClientConfig {
    @Value("${brevo.secret-key}")
    private String brevoKey;

    @Bean
    public RequestInterceptor brevoAuthInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("api-key", brevoKey);
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }
}
