package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.entity.Notification;
import com.hieusrpingboot.petcare.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationsController extends BaseController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notification>>> list(@RequestParam Long userId,
                                                                @RequestParam(required = false) Boolean unread) {
        List<Notification> data = unread != null
                ? notificationRepository.findByUserIdAndIsRead(userId, !unread ? true : false)
                : notificationRepository.findByUserId(userId);
        return success("OK", data);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<String>> markRead(@PathVariable Long id) {
        Notification n = notificationRepository.findById(id).orElse(null);
        if (n == null) return notFound("Not found");
        n.setIsRead(true);
        notificationRepository.save(n);
        return success("Marked read", "OK");
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiResponse<String>> markAllRead(@RequestParam Long userId) {
        List<Notification> all = notificationRepository.findByUserId(userId);
        all.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(all);
        return success("All marked read", "OK");
    }
}


