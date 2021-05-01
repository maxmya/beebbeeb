package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarInstanceDTO {
    private long id;
    private String condition;
    private long quantity;
    private boolean active;
    private boolean bestSeller;
    private CarDTO car;
    private VendorDTO vendor;
    private BranchDTO branch;
    private List<PriceDTO> prices;
    private List<CarSKUHolderDTO> skus;
}
