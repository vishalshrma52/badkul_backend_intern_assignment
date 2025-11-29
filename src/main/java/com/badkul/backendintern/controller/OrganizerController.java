package com.badkul.backendintern.controller;

import com.badkul.backendintern.dto.*;
import com.badkul.backendintern.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService service;

    @PostMapping
    public ResponseEntity<OrganizerResponse> createOrganizer(@Valid @RequestBody OrganizerCreateRequest req) {
        OrganizerResponse res = service.createOrganizer(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerResponse> getOrganizer(@PathVariable Long id) {
        OrganizerResponse res = service.getOrganizer(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrganizerSearchLightDTO>> searchOrganizers(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,desc") String sort) {

        Sort sortObj = Sort.by("id").descending();
        if (sort != null) {
            String[] parts = sort.split(",");
            if (parts.length == 2) {
                sortObj = Sort.by(parts[0]);
                sortObj = parts[1].equalsIgnoreCase("desc") ? sortObj.descending() : sortObj.ascending();
            }
        }
        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<OrganizerSearchLightDTO> results = service.searchOrganizers(q, pageable);
        return ResponseEntity.ok(results);
    }
}
