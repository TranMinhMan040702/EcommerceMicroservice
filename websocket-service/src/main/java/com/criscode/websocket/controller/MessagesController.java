package com.criscode.websocket.controller;

import com.criscode.clients.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessagesController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/websocket/send-notification")
    public void processMessage(@RequestBody NotificationDto notificationDto) {
        simpMessagingTemplate.convertAndSend("/topic/notification", notificationDto);
    }
}
