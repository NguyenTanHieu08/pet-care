package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.FamilyRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class InviteRequest {
    @NotNull
    private Long ownerId;
    @Email
    @NotNull
    private String email;
    @NotNull
    private FamilyRole role;

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public FamilyRole getRole() { return role; }
    public void setRole(FamilyRole role) { this.role = role; }
}


