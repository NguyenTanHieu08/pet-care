package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.FamilyRole;
import jakarta.validation.constraints.NotNull;

public class UpdateMemberRoleRequest {
    @NotNull
    private FamilyRole role;

    public FamilyRole getRole() { return role; }
    public void setRole(FamilyRole role) { this.role = role; }
}
