package com.trixpert.beebbeeb.data.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "public")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "lng")
    private double longitude;

    @Column(name = "lat")
    private double latitude;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

}
