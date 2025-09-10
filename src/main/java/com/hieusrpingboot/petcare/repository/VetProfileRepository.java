package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.VetProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VetProfileRepository extends JpaRepository<VetProfile, Long> {
    Optional<VetProfile> findByUserId(Long userId);
    List<VetProfile> findBySpecialization(String specialization);
    List<VetProfile> findByExperienceGreaterThanEqual(Integer minExperience);
}
