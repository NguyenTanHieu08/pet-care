package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.dto.PetRequest;
import com.hieusrpingboot.petcare.entity.Pet;
import com.hieusrpingboot.petcare.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*")

public class PetController extends BaseController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<ApiResponse<Pet>> createPet(@Valid @RequestBody PetRequest petRequest) {
        try {
            Pet pet = new Pet(
                petRequest.getOwnerId(),
                petRequest.getName(),
                petRequest.getSpecies(),
                petRequest.getBreed(),
                petRequest.getAge(),
                petRequest.getGender(),
                petRequest.getPhotoUrl()
            );
            
            Pet savedPet = petService.createPet(pet);
            return success("Pet created successfully", savedPet, org.springframework.http.HttpStatus.CREATED);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Failed to create pet: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<Pet>>> createPets(@Valid @RequestBody List<PetRequest> petRequests) {
        try {
            if (petRequests == null || petRequests.isEmpty()) {
                return badRequest("Pet list cannot be empty");
            }
            
            List<Pet> pets = petRequests.stream()
                    .map(request -> new Pet(
                        request.getOwnerId(),
                        request.getName(),
                        request.getSpecies(),
                        request.getBreed(),
                        request.getAge(),
                        request.getGender(),
                        request.getPhotoUrl()
                    ))
                    .toList();
            
            List<Pet> savedPets = petService.savePetsForAppointment(pets);
            return success("Pets created successfully", savedPets, org.springframework.http.HttpStatus.CREATED);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Failed to create pets: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pet>> getPet(@PathVariable Long id) {
        try {
            if (!isValidId(id)) {
                return badRequest("Invalid pet ID");
            }
            
            Pet pet = petService.getPetById(id);
            return success("Pet found", pet);
                
        } catch (RuntimeException e) {
            return notFound("Pet not found");
        } catch (Exception e) {
            return internalServerError("Failed to get pet: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Pet>> updatePet(@PathVariable Long id, @Valid @RequestBody PetRequest petRequest) {
        try {
            if (!isValidId(id)) {
                return badRequest("Invalid pet ID");
            }
            
            Pet pet = new Pet(
                petRequest.getOwnerId(),
                petRequest.getName(),
                petRequest.getSpecies(),
                petRequest.getBreed(),
                petRequest.getAge(),
                petRequest.getGender(),
                petRequest.getPhotoUrl()
            );
            
            Pet updatedPet = petService.updatePet(pet, id);
            return success("Pet updated successfully", updatedPet);
                
        } catch (RuntimeException e) {
            return notFound("Pet not found");
        } catch (Exception e) {
            return internalServerError("Failed to update pet: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePet(@PathVariable Long id) {
        try {
            if (!isValidId(id)) {
                return badRequest("Invalid pet ID");
            }
            
            petService.deletePet(id);
            return success("Pet deleted successfully");
                
        } catch (RuntimeException e) {
            return notFound("Pet not found");
        } catch (Exception e) {
            return internalServerError("Failed to delete pet: " + e.getMessage());
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<Pet>>> getPetsByOwner(@PathVariable Long ownerId) {
        try {
            if (!isValidId(ownerId)) {
                return badRequest("Invalid owner ID");
            }
            
            List<Pet> pets = petService.getPetsByOwnerId(ownerId);
            return success("Pets found for owner", pets);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Failed to get pets: " + e.getMessage());
        }
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<ApiResponse<List<Pet>>> getPetsBySpecies(@PathVariable String species) {
        try {
            if (!isValidString(species)) {
                return badRequest("Species cannot be empty");
            }
            
            List<Pet> pets = petService.getPetsBySpecies(species);
            return success("Pets found for species: " + species, pets);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Failed to get pets: " + e.getMessage());
        }
    }

    @GetMapping("/breed/{breed}")
    public ResponseEntity<ApiResponse<List<Pet>>> getPetsByBreed(@PathVariable String breed) {
        try {
            if (!isValidString(breed)) {
                return badRequest("Breed cannot be empty");
            }
            
            List<Pet> pets = petService.getPetsByBreed(breed);
            return success("Pets found for breed: " + breed, pets);
                
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        } catch (Exception e) {
            return internalServerError("Failed to get pets: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pet>>> getAllPets() {
        try {
            List<Pet> pets = petService.getAllPets();
            return success("All pets retrieved successfully", pets);
                
        } catch (Exception e) {
            return internalServerError("Failed to get pets: " + e.getMessage());
        }
    }

    @PostMapping(value = "/{id}/photo", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Pet>> uploadPhoto(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return badRequest("File is empty");
            }

            Pet pet = petService.getPetById(id);
            String uploadsDir = "uploads";
            Path uploadPath = Paths.get(uploadsDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // In a real app, serve static /uploads via resource handler or CDN
            String photoUrl = "/" + uploadsDir + "/" + filename;
            pet.setPhotoUrl(photoUrl);
            Pet saved = petService.updatePet(pet, id);
            return success("Photo uploaded", saved);
        } catch (RuntimeException e) {
            return notFound("Pet not found");
        } catch (IOException e) {
            return internalServerError("Failed to save file: " + e.getMessage());
        } catch (Exception e) {
            return internalServerError("Upload failed: " + e.getMessage());
        }
    }
}