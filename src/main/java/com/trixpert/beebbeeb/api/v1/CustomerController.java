package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.request.EmployeeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.CustomerResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
import com.trixpert.beebbeeb.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Customer API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get Active Customer List")
    public ResponseEntity<ResponseWrapper<List<CustomerResponse>>> getActiveCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get InActive Customer List")
    public ResponseEntity<ResponseWrapper<List<CustomerResponse>>> getInActiveCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers(false));
    }

    @PutMapping("/update/{customerId}")
    @ApiOperation("Update an existing customer")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCustomer(
            @Valid @RequestBody CustomerRegistrationRequest customerRegistrationRequest,
            @PathVariable("customerId") long customerId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(customerService.updateCustomer(customerRegistrationRequest,
                customerId, authorizationHeader));
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(value = "/auth/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Add New Customer")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> mobileRegisterCustomer(
            @RequestBody CustomerMobileRegistrationRequest regRequest,
            HttpServletRequest request) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
         return ResponseEntity.ok(customerService.registerCustomer(regRequest, null));
    }


    @PutMapping("/delete/{customerId}")
    @ApiOperation("Remove Customer By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteCustomer(
            @PathVariable("customerId") long customerId
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(customerService.deleteCustomer(customerId, authorizationHeader));
    }

    @GetMapping("/get/{customerId}")
    @ApiOperation("Get Customer By ID")
    public ResponseEntity<ResponseWrapper<CustomerResponse>> getCustomer(
            @PathVariable("customerId") long customerId) {

        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }
}