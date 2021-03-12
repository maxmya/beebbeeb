package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AuditDTO {
    private Long id;
    private UserDTO user;
    private String title;
    private String action;
    private String description;
    private LocalDateTime timestamp;
    private String trace;
    private String clientId;
    private double latitude;
    private double longitude;
}
