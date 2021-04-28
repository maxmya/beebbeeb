package com.trixpert.beebbeeb.data.response;

import com.trixpert.beebbeeb.data.to.BrandDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String workingHours;
    private LinkableImage logo;
    private List<BrandDTO> brandsAgent;
    private List<BrandDTO> brandsDistributor;
    private List<CarItemResponse> latestCars;
    private boolean importer;
    private boolean homeDelivery;
}
