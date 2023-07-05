package com.criscode.mail.config;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value(("${rabbitmq.queue.mail}"))
    private String mailQueue;

    @Value(("${rabbitmq.routing-keys.internal-mail}"))
    private String internalMailRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(this.mailQueue);
    }

    @Bean
    public Binding internalToMailBinding() {
        return BindingBuilder.bind(mailQueue())
                .to(internalTopicExchange())
                .with(this.internalMailRoutingKey);
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getMailQueue() {
        return mailQueue;
    }

    public String getInternalMailRoutingKey() {
        return internalMailRoutingKey;
    }
}
