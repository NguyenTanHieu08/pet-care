package com.hieusrpingboot.petcare.dto;

import jakarta.validation.constraints.NotBlank;

public class AcceptInviteRequest {
    @NotBlank(message = "Token is required")
    private String token;

    // Constructors
    public AcceptInviteRequest() {}

    public AcceptInviteRequest(String token) {
        this.token = token;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
