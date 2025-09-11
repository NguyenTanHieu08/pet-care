package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.FamilyRole;
import jakarta.validation.constraints.NotNull;

public class UpdateMemberRoleRequest {
    @NotNull(message = "Role is required")
    private FamilyRole role;

    // Constructors
    public UpdateMemberRoleRequest() {}

    public UpdateMemberRoleRequest(FamilyRole role) {
        this.role = role;
    }

    // Getters and Setters
    public FamilyRole getRole() {
        return role;
    }

    public void setRole(FamilyRole role) {
        this.role = role;
    }
}
