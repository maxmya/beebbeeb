package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRegistrationRequest extends RegistrationRequest{
    @NotNull(message = "Vendor name can't be null")
    @Size(min = 2, max = 50 , message = "vendor name is between 2 and 50 char")
    private String vendorName;

    @NotNull(message = "vendor address can't be null")
    private String mainAddress;

    private String gmName;
    private String gmPhone;
    private String accManagerName;
    private String accManagerPhone;
}
