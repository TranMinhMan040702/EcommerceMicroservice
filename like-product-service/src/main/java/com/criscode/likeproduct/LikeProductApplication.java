package com.criscode.likeproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LikeProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(LikeProductApplication.class, args);
    }
}
