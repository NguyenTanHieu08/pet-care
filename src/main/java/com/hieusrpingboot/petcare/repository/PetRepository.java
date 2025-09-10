package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerId(Long ownerId);
    List<Pet> findBySpecies(String species);
    List<Pet> findByBreed(String breed);
}
