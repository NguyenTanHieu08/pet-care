package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.GroomingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroomingRequestRepository extends JpaRepository<GroomingRequest, Long> {
    List<GroomingRequest> findByOwnerId(Long ownerId);
    List<GroomingRequest> findByOwnerIdOrderByTimeslotDesc(Long ownerId);
}