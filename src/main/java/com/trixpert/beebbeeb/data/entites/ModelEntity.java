package com.trixpert.beebbeeb.data.entites;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "model", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private BrandEntity brand;

    private String name;

    @Column(name = "name_en")
    private String englishName;

    private String year;

    private boolean active;

    @OneToMany(mappedBy = "model")
    private List<CarEntity> cars;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "model_photos",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<PhotoEntity> photos;
}
