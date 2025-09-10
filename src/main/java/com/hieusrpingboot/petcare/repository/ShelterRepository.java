package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findByEmail(String email);
    List<Shelter> findByNameContainingIgnoreCase(String name);
    boolean existsByEmail(String email);
}
