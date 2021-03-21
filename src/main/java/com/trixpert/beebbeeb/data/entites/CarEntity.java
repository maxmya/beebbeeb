package com.trixpert.beebbeeb.data.entites;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "car", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "addition_date")
    private LocalDateTime additionDate;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private ModelEntity model;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private ColorEntity color;

    private boolean active;


    @OneToMany(mappedBy = "car")
    private List<CarInstanceEntity> instances;


    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "car_photos",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<PhotoEntity> photos;

}
