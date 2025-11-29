package com.badkul.backendintern.dto;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizerResponse {
    private Long id;
    private String organizerCode;
    private String name;
    private String email;
    private String phone;
    private String businessType;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}

