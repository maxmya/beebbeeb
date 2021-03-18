package com.trixpert.beebbeeb.services;


import com.trixpert.beebbeeb.data.request.AddressRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AddressDTO;

import java.util.List;

public interface AddressService {

    ResponseWrapper<Boolean> addAddress(AddressRegistrationRequest addressRegistrationRequest
            , String authHeader);

    ResponseWrapper<List<AddressDTO>> listAllAddresses(boolean active);


    ResponseWrapper<Boolean> updateAddress(AddressRegistrationRequest addressRegistrationRequest,
                                                     Long addressId, String authHeader);

    ResponseWrapper<Boolean> deleteAddress(Long addressId , String authHeader);

    ResponseWrapper<AddressDTO> getAddress(boolean active , long addressId);

}
