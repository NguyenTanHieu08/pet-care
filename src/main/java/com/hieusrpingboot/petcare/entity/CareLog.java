package com.hieusrpingboot.petcare.entity;

import com.hieusrpingboot.petcare.enums.CareLogType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "care_logs")
public class CareLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareLogType type;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "log_time", nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", insertable = false, updatable = false)
    private Pet pet;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }
    public CareLogType getType() { return type; }
    public void setType(CareLogType type) { this.type = type; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
}



