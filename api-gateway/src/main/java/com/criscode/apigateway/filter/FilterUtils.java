package com.criscode.apigateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Consumer;

public class FilterUtils {

    public static ServerWebExchange mutateRequestHeaders(ServerWebExchange exchange, Consumer<HttpHeaders> headersConsumer) {
        return exchange.mutate().request(
                exchange.getRequest().mutate().headers(headersConsumer).build()).build();
    }

}
