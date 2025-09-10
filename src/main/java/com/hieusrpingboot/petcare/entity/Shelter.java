package com.hieusrpingboot.petcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Shelter name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Contact person is required")
    @Column(name = "contact_person", nullable = false)
    private String contactPerson;

    @Email(message = "Email should be valid")
    @Column(nullable = false)
    private String email;

    private String phone;

    private String address;

    // Relationships
    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AdoptionListing> adoptionListings = new ArrayList<>();

    // Constructors
    public Shelter() {}

    public Shelter(String name, String contactPerson, String email, String phone, String address) {
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AdoptionListing> getAdoptionListings() {
        return adoptionListings;
    }

    public void setAdoptionListings(List<AdoptionListing> adoptionListings) {
        this.adoptionListings = adoptionListings;
    }
}
