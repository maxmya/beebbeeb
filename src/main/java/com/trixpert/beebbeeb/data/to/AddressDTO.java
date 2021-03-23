package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private long id;
    private long customerId;
    private String title;
    private String fullAddress;
    private String governorate;
    private String city;
    private String street;
    private String landmark;
    private String type;
    private String building;
    private String floor;
    private String apartmentNumber;
    private double longitude;
    private double latitude;
    private boolean active;
    private boolean primary;
}
