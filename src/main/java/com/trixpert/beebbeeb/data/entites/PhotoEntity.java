package com.trixpert.beebbeeb.data.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "photo", schema = "public")
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

    @Column(name = "main_photo")
    private boolean mainPhoto;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "model_photos",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<ModelEntity> models;

}
