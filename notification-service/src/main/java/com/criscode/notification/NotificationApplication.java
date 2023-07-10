package com.criscode.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication (scanBasePackages = {
        "com.criscode.amqp",
        "com.criscode.notification"
})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.criscode.clients")
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}