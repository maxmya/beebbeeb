package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.PurchasingRequestRegistrationRequest;
import com.trixpert.beebbeeb.data.response.PurchasingRequestResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;

import java.util.List;

public interface PurchasingRequestService {

    ResponseWrapper<Boolean> registerPurchasingRequest(PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest, String authHeader);

    ResponseWrapper<Boolean> deletePurchasingRequest(long purchasingRequestId, String authHeader);

    ResponseWrapper<List<PurchasingRequestResponse>> listAllPurchasingRequests(boolean active);

    ResponseWrapper<Boolean> updatePurchasingRequest(PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest, long purchasingRequestId, String authHeader);

    ResponseWrapper<Boolean> updateStatusForPurchasingRequest(long purchasingRequestId, String status, String authHeader);

    ResponseWrapper<PurchasingRequestDTO> getPurchasingRequest(long purchasingRequestId);

    ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForVendor(boolean active, long vendorId);

    ResponseWrapper<PurchasingRequestDTO> getPurchasingRequestForVendor(long purchasingRequestId, long vendorId);


    ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCustomer(boolean active, long customerId);

    ResponseWrapper<PurchasingRequestDTO> getPurchasingRequestForCustomer(long purchasingRequestId, long customerId);


    ResponseWrapper<List<PurchasingRequestDTO>> listPurchasingRequestsForCar(boolean active, long carInstanceId);

    ResponseWrapper<PurchasingRequestDTO> getPurchasingRequestForCar(long purchasingRequestId, long carInstanceId);

}
