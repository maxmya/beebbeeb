package com.trixpert.beebbeeb.data.response;

import lombok.Data;

@Data
public class VendorResponse {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String workingHours;
    private LinkableImage logo;
}
