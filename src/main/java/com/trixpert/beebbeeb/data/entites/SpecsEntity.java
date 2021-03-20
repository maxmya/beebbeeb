package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "specs", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarEntity car;

    @Column(name = "transmission_type")
    private String transmissionType;

    @Column(name = "transmission_gears")
    private String transmissionGears;

    @Column(name = "driver_airbag")
    private int driverAirbag;

    @Column(name = "break_abs")
    private int breakAbs;

    @Column(name = "rim_sports")
    private int rimSports;

    @Column(name = "rim_size")
    private int rimSize;
}
