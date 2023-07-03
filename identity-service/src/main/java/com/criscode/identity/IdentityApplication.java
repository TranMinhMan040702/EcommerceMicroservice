package com.criscode.identity;

import com.criscode.identity.entity.Role;
import com.criscode.identity.repository.RoleRepository;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.client.HttpClient;

import javax.swing.*;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.criscode.clients")
public class IdentityApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
