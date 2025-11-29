package com.badkul.backendintern.service;

import com.badkul.backendintern.dto.*;
import com.badkul.backendintern.entity.Organizer;
import com.badkul.backendintern.exception.*;
import com.badkul.backendintern.repository.OrganizerRepository;
import com.badkul.backendintern.util.OrganizerCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository repo;
    private final OrganizerCodeGenerator codeGen;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository repo, OrganizerCodeGenerator codeGen) {
        this.repo = repo;
        this.codeGen = codeGen;
    }

    @Override
    public OrganizerResponse createOrganizer(OrganizerCreateRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        if (repo.existsByPhone(req.getPhone())) {
            throw new ConflictException("Phone already exists");
        }

        // generate organizer code based on last saved code
        String lastCode = repo.findLatestOrganizerCodeNative();
        String code = codeGen.nextCode(lastCode);

        Organizer org = Organizer.builder()
                .organizerCode(code)
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .businessType(req.getBusinessType())
                .status(Organizer.Status.ACTIVE)
                .build();

        Organizer saved = repo.save(org);

        return mapToResponse(saved);
    }

    @Override
    public OrganizerResponse getOrganizer(Long id) {
        Organizer org = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + id));
        return mapToResponse(org);
    }

    @Override
    public Page<OrganizerSearchLightDTO> searchOrganizers(String q, Pageable pageable) {
        Page<Organizer> page = repo.searchByNameEmailPhone(q == null ? "" : q, pageable);
        return page.map(this::mapToLightDTO);
    }

    private OrganizerResponse mapToResponse(Organizer o) {
        return OrganizerResponse.builder()
                .id(o.getId())
                .organizerCode(o.getOrganizerCode())
                .name(o.getName())
                .email(o.getEmail())
                .phone(o.getPhone())
                .businessType(o.getBusinessType())
                .status(o.getStatus().name())
                .createdAt(o.getCreatedAt())
                .updatedAt(o.getUpdatedAt())
                .build();
    }

    private OrganizerSearchLightDTO mapToLightDTO(Organizer o) {
        return OrganizerSearchLightDTO.builder()
                .id(o.getId())
                .organizerCode(o.getOrganizerCode())
                .name(o.getName())
                .email(o.getEmail())
                .phone(o.getPhone())
                .build();
    }
}
