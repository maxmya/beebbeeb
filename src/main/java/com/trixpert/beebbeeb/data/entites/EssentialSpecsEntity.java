package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "essential_specs", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EssentialSpecsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarEntity car;

    private String key;

    private String value;

    private boolean active;

}
