package com.hieusrpingboot.petcare.service;

import com.hieusrpingboot.petcare.dto.AcceptInviteRequest;
import com.hieusrpingboot.petcare.dto.InviteRequest;
import com.hieusrpingboot.petcare.dto.UpdateMemberRoleRequest;
import com.hieusrpingboot.petcare.entity.FamilyMemberAccess;
import com.hieusrpingboot.petcare.enums.FamilyRole;
import com.hieusrpingboot.petcare.repository.FamilyMemberAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyAccessService {
    private static final Logger log = LoggerFactory.getLogger(FamilyAccessService.class);

    @Autowired
    private FamilyMemberAccessRepository accessRepository;

    @Autowired(required = false)
    private EmailService emailService;

    private String generateToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Transactional
    public FamilyMemberAccess invite(InviteRequest req, Long inviterUserId, Long existingMemberUserIdOrNull) {
        Optional<FamilyMemberAccess> existing = accessRepository
                .findByOwnerIdAndMemberUserId(req.getOwnerId(), existingMemberUserIdOrNull);
        if (existing.isPresent()) {
            return existing.get();
        }
        FamilyMemberAccess access = new FamilyMemberAccess();
        access.setOwnerId(req.getOwnerId());
        access.setInviteEmail(req.getEmail());
        access.setRole(req.getRole());
        access.setStatus("INVITED");
        access.setInviteToken(generateToken());
        if (existingMemberUserIdOrNull != null) {
            access.setMemberUserId(existingMemberUserIdOrNull);
        }
        FamilyMemberAccess saved = accessRepository.save(access);

        // Log token and a sample accept URL for developer convenience
        log.info("[INVITE] ownerId={} email={} token={} acceptURL={}",
                saved.getOwnerId(), saved.getInviteEmail(), saved.getInviteToken(),
                "http://localhost:8080/api/family/invitations/accept?currentUserId=<USER_ID>");

        if (emailService != null) {
            String subject = "Invitation to access owner account";
            String content = "You have been invited. Use token: " + saved.getInviteToken();
            emailService.sendPlainText(req.getEmail(), subject, content);
        }
        return saved;
    }

    @Transactional
    public FamilyMemberAccess accept(AcceptInviteRequest req, Long currentUserId) {
        FamilyMemberAccess access = accessRepository.findByInviteToken(req.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        access.setMemberUserId(currentUserId);
        access.setStatus("ACTIVE");
        access.setInviteToken(null);
        return accessRepository.save(access);
    }

    public List<FamilyMemberAccess> listMembers(Long ownerId) {
        return accessRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public FamilyMemberAccess updateRole(Long id, UpdateMemberRoleRequest req) {
        FamilyMemberAccess access = accessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        access.setRole(req.getRole());
        return accessRepository.save(access);
    }

    @Transactional
    public void revoke(Long id) {
        FamilyMemberAccess access = accessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        access.setStatus("REVOKED");
        accessRepository.save(access);
    }

    // Permission helpers
    public boolean canManageMembers(Long ownerId, Long userId) {
        if (userId == null) return false;
        return accessRepository.existsByOwnerIdAndMemberUserIdAndRole(ownerId, userId, FamilyRole.FULL_ACCESS);
    }
}
