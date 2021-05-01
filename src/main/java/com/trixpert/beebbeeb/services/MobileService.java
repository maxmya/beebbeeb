package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.AddressDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MobileService {

    ResponseWrapper<CustomerProfileResponse> profileResponse(HttpServletRequest request);

    ResponseWrapper<List<AddressDTO>> getListOfAddresses(HttpServletRequest request);

    ResponseWrapper<Boolean> saveAddress(AddressDTO address);

    ResponseWrapper<List<CarItemResponse>> listCars(String page, String size, String query);

    ResponseWrapper<OtpResponse> registerUser(CustomerMobileRegistrationRequest customerRegisterRequest);

    ResponseWrapper<Boolean> verifyUser(String phone);

    ResponseWrapper<MobileHomeResponse> getMobileHome();

    ResponseWrapper<CarDetailsResponse> getCarDetails(long carId);

    ResponseWrapper<List<PurchasingRequestMobileResponse>> listPurchasingRequestForCustomer(boolean active,
                                                                                            String authHeader);
}
