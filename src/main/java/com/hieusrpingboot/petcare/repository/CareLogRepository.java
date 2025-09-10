package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.CareLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CareLogRepository extends JpaRepository<CareLog, Long> {
    List<CareLog> findByPetIdOrderByTimeDesc(Long petId);
    List<CareLog> findByPetIdAndTimeBetweenOrderByTimeDesc(Long petId, LocalDateTime start, LocalDateTime end);
}



