package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
import com.trixpert.beebbeeb.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ResponseWrapper<List<CustomerDTO>>> getActiveCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get InActive Customer List")
    public ResponseEntity<ResponseWrapper<List<CustomerDTO>>> getInActiveCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers(false));
    }

    @PutMapping("/update")
    @ApiOperation("Update an existing customer")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCustomer(
            @RequestPart(name = "body") CustomerDTO customerDTO, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(customerService.updateCustomer(customerDTO, authorizationHeader));
    }

    @PostMapping("/auth/register")
    @ApiOperation("Add New Customer")
    public ResponseEntity<ResponseWrapper<Boolean>> addCustomer(
            @RequestPart(name = "body") CustomerRegistrationRequest customerRegisterRequest
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(customerService.registerCustomer(customerRegisterRequest
                , authorizationHeader));
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
    public ResponseEntity<ResponseWrapper<CustomerDTO>> getCustomer(
            @PathVariable("customerId") long customerId) {

        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }
}