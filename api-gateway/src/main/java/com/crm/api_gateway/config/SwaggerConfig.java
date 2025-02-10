package com.crm.api_gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi apiGatewayOpenApi() {
        return GroupedOpenApi.builder()
                .group("API Gateway")
                .pathsToMatch("/api/**")
                .build();
    }
}
