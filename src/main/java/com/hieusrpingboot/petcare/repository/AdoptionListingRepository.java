package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.AdoptionListing;
import com.hieusrpingboot.petcare.enums.AdoptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionListingRepository extends JpaRepository<AdoptionListing, Long> {
    List<AdoptionListing> findByShelterId(Long shelterId);
    List<AdoptionListing> findByStatus(AdoptionStatus status);
    List<AdoptionListing> findBySpecies(String species);
    List<AdoptionListing> findByBreed(String breed);
    List<AdoptionListing> findByStatusAndSpecies(AdoptionStatus status, String species);
}
