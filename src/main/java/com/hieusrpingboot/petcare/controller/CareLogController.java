package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.entity.CareLog;
import com.hieusrpingboot.petcare.enums.CareLogType;
import com.hieusrpingboot.petcare.repository.CareLogRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/care-logs")
@CrossOrigin(origins = "*")
public class CareLogController extends BaseController {

    @Autowired
    private CareLogRepository careLogRepository;

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<CareLog>>> list(@PathVariable Long petId) {
        return success("OK", careLogRepository.findByPetIdOrderByTimeDesc(petId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CareLog>> create(@Valid @RequestBody CareLog body) {
        if (body.getTime() == null) body.setTime(LocalDateTime.now());
        if (body.getType() == null) body.setType(CareLogType.OTHER);
        return success("Created", careLogRepository.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CareLog>> update(@PathVariable Long id, @Valid @RequestBody CareLog body) {
        body.setId(id);
        return success("Updated", careLogRepository.save(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        careLogRepository.deleteById(id);
        return success("Deleted", "OK");
    }
}





