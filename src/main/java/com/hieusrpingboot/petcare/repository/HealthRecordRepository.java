package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByPetId(Long petId);
    List<HealthRecord> findByVetId(Long vetId);
    List<HealthRecord> findByPetIdOrderByVisitDateDesc(Long petId);
}
