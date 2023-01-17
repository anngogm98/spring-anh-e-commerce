package com.springcloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/product/**")
                        .uri("lb://product-service/"))
                .route("randomName", r -> r.path("/**")
                        .filters(f -> f.rewritePath("/(?<randomString>.*)", "/api/product/check"))
                        .uri("lb://product-service/"))
//                .route("host_route", r -> r.host("*.myhost.org")
//                        .uri("http://httpbin.org"))
//                .route("product-service", r -> r.path("/**")
//                        .filters(f -> f.rewritePath("/demo/(?<segment>.*)", "/api/product/${segment}"))
//                        .uri("lb://product-service/"))
//                .route("hystrix_route", r -> r.host("*.hystrix.org")
//                        .filters(f -> f.hystrix(c -> c.setName("slowcmd")))
//                        .uri("http://httpbin.org"))
//                .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
//                        .filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
//                        .uri("http://httpbin.org"))
//                .route("limit_route", r -> r
//                        .host("*.limited.org").and().path("/anything/**")
//                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//                        .uri("http://httpbin.org"))
                .build();
    }
}
