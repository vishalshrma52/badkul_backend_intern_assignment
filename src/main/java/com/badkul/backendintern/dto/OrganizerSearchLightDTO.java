package com.badkul.backendintern.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizerSearchLightDTO {
    private Long id;
    private String organizerCode;
    private String name;
    private String email;
    private String phone;
}

