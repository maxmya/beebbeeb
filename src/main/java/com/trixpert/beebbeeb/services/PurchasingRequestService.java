package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.PurchasingRequestRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;

import java.util.List;

public interface PurchasingRequestService {

    ResponseWrapper<Boolean> registerPurchasingRequest(PurchasingRequestRegistrationRequest
                                                       purchasingRequestRegistrationRequest,
                                                       String authHeader);
    ResponseWrapper<Boolean> deletePurchasingRequest(long purchasingRequestId , String authHeader);

    ResponseWrapper<List<PurchasingRequestDTO>> listAllPurchasingRequests(boolean active);

    ResponseWrapper<Boolean> updatePurchasingRequest(PurchasingRequestRegistrationRequest
                                                     purchasingRequestRegistrationRequest,
                                                     long purchasingRequstId ,
                                                     String authHeader);
    ResponseWrapper<PurchasingRequestDTO> getPurchasingRequest(long purchasingRequestId);

    ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForVendor(long vendorId);

    ResponseWrapper<List<PurchasingRequestDTO>> getPurchasingRequestForVendor(long vendorId);


    ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCustomer(long vendorId);

    ResponseWrapper<List<PurchasingRequestDTO>> getPurchasingRequestForCustomer(long vendorId);


    ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCar(long vendorId);

    ResponseWrapper<List<PurchasingRequestDTO>> getPurchasingRequestForCar(long vendorId);

}
