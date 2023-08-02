package com.criscode.notification.repository;

import com.criscode.notification.entity.Notification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByRecipientIdOrderByTimestampDesc(Integer recipientId, PageRequest pageRequest);

    List<Notification> findAllByRecipientId(Integer recipientId);
}
