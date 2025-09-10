package com.hieusrpingboot.petcare.dto;

import jakarta.validation.constraints.NotBlank;

public class AcceptInviteRequest {
    @NotBlank
    private String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
