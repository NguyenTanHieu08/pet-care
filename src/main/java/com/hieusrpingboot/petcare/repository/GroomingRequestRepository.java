package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.GroomingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GroomingRequestRepository extends JpaRepository<GroomingRequest, Long> {
    List<GroomingRequest> findByOwnerIdOrderByTimeslotDesc(Long ownerId);
    List<GroomingRequest> findByPetIdOrderByTimeslotDesc(Long petId);
    List<GroomingRequest> findByOwnerIdAndTimeslotBetween(Long ownerId, LocalDateTime start, LocalDateTime end);
}





