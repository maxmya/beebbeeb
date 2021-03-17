package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRegistrationRequest {
    private String title;
    private String fullAddress;
    private double longitude;
    private double latitude;
    private long customerId;
}
