package com.criscode.notification.controller;

import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.notification.service.INotificationService;
import com.criscode.notification.service.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification-service/notification")
@CrossOrigin({ "https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/" })
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping("/send-notification")
    void sendNotification(@RequestBody NotificationDto notificationDto) {
        notificationService.sendNotification(notificationDto);
    }

    @GetMapping("/{recipientId}")
    ResponseEntity<?> findAllByRecipientId(@PathVariable("recipientId") Integer recipientId) {
        return ResponseEntity.ok(notificationService.findByRecipientId(recipientId));
    }

    @GetMapping("/get-top-5/{recipientId}")
    ResponseEntity<?> findAllByRecipientIdTop5(@PathVariable("recipientId") Integer recipientId) {
        return ResponseEntity.ok(notificationService.findByRecipientIdTop5(recipientId));
    }

    @PostMapping("/update-status/{notificationId}")
    void updateStatus(@PathVariable("notificationId") Integer notificationId) {
        notificationService.updateStatus(notificationId);
    }
}
