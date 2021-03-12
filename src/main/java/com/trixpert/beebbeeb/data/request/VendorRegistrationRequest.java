package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRegistrationRequest extends RegistrationRequest{
    private String vendorName;
    private String mainAddress;
    private String gmName;
    private String gmPhone;
    private String accManagerName;
    private String accManagerPhone;
}
