package com.trixpert.beebbeeb.data.response;

import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import com.trixpert.beebbeeb.data.entites.PriceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchasingRequestMobileResponse {
    private long id;
    private String status;
    private String vendorName;
    private String customerName;
    private String carBrand;
    private String carModel;
    private PhotoEntity modelMainPhoto;
    private PriceEntity price;
}
