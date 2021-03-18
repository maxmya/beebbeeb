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
                VendorRegistrationRequest vendorRegistrationRequest,
                MultipartFile logoFile
                , String authHeader) throws IOException;

    ResponseWrapper<List<VendorDTO>> getAllVendors();

    ResponseWrapper<Boolean> deleteVendor(Long vendorId , String authHeader);

    ResponseWrapper<Boolean> updateVendor(VendorDTO vendorDTO , String authHeader);

}
