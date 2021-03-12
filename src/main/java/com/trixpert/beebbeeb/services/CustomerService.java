package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CustomerDTO;

import java.util.List;

public interface CustomerService {

    ResponseWrapper<Boolean> registerCustomer(CustomerRegistrationRequest customerRegisterRequest
            , String authHeader);

    ResponseWrapper<Boolean> deleteCustomer(long customerId, String authHeader);

    ResponseWrapper<Boolean> updateCustomer(CustomerDTO customerDTO, String authHeader);

    ResponseWrapper<List<CustomerDTO>> getAllCustomers(boolean active);

    ResponseWrapper<CustomerDTO> getCustomer(long customerId);
}
