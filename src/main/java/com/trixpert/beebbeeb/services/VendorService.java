package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.VendorDTO;

import java.util.List;

public interface VendorService {

    ResponseWrapper<Boolean> registerVendor(
                VendorRegistrationRequest vendorRegistrationRequest
                , String authHeader);

    ResponseWrapper<List<VendorDTO>> getAllVendors();

    ResponseWrapper<Boolean> deleteVendor(long vendorId , String authHeader);

    ResponseWrapper<Boolean> updateVendor(VendorRegistrationRequest vendorRegistrationRequest
            ,long vendorId, String authHeader);

    ResponseWrapper<VendorDTO> getVendor(long vendorId);

}
