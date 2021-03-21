package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInstanceRequest {
    private long vendorId;
    private long branchId;
    private long carId;
    private String originalPrice;
    private String vendorPrice;

}

