package com.criscode.clients.websocket;

import com.criscode.clients.notification.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "websocket")
public interface WebSocketClient {
    @PostMapping("/websocket/send-notification")
    void processMessage(@RequestBody NotificationDto notificationDto);
}
