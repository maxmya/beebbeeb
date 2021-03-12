package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private String title;

    private String action;

    private String description;

    private LocalDateTime timestamp;

    private String trace;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "lat")
    private double latitude;

    @Column(name = "lng")
    private double longitude;

}
