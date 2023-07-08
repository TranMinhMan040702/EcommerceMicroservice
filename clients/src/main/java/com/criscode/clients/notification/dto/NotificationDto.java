package com.criscode.clients.notification.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NotificationDto {

    private Integer id;

    private String title;

    private String message;

    private Date timestamp;

    private Integer recipientId;

    private Boolean status;

}
