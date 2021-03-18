package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.AddressRegistrationRequest;
import com.trixpert.beebbeeb.data.request.ColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ColorDTO;
import com.trixpert.beebbeeb.services.AddressService;
import com.trixpert.beebbeeb.services.ColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"Address API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get all active Address List")
    public ResponseEntity<ResponseWrapper<List<AddressDTO>>> getAllActiveAddresses() {
        return ResponseEntity.ok(addressService.listAllAddresses(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get all in active  address List")
    public ResponseEntity<ResponseWrapper<List<AddressDTO>>> getAllInActiveAddresses() {
        return ResponseEntity.ok(addressService.listAllAddresses(false));
    }

    @PutMapping("/update/{addressId}")
    @ApiOperation("Update an existing address with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateAddress(
            @RequestPart(name = "body")AddressRegistrationRequest addressRegistrationRequest,
            @PathVariable("addressId") long addressId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(addressService.updateAddress(addressRegistrationRequest , addressId , authorizationHeader));
    }

    @PostMapping("/add")
    @ApiOperation("Add New  Address")
    public ResponseEntity<ResponseWrapper<Boolean>> addAddress(
            @RequestBody AddressRegistrationRequest addressRegistrationRequest,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(addressService.addAddress(addressRegistrationRequest , authorizationHeader));
    }

    @PutMapping("/delete/{addressId}")
    @ApiOperation("Remove address By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteAddress(
            @PathVariable("addressId") Long addressId,
            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(addressService.deleteAddress(addressId , authorizationHeader));
    }
    @GetMapping("/get/{addressId}")
    @ApiOperation("Get address By Id")
    public ResponseEntity<ResponseWrapper<AddressDTO>> getAddress(
            @PathVariable("addressId") Long addressId) {

        return ResponseEntity.ok(addressService.getAddress(addressId));
    }

    }
