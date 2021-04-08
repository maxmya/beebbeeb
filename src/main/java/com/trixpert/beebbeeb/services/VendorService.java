package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface VendorService {

    ResponseWrapper<Boolean> registerVendor(
                VendorRegistrationRequest vendorRegistrationRequest, String authHeader);

    ResponseWrapper<List<VendorDTO>> getAllVendors(boolean active);

    ResponseWrapper<Boolean> deleteVendor(long vendorId , String authHeader);

    ResponseWrapper<Boolean> updateVendor(VendorRegistrationRequest vendorRegistrationRequest
            ,long vendorId, String authHeader);

    ResponseWrapper<VendorDTO> getVendor(long vendorId);

    ResponseWrapper<Boolean> addVendorPhoto(long vendorId ,MultipartFile vendorPhoto);

}
