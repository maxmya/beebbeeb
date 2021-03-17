package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.request.EmployeeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CustomerDTO;

import java.util.List;

public interface CustomerService {

    ResponseWrapper<Boolean> registerCustomer(CustomerMobileRegistrationRequest registrationRequest);

    ResponseWrapper<Boolean> deleteCustomer(long customerId, String authHeader);

    ResponseWrapper<Boolean> updateCustomer(CustomerRegistrationRequest customerRegistrationRequest ,
                                            long customerId, String authHeader);

    ResponseWrapper<List<CustomerDTO>> getAllCustomers(boolean active);

    ResponseWrapper<CustomerDTO> getCustomer(long customerId);
}
