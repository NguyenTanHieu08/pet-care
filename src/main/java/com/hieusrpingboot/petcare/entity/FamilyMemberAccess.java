package com.hieusrpingboot.petcare.entity;

import com.hieusrpingboot.petcare.enums.FamilyRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "family_member_access",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_owner_member", columnNames = {"owner_id", "member_user_id"}),
                @UniqueConstraint(name = "uq_owner_invite_email", columnNames = {"owner_id", "invite_email"})
        })
public class FamilyMemberAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "member_user_id")
    private Long memberUserId;

    @Email
    @Column(name = "invite_email")
    private String inviteEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private FamilyRole role = FamilyRole.VIEW_ONLY;

    @Column(name = "status", nullable = false)
    private String status = "INVITED"; // INVITED | ACTIVE | REVOKED

    @Column(name = "invite_token", unique = true)
    private String inviteToken;

    @Column(name = "invited_at")
    private LocalDateTime invitedAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public Long getMemberUserId() { return memberUserId; }
    public void setMemberUserId(Long memberUserId) { this.memberUserId = memberUserId; }
    public String getInviteEmail() { return inviteEmail; }
    public void setInviteEmail(String inviteEmail) { this.inviteEmail = inviteEmail; }
    public FamilyRole getRole() { return role; }
    public void setRole(FamilyRole role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getInviteToken() { return inviteToken; }
    public void setInviteToken(String inviteToken) { this.inviteToken = inviteToken; }
    public LocalDateTime getInvitedAt() { return invitedAt; }
    public void setInvitedAt(LocalDateTime invitedAt) { this.invitedAt = invitedAt; }
    public LocalDateTime getAcceptedAt() { return acceptedAt; }
    public void setAcceptedAt(LocalDateTime acceptedAt) { this.acceptedAt = acceptedAt; }
    public LocalDateTime getRevokedAt() { return revokedAt; }
    public void setRevokedAt(LocalDateTime revokedAt) { this.revokedAt = revokedAt; }
}



