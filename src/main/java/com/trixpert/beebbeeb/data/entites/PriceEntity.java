package com.trixpert.beebbeeb.data.entites;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "price", schema = "public")
@Data
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
