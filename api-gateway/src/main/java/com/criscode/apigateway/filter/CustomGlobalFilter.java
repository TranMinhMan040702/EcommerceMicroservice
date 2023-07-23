package com.criscode.apigateway.filter;

import com.criscode.apigateway.config.SingleResponse;
import com.criscode.apigateway.config.ValidateTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class CustomGlobalFilter implements GlobalFilter {

    private final RouteValidator routeValidator;
    private final WebClient.Builder webBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (routeValidator.isSecured.test(exchange.getRequest())) {
            ServerHttpRequest request = exchange.getRequest();
            String jwtToken = extractJwtToken(request);

            if (jwtToken != null) {
                // todo: Call auth-service to check token and get user in db
                return webBuilder.build()
                        .patch()
                        .uri("lb://IDENTITY/api/v1/identity-service/auth/validate-token/" + jwtToken)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<SingleResponse<ValidateTokenResponse>>() {})
                        .map(response -> FilterUtils.mutateRequestHeaders(exchange, h -> h.setAll(Map.of(
                                "X-Username", response.data().getUsername(),
                                "X-Roles", String.join(",", response.data().getAuthorities())
                        ))))
                        .flatMap(chain::filter)
                        .onErrorResume(throwable -> {
                            log.debug("Invalid JWT Token");
                            return onError(exchange, "Invalid JWT Token");
                        });

            }
            log.debug("Header not has JWT Token");
            return onError(exchange, "Header not has JWT Token");
        }
        return chain.filter(exchange);
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

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
