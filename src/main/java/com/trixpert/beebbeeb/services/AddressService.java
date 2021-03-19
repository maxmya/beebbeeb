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
                                                     long addressId, String authHeader);

    ResponseWrapper<Boolean> deleteAddress(long addressId , String authHeader);

    ResponseWrapper<AddressDTO> getAddress(long addressId);

}
