package com.criscode.notification.converter;

import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.notification.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationConverter {

    public Notification mapToEntity(NotificationDto notificationDto) {
        return Notification.builder()
                .id(notificationDto.getId())
                .title(notificationDto.getTitle())
                .message(notificationDto.getMessage())
                .recipientId(notificationDto.getRecipientId())
                .status(notificationDto.getStatus())
                .timestamp(new Date())
                .build();
    }

    public NotificationDto mapToDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .recipientId(notification.getRecipientId())
                .status(notification.getStatus())
                .timestamp(notification.getTimestamp())
                .build();
    }

    public List<NotificationDto> mapToDto(List<Notification> notifications) {
        return notifications.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
