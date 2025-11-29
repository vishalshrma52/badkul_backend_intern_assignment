package com.badkul.backendintern.exception;

import lombok.*;
import java.time.Instant;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ApiException {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String message;
    private String path;
}
