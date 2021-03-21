package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brand", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String origin;

    @Column(name = "logo_url")
    private String logoUrl;

    private String description;

    private boolean active;

    @OneToMany(mappedBy = "brand")
    private List<CarEntity> cars;


}
