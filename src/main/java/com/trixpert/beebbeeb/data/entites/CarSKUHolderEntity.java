package com.trixpert.beebbeeb.data.entites;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "car_sku", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarSKUHolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private String status;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarInstanceEntity carInstance;

}
