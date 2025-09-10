package com.hieusrpingboot.petcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "grooming_requests")
public class GroomingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "timeslot", nullable = false)
    private LocalDateTime timeslot;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELED, DONE

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public LocalDateTime getTimeslot() { return timeslot; }
    public void setTimeslot(LocalDateTime timeslot) { this.timeslot = timeslot; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


