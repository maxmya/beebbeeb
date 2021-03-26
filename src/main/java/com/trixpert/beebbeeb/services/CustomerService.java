package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.response.CustomerResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;

import java.util.List;

public interface CustomerService {

    ResponseWrapper<Boolean> deleteCustomer(long customerId, String authHeader);

    ResponseWrapper<Boolean> updateCustomer(CustomerRegistrationRequest customerRegistrationRequest, long customerId, String authHeader);

    ResponseWrapper<List<CustomerResponse>> getAllCustomers(boolean active);

    ResponseWrapper<CustomerResponse> getCustomer(long customerId);
}
