package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.entity.GroomingRequest;
import com.hieusrpingboot.petcare.repository.GroomingRequestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services/grooming")
@CrossOrigin(origins = "*")
public class GroomingController extends BaseController {

    @Autowired
    private GroomingRequestRepository groomingRequestRepository;

    @PostMapping("/requests")
    public ResponseEntity<ApiResponse<GroomingRequest>> create(@Valid @RequestBody GroomingRequest body) {
        body.setStatus("PENDING");
        return success("Created", groomingRequestRepository.save(body));
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<GroomingRequest>>> listByOwner(@RequestParam Long ownerId) {
        return success("OK", groomingRequestRepository.findByOwnerIdOrderByTimeslotDesc(ownerId));
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<ApiResponse<GroomingRequest>> update(@PathVariable Long id, @Valid @RequestBody GroomingRequest body) {
        body.setId(id);
        return success("Updated", groomingRequestRepository.save(body));
    }
}



