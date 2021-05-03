package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.PurchasingRequestResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;

import com.trixpert.beebbeeb.data.response.VendorDetailsResponse;

import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VendorService {

    ResponseWrapper<Boolean> registerVendor(
                VendorRegistrationRequest vendorRegistrationRequest,
                MultipartFile generalManagerIdDocumentFace,
                MultipartFile generalManagerIdDocumentBack,
                MultipartFile accountManagerIdDocumentFace,
                MultipartFile accountManagerIdDocumentBack,
                MultipartFile taxRecordDocument,
                MultipartFile commercialRegisterDocument,
                MultipartFile contractDocument,
                String authHeader);

    ResponseWrapper<List<VendorDTO>> getAllVendors(boolean active);

    ResponseWrapper<List<PurchasingRequestResponse>> listPurchasingRequestsForVendor(boolean active, String authHeader);

    ResponseWrapper<List<PurchasingRequestResponse>> listPurchasingRequestsForVendorByVendorId(boolean active, long vendorId);

    ResponseWrapper<List<CarInstanceDTO>> listCarsForVendor(long vendorId, boolean active);

    ResponseWrapper<Boolean> deleteVendor(long vendorId , String authHeader);

    ResponseWrapper<Boolean> updateVendor(VendorRegistrationRequest vendorRegistrationRequest
            ,long vendorId, String authHeader);

    ResponseWrapper<VendorDTO> getVendor(long vendorId);

    ResponseWrapper<Boolean> addVendorPhoto(long vendorId ,MultipartFile vendorPhoto);

    ResponseWrapper<Boolean> registerVendorWorkingDays(long vendorId ,String workingTimesRegistrationRequest);

    ResponseWrapper<VendorDetailsResponse> getVendorDetails(long vendorId);

}
