package com.example.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("Binarfud", r-> r.path("/binarfud/**")
                        .uri("http://localhost:8081/binarfud-service")

                )
                .route("Notification", r-> r.path("/notification/**")
                        .uri("http://localhost:8082/notification-service")
                )
                .build();
    }
}
