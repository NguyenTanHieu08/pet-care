package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.HealthRecord;
import com.hieusrpingboot.petcare.enums.HealthRecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByPetId(Long petId);
    List<HealthRecord> findByVetId(Long vetId);
    List<HealthRecord> findByPetIdOrderByVisitDateDesc(Long petId);
    List<HealthRecord> findByPetIdAndRecordTypeOrderByVisitDateDesc(Long petId, HealthRecordType recordType);
    
    @Query("SELECT DISTINCT h.recordType FROM HealthRecord h WHERE h.petId = :petId")
    List<HealthRecordType> findDistinctRecordTypesByPetId(@Param("petId") Long petId);
}
