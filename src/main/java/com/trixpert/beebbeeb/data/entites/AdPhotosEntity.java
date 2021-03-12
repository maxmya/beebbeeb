package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "advertising_photo", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdPhotosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad_id")
    private long adId;

    @Column(name = "photo_id")
    private long photoId;

}
