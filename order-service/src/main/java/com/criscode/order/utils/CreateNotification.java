package com.criscode.order.utils;

import com.criscode.clients.notification.dto.NotificationDto;

import java.util.Date;

public class CreateNotification {

    private static final String TITLE_DELIVERED = "Đơn hàng đã giao";

    public static NotificationDto delivered(Integer orderId, Integer userId) {
        return NotificationDto.builder()
                .title(TITLE_DELIVERED)
                .message("Đơn hàng có mã " + orderId + " đã được giao")
                .timestamp(new Date())
                .recipientId(userId)
                .status(false)
                .build();
    }
}
