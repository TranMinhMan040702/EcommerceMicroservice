package com.criscode.websocket.controller;

import com.criscode.clients.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessagesController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/websocket/send-notification/{userId}")
    public void processMessage(@RequestBody NotificationDto notificationDto,
                               @PathVariable("userId") Integer userId) {
        simpMessagingTemplate.convertAndSend("/user/" + userId + "/topic/notification", notificationDto);
    }

}
