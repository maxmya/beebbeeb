package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "model_photos", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelPhotosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_id")
    private long photoId;

    @Column(name = "model_id")
    private long modelId;

}
