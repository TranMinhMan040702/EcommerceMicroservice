package com.criscode.apigateway.filter;

import com.criscode.clients.user.UserClient;
import com.criscode.clients.user.dto.ValidateTokenResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    private final RouteValidator routeValidator;

    private final UserClient userClient;


    public AuthenticationFilter(RouteValidator routeValidator, UserClient userClient) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.userClient = userClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                ServerHttpRequest request = exchange.getRequest();
                String jwtToken = extractJwtToken(request);

                if (jwtToken != null) {
                    ValidateTokenResponse validateTokenResponse = userClient.validateAccessToken(jwtToken);
                    if (validateTokenResponse.getUsername() != null) {
                        String username = validateTokenResponse.getUsername();
                        List<String> roles = validateTokenResponse.getAuthorities();

                        // todo: Call auth-service to check token and get user in db
                        if (checkRequireAdminRole(request) && !roles.contains("ADMIN")) {
                            return onError(exchange, "Access denied. Admin role required.");
                        }

                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                .header("X-Roles", String.join(",", roles))
                                .header("X-User", username)
                                .build();

                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    }
                }
                return onError(exchange, "Invalid JWT Token");
            }
            return chain.filter(exchange);
        });
    }

    private String extractJwtToken(ServerHttpRequest request) {
        List<String> authorizationHeaders = request.getHeaders().get("Authorization");
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String authorizationHeader = authorizationHeaders.get(0);
            if (authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring(7);
            }
        }
        return null;
    }

    private Boolean checkRequireAdminRole(ServerHttpRequest request) {
        return routeValidator.isRequireAdminRole.test(request);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    public static class Config {

    }
}
