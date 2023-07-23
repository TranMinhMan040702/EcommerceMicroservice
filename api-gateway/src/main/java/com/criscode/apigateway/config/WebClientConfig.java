package com.criscode.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final HttpClient httpClient;

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
