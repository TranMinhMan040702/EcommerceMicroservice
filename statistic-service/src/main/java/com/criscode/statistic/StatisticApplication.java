package com.criscode.statistic;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.criscode.clients")
public class StatisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class, args);
    }
}
