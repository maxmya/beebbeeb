package com.trixpert.beebbeeb.data.entites;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "price", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String amount;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarInstanceEntity car;

}
