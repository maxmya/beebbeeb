package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import com.trixpert.beebbeeb.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Vendors API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {


    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @CrossOrigin(origins={"*"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/register", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Register Vendor With Email & Password")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> registerVendor(
            @Valid @RequestBody VendorRegistrationRequest vendorRegistrationRequest,
            HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.registerVendor(vendorRegistrationRequest, authorizationHeader));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list/active")
    @ApiOperation("Get All Active Vendors")
    public ResponseEntity<ResponseWrapper<List<VendorDTO>>> getAllActiveVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors(true));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list/inactive")
    @ApiOperation("Get All Inactive Vendors")
    public ResponseEntity<ResponseWrapper<List<VendorDTO>>> getAllInactiveVendors(){
        return ResponseEntity.ok(vendorService.getAllVendors(false));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{vendorId}")
    @ApiOperation("Updating an existing vendor with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateVendor(
            @Valid @RequestBody VendorRegistrationRequest vendorRegistrationRequest,
            @PathVariable("vendorId") long vendorId ,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(vendorService.updateVendor(vendorRegistrationRequest
                , vendorId, authorizationHeader));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{vendorId}")
    @ApiOperation("Deleting a vendor with vendor Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteVendor(
            @PathVariable("vendorId") Long vendorId,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(vendorService.deleteVendor(vendorId, authorizationHeader));

    }

    @GetMapping("/get/{vendorId}")
    @ApiOperation("Get vendor by Id")
    public ResponseEntity<ResponseWrapper<VendorDTO>> getVendor(
            @PathVariable("vendorId") long vendorId) {
        return ResponseEntity.ok(vendorService.getVendor(vendorId));
    }
}
