package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Owner;
import com.hieusrpingboot.petcare.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmail(String email);
    List<Owner> findByRole(Role role);
    boolean existsByEmail(String email);
    Optional<Owner> findByVerificationCode(String verificationCode);
}
