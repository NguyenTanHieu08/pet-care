package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Appointment;
import com.hieusrpingboot.petcare.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long petId);
    List<Appointment> findByOwnerId(Long ownerId);
    List<Appointment> findByVetId(Long vetId);
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findByVetIdAndAppointmentTimeBetween(Long vetId, LocalDateTime start, LocalDateTime end);
}
