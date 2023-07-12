package com.criscode.clients.notification;

import com.criscode.clients.notification.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification")
public interface NotificationClient {
    @PostMapping("/notification/send-notification")
    void sendNotification(@RequestBody NotificationDto notificationDto);
}
