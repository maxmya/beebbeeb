package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.request.EmployeeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.CustomerResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {

    ResponseWrapper<Boolean> registerCustomer(CustomerMobileRegistrationRequest registrationRequest , MultipartFile photoFile);

    ResponseWrapper<Boolean> deleteCustomer(long customerId, String authHeader);

    ResponseWrapper<Boolean> updateCustomer(CustomerRegistrationRequest customerRegistrationRequest ,
                                            long customerId, String authHeader);

    ResponseWrapper<List<CustomerResponse>> getAllCustomers(boolean active);

    ResponseWrapper<CustomerResponse> getCustomer(long customerId);
}
