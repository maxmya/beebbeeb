package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "photo",schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean interior;

    @Column(name = "photo_url")
    private String photoUrl;

    private String caption;

    private String description;

}
