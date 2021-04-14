package com.trixpert.beebbeeb.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarDetailsResponse {
    private long id;
    private String carName;
    private String originalPrice;
    private String vendorPrice;
    private String colorFamily;
    private String modelYear;
    private String brandLogo;
    private String brandName;
    private String carType;
    private String carCategory;
    private CommercialColorResponse commercialColor;
    private List<LinkableImage> interiorImages;
    private List<LinkableImage> exteriorImages;
    private LinkableImage mainPhoto;
    private Map<String, String> mainSpecs;
    private Map<String, String> extraSpecs;
    private VendorResponse vendor;
    private List<BranchResponse> branches;
    private String carBrochureURL;
}
