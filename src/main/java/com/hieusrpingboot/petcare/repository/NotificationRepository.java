package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Notification;
import com.hieusrpingboot.petcare.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndIsRead(Long userId, Boolean isRead);
    List<Notification> findByType(NotificationType type);
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Notification> findByCreatedAtAfter(LocalDateTime after);
}
