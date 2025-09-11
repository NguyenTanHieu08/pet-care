package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.dto.HealthRecordRequest;
import com.hieusrpingboot.petcare.dto.HealthRecordResponse;
import com.hieusrpingboot.petcare.dto.HealthRecordTimelineResponse;
import com.hieusrpingboot.petcare.entity.HealthRecord;
import com.hieusrpingboot.petcare.enums.HealthRecordType;
import com.hieusrpingboot.petcare.repository.HealthRecordRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/health-records")
@CrossOrigin(origins = "*")
public class HealthRecordController extends BaseController {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<HealthRecordResponse>> create(@Valid @RequestBody HealthRecordRequest request) {
        HealthRecord healthRecord = convertToEntity(request);
        HealthRecord saved = healthRecordRepository.save(healthRecord);
        HealthRecordResponse response = convertToResponse(saved);
        return success("Created", response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthRecordResponse>> getById(@PathVariable Long id) {
        return healthRecordRepository.findById(id)
                .map(item -> success("OK", convertToResponse(item)))
                .orElseGet(() -> notFound("HealthRecord not found"));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<HealthRecordResponse>>> listByPet(@PathVariable Long petId) {
        List<HealthRecord> records = healthRecordRepository.findByPetIdOrderByVisitDateDesc(petId);
        List<HealthRecordResponse> responses = records.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return success("OK", responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthRecordResponse>> update(@PathVariable Long id, @Valid @RequestBody HealthRecordRequest request) {
        return healthRecordRepository.findById(id)
                .map(existing -> {
                    HealthRecord updated = convertToEntity(request);
                    updated.setId(id);
                    HealthRecord saved = healthRecordRepository.save(updated);
                    return success("Updated", convertToResponse(saved));
                })
                .orElseGet(() -> notFound("HealthRecord not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        if (!healthRecordRepository.existsById(id)) {
            return notFound("HealthRecord not found");
        }
        healthRecordRepository.deleteById(id);
        return success("Deleted", "OK");
    }

    // Timeline view endpoint
    @GetMapping("/pet/{petId}/timeline")
    public ResponseEntity<ApiResponse<List<HealthRecordTimelineResponse>>> getTimeline(@PathVariable Long petId) {
        List<HealthRecord> records = healthRecordRepository.findByPetIdOrderByVisitDateDesc(petId);
        List<HealthRecordTimelineResponse> timeline = buildTimeline(records);
        return success("OK", timeline);
    }

    // Filter by record type
    @GetMapping("/pet/{petId}/filter")
    public ResponseEntity<ApiResponse<List<HealthRecordResponse>>> filterByType(
            @PathVariable Long petId,
            @RequestParam HealthRecordType recordType) {
        List<HealthRecord> records = healthRecordRepository.findByPetIdAndRecordTypeOrderByVisitDateDesc(petId, recordType);
        List<HealthRecordResponse> responses = records.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return success("OK", responses);
    }

    // Get all record types for a pet
    @GetMapping("/pet/{petId}/types")
    public ResponseEntity<ApiResponse<List<HealthRecordType>>> getRecordTypes(@PathVariable Long petId) {
        List<HealthRecordType> types = healthRecordRepository.findDistinctRecordTypesByPetId(petId);
        return success("OK", types);
    }

    // Helper methods
    private HealthRecord convertToEntity(HealthRecordRequest request) {
        HealthRecord entity = new HealthRecord();
        entity.setPetId(request.getPetId());
        entity.setVetId(request.getVetId());
        entity.setVisitDate(request.getVisitDate());
        entity.setRecordType(request.getRecordType());
        entity.setDiagnosis(request.getDiagnosis());
        entity.setTreatment(request.getTreatment());
        entity.setVaccineType(request.getVaccineType());
        entity.setVaccineBatch(request.getVaccineBatch());
        entity.setNextVaccinationDate(request.getNextVaccinationDate());
        entity.setAllergyType(request.getAllergyType());
        entity.setAllergyDescription(request.getAllergyDescription());
        entity.setMedicationName(request.getMedicationName());
        entity.setMedicationDosage(request.getMedicationDosage());
        entity.setMedicationFrequency(request.getMedicationFrequency());
        entity.setMedicationDuration(request.getMedicationDuration());
        entity.setDoctorName(request.getDoctorName());
        entity.setClinicName(request.getClinicName());
        entity.setNotes(request.getNotes());
        entity.setAttachments(request.getAttachments());
        return entity;
    }

    private HealthRecordResponse convertToResponse(HealthRecord entity) {
        HealthRecordResponse response = new HealthRecordResponse();
        response.setId(entity.getId());
        response.setPetId(entity.getPetId());
        response.setVetId(entity.getVetId());
        response.setVisitDate(entity.getVisitDate());
        response.setRecordType(entity.getRecordType());
        response.setDiagnosis(entity.getDiagnosis());
        response.setTreatment(entity.getTreatment());
        response.setVaccineType(entity.getVaccineType());
        response.setVaccineBatch(entity.getVaccineBatch());
        response.setNextVaccinationDate(entity.getNextVaccinationDate());
        response.setAllergyType(entity.getAllergyType());
        response.setAllergyDescription(entity.getAllergyDescription());
        response.setMedicationName(entity.getMedicationName());
        response.setMedicationDosage(entity.getMedicationDosage());
        response.setMedicationFrequency(entity.getMedicationFrequency());
        response.setMedicationDuration(entity.getMedicationDuration());
        response.setDoctorName(entity.getDoctorName());
        response.setClinicName(entity.getClinicName());
        response.setNotes(entity.getNotes());
        response.setAttachments(entity.getAttachments());

        // Set additional info
        if (entity.getPet() != null) {
            response.setPetName(entity.getPet().getName());
        }
        if (entity.getVet() != null) {
            response.setVetName(entity.getVet().getName());
            response.setVetEmail(entity.getVet().getEmail());
        }

        return response;
    }

    private List<HealthRecordTimelineResponse> buildTimeline(List<HealthRecord> records) {
        Map<String, List<HealthRecordTimelineResponse.HealthRecordTimelineItem>> groupedByDate = new LinkedHashMap<>();
        
        for (HealthRecord record : records) {
            String date = record.getVisitDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            HealthRecordTimelineResponse.HealthRecordTimelineItem item = 
                new HealthRecordTimelineResponse.HealthRecordTimelineItem(
                    record.getId(),
                    record.getVisitDate(),
                    record.getRecordType(),
                    getTitleForRecord(record),
                    getDescriptionForRecord(record),
                    record.getDoctorName(),
                    record.getClinicName(),
                    getIconForRecordType(record.getRecordType())
                );
            
            groupedByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(item);
        }
        
        return groupedByDate.entrySet().stream()
                .map(entry -> new HealthRecordTimelineResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private String getTitleForRecord(HealthRecord record) {
        switch (record.getRecordType()) {
            case VACCINATION:
                return "Tiêm chủng: " + (record.getVaccineType() != null ? record.getVaccineType() : "Không xác định");
            case ALLERGY:
                return "Dị ứng: " + (record.getAllergyType() != null ? record.getAllergyType() : "Không xác định");
            case DISEASE:
                return "Chẩn đoán: " + record.getDiagnosis();
            case TREATMENT:
                return "Điều trị: " + (record.getMedicationName() != null ? record.getMedicationName() : "Không xác định");
            case MEDICATION:
                return "Thuốc: " + (record.getMedicationName() != null ? record.getMedicationName() : "Không xác định");
            case CHECKUP:
                return "Khám sức khỏe";
            default:
                return record.getDiagnosis();
        }
    }

    private String getDescriptionForRecord(HealthRecord record) {
        StringBuilder desc = new StringBuilder();
        if (record.getDiagnosis() != null) {
            desc.append(record.getDiagnosis());
        }
        if (record.getTreatment() != null && !record.getTreatment().isEmpty()) {
            if (desc.length() > 0) desc.append(" - ");
            desc.append(record.getTreatment());
        }
        if (record.getNotes() != null && !record.getNotes().isEmpty()) {
            if (desc.length() > 0) desc.append(" - ");
            desc.append(record.getNotes());
        }
        return desc.toString();
    }

    private String getIconForRecordType(HealthRecordType type) {
        switch (type) {
            case VACCINATION:
                return "💉";
            case ALLERGY:
                return "⚠️";
            case DISEASE:
                return "🏥";
            case TREATMENT:
                return "💊";
            case MEDICATION:
                return "💊";
            case CHECKUP:
                return "🩺";
            default:
                return "📋";
        }
    }
}

