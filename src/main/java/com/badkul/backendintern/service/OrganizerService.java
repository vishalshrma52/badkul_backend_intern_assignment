package com.badkul.backendintern.service;

import com.badkul.backendintern.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizerService {
    OrganizerResponse createOrganizer(OrganizerCreateRequest req);
    OrganizerResponse getOrganizer(Long id);
    Page<OrganizerSearchLightDTO> searchOrganizers(String q, Pageable pageable);
}
