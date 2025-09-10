package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.entity.HealthRecord;
import com.hieusrpingboot.petcare.repository.HealthRecordRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
@CrossOrigin(origins = "*")
public class HealthRecordController extends BaseController {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<HealthRecord>> create(@Valid @RequestBody HealthRecord body) {
        HealthRecord saved = healthRecordRepository.save(body);
        return success("Created", saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthRecord>> getById(@PathVariable Long id) {
        return healthRecordRepository.findById(id)
                .map(item -> success("OK", item))
                .orElseGet(() -> notFound("HealthRecord not found"));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<HealthRecord>>> listByPet(@PathVariable Long petId) {
        return success("OK", healthRecordRepository.findByPetIdOrderByVisitDateDesc(petId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthRecord>> update(@PathVariable Long id, @Valid @RequestBody HealthRecord body) {
        return healthRecordRepository.findById(id)
                .map(existing -> {
                    body.setId(id);
                    return success("Updated", healthRecordRepository.save(body));
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
}
