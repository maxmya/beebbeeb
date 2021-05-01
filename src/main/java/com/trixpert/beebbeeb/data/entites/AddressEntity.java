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

    private String governorate;

    private String city;

    private String street;

    private String landmark;

    private String type;

    private String building;

    private String floor;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    private boolean main;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

}
