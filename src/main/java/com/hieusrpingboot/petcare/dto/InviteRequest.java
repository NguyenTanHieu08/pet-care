package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.FamilyRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class InviteRequest {
    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Role is required")
    private FamilyRole role;

    // Constructors
    public InviteRequest() {}

    public InviteRequest(Long ownerId, String email, FamilyRole role) {
        this.ownerId = ownerId;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FamilyRole getRole() {
        return role;
    }

    public void setRole(FamilyRole role) {
        this.role = role;
    }
}
