package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRegistrationRequest {
    private String title;

    @NotNull(message = "Address title cannot be null")
    @Size(min = 2, max = 50)
    private String fullAddress;

    @Pattern(message = "longitude must be a number", regexp = "^[0-9]*$")
    private double longitude;

    @Pattern(message = "latitude must be a number", regexp = "^[0-9]*$")
    private double latitude;

    @Pattern(message = "customerId must be a number", regexp = "^[0-9]*$")
    @NotNull(message = "Address customerId cannot be null")
    private long customerId;
}
