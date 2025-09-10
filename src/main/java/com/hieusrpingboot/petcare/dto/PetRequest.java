package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class PetRequest {
    
    @NotNull(message = "Owner ID is required")
    private Long ownerId;
    
    @NotBlank(message = "Pet name is required")
    private String name;
    
    @NotBlank(message = "Species is required")
    private String species;
    
    private String breed;
    
    @PositiveOrZero(message = "Age must be positive or zero")
    private Integer age;
    
    private Gender gender;
    
    private String photoUrl;
    
    // Constructors
    public PetRequest() {}
    
    public PetRequest(Long ownerId, String name, String species, String breed, Integer age, Gender gender, String photoUrl) {
        this.ownerId = ownerId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.photoUrl = photoUrl;
    }
    
    // Getters and Setters
    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

