package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.dto.*;
import com.hieusrpingboot.petcare.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.register(registerRequest);
            
            if (response.getMessage() != null && response.getMessage().contains("already exists")) {
                return conflict("Email already exists");
            }
            
            return success("User registered successfully", response, org.springframework.http.HttpStatus.CREATED);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/shelter/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerShelter(@Valid @RequestBody ShelterRegisterRequest request) {
        try {
            AuthResponse res = authService.registerShelter(request);
            if (res.getMessage() != null && res.getMessage().contains("already exists")) {
                return conflict("Email already exists");
            }
            return success("Shelter registered. Please verify email", res, org.springframework.http.HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Register shelter failed: " + e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        try {
            authService.verifyEmail(request.getEmail(), request.getCode());
            return success("Email verified successfully", "OK");
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Verification failed: " + e.getMessage());
        }
    }

    @PostMapping("/enable-2fa")
    public ResponseEntity<ApiResponse<String>> enable2fa(@RequestParam String email, @RequestParam boolean enabled) {
        try {
            authService.setTwoFactor(email, enabled);
            return success(enabled ? "2FA enabled" : "2FA disabled", "OK");
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Update 2FA failed: " + e.getMessage());
        }
    }

    @PostMapping("/request-login-otp")
    public ResponseEntity<ApiResponse<String>> requestLoginOtp(@RequestParam String email) {
        try {
            authService.sendLoginOtp(email);
            return success("OTP sent to email", "OK");
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Send OTP failed: " + e.getMessage());
        }
    }

    @PostMapping("/verify-login-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyLoginOtp(@Valid @RequestBody LoginOtpRequest request) {
        try {
            AuthResponse res = authService.verifyLoginOtp(request.getEmail(), request.getOtp());
            return success("Login with OTP successful", res);
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Verify OTP failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest);
            
            if (authResponse.getMessage() != null && authResponse.getMessage().contains("Invalid")) {
                return unauthorized("Invalid email or password");
            }
            
            // Chỉ trả về token và role, bỏ các thông tin khác
            LoginResponse response = new LoginResponse(authResponse.getToken(), authResponse.getRole());
            return success("Login successful", response);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<AuthResponse>> getUser(@PathVariable Long userId) {
        try {
            if (!isValidId(userId)) {
                return badRequest("Invalid user ID");
            }
            
            AuthResponse response = authService.getUserById(userId);
            
            if (response.getMessage() != null && response.getMessage().contains("not found")) {
                return notFound("User not found");
            }
            
            return success("User found", response);
                
        } catch (Exception e) {
            return internalServerError("Failed to get user: " + e.getMessage());
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<AuthResponse>> updateUser(@PathVariable Long userId, 
                                                               @Valid @RequestBody RegisterRequest updateRequest) {
        try {
            if (!isValidId(userId)) {
                return badRequest("Invalid user ID");
            }
            
            AuthResponse response = authService.updateUser(userId, updateRequest);
            
            if (response.getMessage() != null && response.getMessage().contains("not found")) {
                return notFound("User not found");
            }
            
            return success("User updated successfully", response);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Update failed: " + e.getMessage());
        }
    }
}
