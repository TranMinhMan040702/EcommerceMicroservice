package com.criscode.notification.controller;

import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

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
