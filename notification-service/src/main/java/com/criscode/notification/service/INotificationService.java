package com.criscode.notification.service;

import com.criscode.clients.notification.dto.NotificationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface INotificationService {
    void sendNotification(NotificationDto notificationDto);

    void save(NotificationDto notificationDto);

    List<NotificationDto> findByRecipientId(Integer recipientId);

    List<NotificationDto> findByRecipientIdTop5(Integer recipientId);

    void updateStatus(Integer notificationId);
}
