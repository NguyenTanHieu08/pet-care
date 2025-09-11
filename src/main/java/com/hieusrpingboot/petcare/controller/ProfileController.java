package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.entity.Owner;
import com.hieusrpingboot.petcare.repository.OwnerRepository;
import com.hieusrpingboot.petcare.repository.PetRepository;
import com.hieusrpingboot.petcare.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController extends BaseController {

    @Autowired private OwnerRepository ownerRepository;
    @Autowired private PetRepository petRepository;
    @Autowired private NotificationRepository notificationRepository;

    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> overview(@RequestParam Long userId) {
        Owner o = ownerRepository.findById(userId).orElse(null);
        if (o == null) return notFound("User not found");
        Map<String, Object> data = new HashMap<>();
        data.put("user", o);
        data.put("petsCount", petRepository.findByOwnerId(userId).size());
        data.put("unreadNotifications", notificationRepository.findByUserIdAndIsRead(userId, false).size());
        return success("OK", data);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Owner>> me(@RequestParam Long userId) {
        return ownerRepository.findById(userId)
                .map(u -> success("OK", u))
                .orElseGet(() -> notFound("User not found"));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<Owner>> update(@RequestParam Long userId, @RequestBody Owner body) {
        return ownerRepository.findById(userId)
                .map(u -> {
                    u.setName(body.getName());
                    u.setPhone(body.getPhone());
                    u.setAddress(body.getAddress());
                    return success("Updated", ownerRepository.save(u));
                })
                .orElseGet(() -> notFound("User not found"));
    }
}







