package com.criscode.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/register-confirm",
            "/auth/authenticate",
            "/auth/eureka",
            "/products/get-all",
            "/products/category",
            "/deliveries/get-all",
            "/review/product"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    // Check url require role admin
    public Predicate<ServerHttpRequest> isRequireAdminRole =
            request -> request.getURI().getPath().contains("admin");

}
