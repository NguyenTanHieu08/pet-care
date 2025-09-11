package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.Role;

public class LoginResponse {
    private String token;
    private Role role;
    private String message;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String token, Role role) {
        this.token = token;
        this.role = role;
    }
    
    public LoginResponse(String token, Role role, String message) {
        this.token = token;
        this.role = role;
        this.message = message;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}

