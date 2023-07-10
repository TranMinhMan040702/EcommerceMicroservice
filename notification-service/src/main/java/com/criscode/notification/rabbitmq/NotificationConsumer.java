package com.criscode.notification.rabbitmq;

import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.clients.websocket.WebSocketClient;
import com.criscode.notification.service.impl.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {

    private final WebSocketClient webSocketClient;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(NotificationDto notificationDto) {
        log.info("Consumed {} from queue", notificationDto);
        webSocketClient.processMessage(notificationDto);
    }

}
