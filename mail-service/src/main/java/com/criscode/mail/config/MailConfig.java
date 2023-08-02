package com.criscode.mail.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Value("${rabbitmq.exchanges.topic}")
    private String exchange;

    @Value("${rabbitmq.queue.register-account}")
    private String registerAccountQueue;

    @Value("${rabbitmq.queue.forgot-pass}")
    private String forgotPassQueue;

    @Value("${rabbitmq.routing-key.register-account}")
    private String registerAccountRoutingKey;

    @Value("${rabbitmq.routing-key.forgot-pass}")
    private String forgotPassRoutingKey;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue registerAccountQueue() {
        return new Queue(registerAccountQueue);
    }

    @Bean
    public Queue forgotPassQueue() {
        return new Queue(forgotPassQueue);
    }

    @Bean
    public Binding bindingRegisterAccount() {
        return BindingBuilder
                .bind(registerAccountQueue())
                .to(topicExchange())
                .with(this.registerAccountRoutingKey);
    }

    @Bean
    public Binding bindingForgotPass() {
        return BindingBuilder
                .bind(forgotPassQueue())
                .to(topicExchange())
                .with(this.forgotPassRoutingKey);
    }

}
