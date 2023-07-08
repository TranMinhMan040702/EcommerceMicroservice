package com.criscode.notification.rabbitmq;

import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.notification.service.impl.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

//    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(EmailDetails emailDetails) {
        log.info("Consumed {} from queue", emailDetails);
//        mailService.sendActivationEmail(emailDetails);
    }

}
