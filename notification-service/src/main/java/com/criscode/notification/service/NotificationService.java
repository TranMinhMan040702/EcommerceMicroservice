package com.criscode.notification.service;

import com.criscode.amqp.RabbitMQMessageProducer;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.notification.converter.NotificationConverter;
import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.notification.entity.Notification;
import com.criscode.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final RabbitMQMessageProducer producer;
    private final NotificationConverter notificationConverter;
    private final NotificationRepository notificationRepository;
    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String routing_key;

    public void sendNotification(NotificationDto notificationDto) {
        log.info("Push notification {}", notificationDto);
        //todo: save notification
        notificationRepository.save(notificationConverter.mapToEntity(notificationDto));
        //todo: push notification into rabbitmq
        producer.publish(notificationDto, exchange, routing_key);
    }

    public List<NotificationDto> findByRecipientId(Integer recipientId) {
        return notificationConverter.mapToDto(
                notificationRepository.findAllByRecipientId(recipientId)
        );
    }

    public List<NotificationDto> findByRecipientIdTop5(Integer recipientId) {
        PageRequest pageRequest = PageRequest.of(0, 5);
        return notificationConverter.mapToDto(
                notificationRepository.findAllByRecipientIdOrOrderByTimestampDesc(recipientId, pageRequest)
        );
    }

    public void updateStatus(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundException("Notification does exist with id: " + notificationId)
        );
        notification.setStatus(true);
        notificationRepository.save(notification);
    }
}
