package com.criscode.mail.rabbitmq;

import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.mail.service.impl.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.queue.mail}")
    public void consumer(EmailDetails emailDetails) {
        log.info("Consumed {} from queue", emailDetails);
        mailService.sendActivationEmail(emailDetails);
    }

}
