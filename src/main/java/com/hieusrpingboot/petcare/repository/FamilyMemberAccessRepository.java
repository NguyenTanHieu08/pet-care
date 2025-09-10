package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.FamilyMemberAccess;
import com.hieusrpingboot.petcare.enums.FamilyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMemberAccessRepository extends JpaRepository<FamilyMemberAccess, Long> {
    Optional<FamilyMemberAccess> findByInviteToken(String token);
    List<FamilyMemberAccess> findByOwnerId(Long ownerId);
    Optional<FamilyMemberAccess> findByOwnerIdAndMemberUserId(Long ownerId, Long memberUserId);
    boolean existsByOwnerIdAndMemberUserIdAndRole(Long ownerId, Long memberUserId, FamilyRole role);
}


