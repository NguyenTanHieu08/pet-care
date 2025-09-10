package com.hieusrpingboot.petcare.service;

import com.hieusrpingboot.petcare.entity.Pet;
import com.hieusrpingboot.petcare.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService implements IPetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Pet> savePetsForAppointment(List<Pet> pets) {
        if (pets == null || pets.isEmpty()) {
            throw new IllegalArgumentException("Pet list cannot be null or empty");
        }
        
        // Validate each pet before saving
        for (Pet pet : pets) {
            validatePet(pet);
        }
        
        return petRepository.saveAll(pets);
    }

    @Override
    public List<Pet> savePetForAppointment(List<Pet> pets) {
        // This method seems to be a duplicate of savePetsForAppointment
        // I'll implement it the same way for consistency
        return savePetsForAppointment(pets);
    }

    // Additional method to create a single pet
    public Pet createPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        validatePet(pet);
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Pet pet, Long id) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        
        // Check if pet exists
        Optional<Pet> existingPetOpt = petRepository.findById(id);
        if (existingPetOpt.isEmpty()) {
            throw new RuntimeException("Pet with ID " + id + " not found");
        }
        
        Pet existingPet = existingPetOpt.get();
        
        // Update fields
        existingPet.setName(pet.getName());
        existingPet.setSpecies(pet.getSpecies());
        existingPet.setBreed(pet.getBreed());
        existingPet.setAge(pet.getAge());
        existingPet.setGender(pet.getGender());
        existingPet.setPhotoUrl(pet.getPhotoUrl());
        
        // Keep the original ownerId
        // existingPet.setOwnerId(pet.getOwnerId()); // Don't change owner
        
        validatePet(existingPet);
        
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePet(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        
        // Check if pet exists
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet with ID " + id + " not found");
        }
        
        petRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Pet getPetById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new RuntimeException("Pet with ID " + id + " not found");
        }
        
        return pet.get();
    }

    // Additional helper methods
    @Transactional(readOnly = true)
    public List<Pet> getPetsByOwnerId(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        return petRepository.findByOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    public List<Pet> getPetsBySpecies(String species) {
        if (species == null || species.trim().isEmpty()) {
            throw new IllegalArgumentException("Species cannot be null or empty");
        }
        return petRepository.findBySpecies(species);
    }

    @Transactional(readOnly = true)
    public List<Pet> getPetsByBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            throw new IllegalArgumentException("Breed cannot be null or empty");
        }
        return petRepository.findByBreed(breed);
    }

    @Transactional(readOnly = true)
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Private helper method for validation
    private void validatePet(Pet pet) {
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name is required");
        }
        if (pet.getSpecies() == null || pet.getSpecies().trim().isEmpty()) {
            throw new IllegalArgumentException("Pet species is required");
        }
        if (pet.getOwnerId() == null) {
            throw new IllegalArgumentException("Pet owner ID is required");
        }
        if (pet.getAge() != null && pet.getAge() < 0) {
            throw new IllegalArgumentException("Pet age cannot be negative");
        }
    }
}
