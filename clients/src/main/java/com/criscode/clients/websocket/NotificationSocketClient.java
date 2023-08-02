package com.criscode.clients.websocket;

import com.criscode.clients.notification.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "websocket")
public interface NotificationSocketClient {
    @PostMapping("/websocket/send-notification/{userId}")
    void processMessage(@RequestBody NotificationDto notificationDto, @PathVariable("userId") Integer userId);

}
