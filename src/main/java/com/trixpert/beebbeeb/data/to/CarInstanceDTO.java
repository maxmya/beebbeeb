package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarInstanceDTO {
    private long id;
    private String condition;
    private String originalPrice;
    private CarDTO car;
    private VendorDTO vendor;
    private BranchDTO branch;
}
