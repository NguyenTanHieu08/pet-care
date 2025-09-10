package com.hieusrpingboot.petcare.entity;

import com.hieusrpingboot.petcare.enums.AdoptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "adoption_listings")
public class AdoptionListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Shelter ID is required")
    @Column(name = "shelter_id", nullable = false)
    private Long shelterId;

    @NotBlank(message = "Pet name is required")
    @Column(name = "pet_name", nullable = false)
    private String petName;

    @NotBlank(message = "Species is required")
    @Column(nullable = false)
    private String species;

    private String breed;

    @PositiveOrZero(message = "Age must be positive or zero")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    @Column(nullable = false)
    private AdoptionStatus status;

    @Column(name = "health_status")
    private String healthStatus;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", insertable = false, updatable = false)
    private Shelter shelter;

    // Constructors
    public AdoptionListing() {}

    public AdoptionListing(Long shelterId, String petName, String species, String breed, Integer age, AdoptionStatus status, String healthStatus) {
        this.shelterId = shelterId;
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.status = status;
        this.healthStatus = healthStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
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

    public AdoptionStatus getStatus() {
        return status;
    }

    public void setStatus(AdoptionStatus status) {
        this.status = status;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}
