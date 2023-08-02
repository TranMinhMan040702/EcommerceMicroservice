package com.criscode.notification.service.impl;

import com.criscode.amqp.RabbitMQMessageProducer;
import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.notification.converter.NotificationConverter;
import com.criscode.notification.entity.Notification;
import com.criscode.notification.repository.NotificationRepository;
import com.criscode.notification.service.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final RabbitMQMessageProducer producer;
    private final NotificationConverter notificationConverter;
    private final NotificationRepository notificationRepository;
    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String routing_key;

    @Override
    public void sendNotification(NotificationDto notificationDto) {
        log.info("Push notification {}", notificationDto);
        producer.publish(notificationDto, exchange, routing_key);
    }

    @Override
    public void save(NotificationDto notificationDto) {
        notificationRepository.save(notificationConverter.mapToEntity(notificationDto));
    }

    @Override
    public List<NotificationDto> findByRecipientId(Integer recipientId) {
        return notificationConverter.mapToDto(
                notificationRepository.findAllByRecipientId(recipientId)
        );
    }

    // Error, hasn't date => create date
    @Override
    public List<NotificationDto> findByRecipientIdTop5(Integer recipientId) {
        PageRequest pageRequest = PageRequest.of(0, 5);
        return notificationConverter.mapToDto(
                notificationRepository.findAllByRecipientIdOrderByTimestampDesc(recipientId, pageRequest)
        );
    }

    @Override
    public void updateStatus(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundException("Notification does exist with id: " + notificationId)
        );
        notification.setStatus(true);
        notificationRepository.save(notification);
    }
}
