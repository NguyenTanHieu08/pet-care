package com.hieusrpingboot.petcare.service;

import com.hieusrpingboot.petcare.dto.AuthResponse;
import com.hieusrpingboot.petcare.dto.LoginRequest;
import com.hieusrpingboot.petcare.dto.RegisterRequest;
import com.hieusrpingboot.petcare.entity.Owner;
import com.hieusrpingboot.petcare.enums.Role;
import com.hieusrpingboot.petcare.repository.OwnerRepository;
import com.hieusrpingboot.petcare.repository.ShelterRepository;
import com.hieusrpingboot.petcare.entity.Shelter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    private String generateNumericCode(int length) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append(r.nextInt(10));
        return sb.toString();
    }

    private String generateResetToken() {
        // reuse numeric code for simplicity; can switch to UUID
        return generateNumericCode(6);
    }

    public void requestPasswordReset(String email) {
        Optional<Owner> opt = ownerRepository.findByEmail(email);
        if (opt.isEmpty()) return; // do not reveal user existence
        Owner o = opt.get();
        o.setVerificationCode(generateResetToken());
        o.setVerificationExpiry(LocalDateTime.now().plusMinutes(10));
        ownerRepository.save(o);
        log.info("[FORGOT PASSWORD] email={} token={}", o.getEmail(), o.getVerificationCode());
        emailService.sendPlainText(o.getEmail(), "Reset your PetCare password",
                "Your reset code is: " + o.getVerificationCode() + " (expires in 10 minutes)");
    }

    public void resetPassword(String token, String newPassword) {
        Optional<Owner> opt = ownerRepository.findByVerificationCode(token);
        if (opt.isEmpty()) throw new IllegalArgumentException("Invalid token");
        Owner o = opt.get();
        if (o.getVerificationExpiry() == null || LocalDateTime.now().isAfter(o.getVerificationExpiry()))
            throw new IllegalArgumentException("Token expired");
        o.setPasswordHash(passwordEncoder.encode(newPassword));
        o.setVerificationCode(null);
        o.setVerificationExpiry(null);
        ownerRepository.save(o);
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        // Check if email already exists
        if (ownerRepository.existsByEmail(registerRequest.getEmail())) {
            return new AuthResponse("Email already exists");
        }

        // Create new owner
        Owner owner = new Owner();
        owner.setName(registerRequest.getName());
        owner.setEmail(registerRequest.getEmail());
        owner.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        owner.setPhone(registerRequest.getPhone());
        owner.setAddress(registerRequest.getAddress());
        owner.setRole(Role.valueOf(registerRequest.getRole()));

        // Save owner
        Owner savedOwner = ownerRepository.save(owner);

        // prepare email verification
        savedOwner.setEmailVerified(false);
        savedOwner.setVerificationCode(generateNumericCode(6));
        savedOwner.setVerificationExpiry(LocalDateTime.now().plusMinutes(10));
        ownerRepository.save(savedOwner);

        // Log verification code to console for local testing convenience
        log.info("[VERIFY EMAIL] email={} code={}", savedOwner.getEmail(), savedOwner.getVerificationCode());

        emailService.sendPlainText(
            savedOwner.getEmail(),
            "Verify your PetCare account",
            "Your verification code is: " + savedOwner.getVerificationCode() + " (expires in 10 minutes)"
        );

        // Return without token; require email verification first
        return new AuthResponse(
            savedOwner.getId(),
            savedOwner.getName(),
            savedOwner.getEmail(),
            savedOwner.getPhone(),
            savedOwner.getAddress(),
            savedOwner.getRole(),
            "PENDING_EMAIL_VERIFICATION"
        );
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // Find owner by email
        Optional<Owner> ownerOpt = ownerRepository.findByEmail(loginRequest.getEmail());
        
        if (ownerOpt.isEmpty()) {
            return new AuthResponse("Invalid email or password");
        }

        Owner owner = ownerOpt.get();

        // Check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), owner.getPasswordHash())) {
            return new AuthResponse("Invalid email or password");
        }

        if (!owner.isEmailVerified()) {
            // resend verification
            owner.setVerificationCode(generateNumericCode(6));
            owner.setVerificationExpiry(LocalDateTime.now().plusMinutes(10));
            ownerRepository.save(owner);
            log.info("[VERIFY EMAIL - RESEND] email={} code={}", owner.getEmail(), owner.getVerificationCode());
            emailService.sendPlainText(owner.getEmail(), "Verify your PetCare account",
                "Your verification code is: " + owner.getVerificationCode());
            return new AuthResponse("Email not verified. Verification code resent.");
        }

        String token = jwtService.generateToken(owner.getEmail());
        return new AuthResponse(
            owner.getId(),
            owner.getName(),
            owner.getEmail(),
            owner.getPhone(),
            owner.getAddress(),
            owner.getRole(),
            token
        );
    }

    public AuthResponse getUserById(Long userId) {
        Optional<Owner> ownerOpt = ownerRepository.findById(userId);
        
        if (ownerOpt.isEmpty()) {
            return new AuthResponse("User not found");
        }

        Owner owner = ownerOpt.get();
        String token = jwtService.generateToken(owner.getEmail());
        return new AuthResponse(
            owner.getId(),
            owner.getName(),
            owner.getEmail(),
            owner.getPhone(),
            owner.getAddress(),
            owner.getRole(),
            token
        );
    }

    public AuthResponse updateUser(Long userId, RegisterRequest updateRequest) {
        Optional<Owner> ownerOpt = ownerRepository.findById(userId);
        
        if (ownerOpt.isEmpty()) {
            return new AuthResponse("User not found");
        }

        Owner owner = ownerOpt.get();
        
        // Update fields
        owner.setName(updateRequest.getName());
        owner.setPhone(updateRequest.getPhone());
        owner.setAddress(updateRequest.getAddress());
        
        // Only update password if provided
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().trim().isEmpty()) {
            owner.setPasswordHash(passwordEncoder.encode(updateRequest.getPassword()));
        }

        Owner updatedOwner = ownerRepository.save(owner);

        String token = jwtService.generateToken(updatedOwner.getEmail());
        return new AuthResponse(
            updatedOwner.getId(),
            updatedOwner.getName(),
            updatedOwner.getEmail(),
            updatedOwner.getPhone(),
            updatedOwner.getAddress(),
            updatedOwner.getRole(),
            token
        );
    }

    // Shelter registration: create Owner(role=SHELTER) + Shelter profile, send email verify
    @Autowired
    private ShelterRepository shelterRepository;

    public AuthResponse registerShelter(com.hieusrpingboot.petcare.dto.ShelterRegisterRequest req) {
        if (ownerRepository.existsByEmail(req.getEmail())) {
            return new AuthResponse("Email already exists");
        }
        Owner owner = new Owner();
        owner.setName(req.getName());
        owner.setEmail(req.getEmail());
        owner.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        owner.setPhone(req.getPhone());
        owner.setAddress(req.getAddress());
        owner.setRole(Role.SHELTER);
        Owner saved = ownerRepository.save(owner);

        // Create Shelter record
        Shelter shelter = new Shelter(
            req.getName(), req.getContactPerson(), req.getEmail(), req.getPhone(), req.getAddress()
        );
        shelterRepository.save(shelter);

        // prepare verification for shelter owner
        saved.setEmailVerified(false);
        saved.setVerificationCode(generateNumericCode(6));
        saved.setVerificationExpiry(LocalDateTime.now().plusMinutes(10));
        ownerRepository.save(saved);
        log.info("[VERIFY EMAIL] email={} code={}", saved.getEmail(), saved.getVerificationCode());
        emailService.sendPlainText(saved.getEmail(), "Verify your PetCare shelter account",
            "Your verification code is: " + saved.getVerificationCode());

        return new AuthResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone(), saved.getAddress(), saved.getRole(), "PENDING_EMAIL_VERIFICATION");
    }

    // New 2FA / verification flows
    public void verifyEmail(String email, String code) {
        Optional<Owner> opt = ownerRepository.findByEmail(email);
        if (opt.isEmpty()) throw new IllegalArgumentException("User not found");
        Owner o = opt.get();
        if (o.getVerificationCode() == null || o.getVerificationExpiry() == null)
            throw new IllegalArgumentException("No verification request found");
        if (LocalDateTime.now().isAfter(o.getVerificationExpiry()))
            throw new IllegalArgumentException("Verification code expired");
        if (!o.getVerificationCode().equals(code))
            throw new IllegalArgumentException("Invalid code");
        o.setEmailVerified(true);
        o.setVerificationCode(null);
        o.setVerificationExpiry(null);
        ownerRepository.save(o);
    }

    public void setTwoFactor(String email, boolean enabled) {
        Optional<Owner> opt = ownerRepository.findByEmail(email);
        if (opt.isEmpty()) throw new IllegalArgumentException("User not found");
        Owner o = opt.get();
        if (!o.isEmailVerified()) throw new IllegalArgumentException("Email not verified");
        o.setTwoFactorEnabled(enabled);
        ownerRepository.save(o);
    }

    public void sendLoginOtp(String email) {
        Optional<Owner> opt = ownerRepository.findByEmail(email);
        if (opt.isEmpty()) throw new IllegalArgumentException("User not found");
        Owner o = opt.get();
        if (!o.isTwoFactorEnabled()) throw new IllegalArgumentException("2FA is not enabled");
        o.setLoginOtp(generateNumericCode(6));
        o.setLoginOtpExpiry(LocalDateTime.now().plusMinutes(5));
        ownerRepository.save(o);
        emailService.sendPlainText(email, "Your login OTP", "OTP: " + o.getLoginOtp());
    }

    public AuthResponse verifyLoginOtp(String email, String otp) {
        Optional<Owner> opt = ownerRepository.findByEmail(email);
        if (opt.isEmpty()) throw new IllegalArgumentException("User not found");
        Owner o = opt.get();
        if (o.getLoginOtp() == null || o.getLoginOtpExpiry() == null)
            throw new IllegalArgumentException("No OTP requested");
        if (LocalDateTime.now().isAfter(o.getLoginOtpExpiry()))
            throw new IllegalArgumentException("OTP expired");
        if (!o.getLoginOtp().equals(otp))
            throw new IllegalArgumentException("Invalid OTP");
        o.setLoginOtp(null);
        o.setLoginOtpExpiry(null);
        ownerRepository.save(o);
        String token = jwtService.generateToken(o.getEmail());
        return new AuthResponse(
            o.getId(), o.getName(), o.getEmail(), o.getPhone(), o.getAddress(), o.getRole(), token
        );
    }
}
