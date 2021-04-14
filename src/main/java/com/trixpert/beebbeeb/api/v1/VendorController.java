package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.BrandRegisterRequest;
import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.request.WokringTimsRegistrationRequest;
import com.trixpert.beebbeeb.data.response.PurchasingRequestResponse;
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

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Register Vendor With Email & Password")
    public ResponseEntity<ResponseWrapper<Boolean>> registerVendor(
            @Valid @RequestBody VendorRegistrationRequest vendorRegistrationRequest,
            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.registerVendor(vendorRegistrationRequest, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/list/active")
    @ApiOperation("Get All Active Vendors")
    public ResponseEntity<ResponseWrapper<List<VendorDTO>>> getAllActiveVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors(true));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/list/inactive")
    @ApiOperation("Get All Inactive Vendors")
    public ResponseEntity<ResponseWrapper<List<VendorDTO>>> getAllInactiveVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors(false));
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/purchasing/list/active")
    @ApiOperation("Get All active orders for specific vendor")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestResponse>>> getAllActivePurchasingRequestForVendors(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.listPurchasingRequestsForVendor(true, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/purchasing/list/inactive")
    @ApiOperation("Get All inactive orders for specific vendor")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestResponse>>> getAllInactivePurchasingRequestForVendors(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.listPurchasingRequestsForVendor(false, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("/update/{vendorId}")
    @ApiOperation("Updating an existing vendor with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateVendor(
            @Valid @RequestBody VendorRegistrationRequest vendorRegistrationRequest,
            @PathVariable("vendorId") long vendorId,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(vendorService.updateVendor(vendorRegistrationRequest
                , vendorId, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("/{vendorId}")
    @ApiOperation("Deleting a vendor with vendor Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteVendor(
            @PathVariable("vendorId") Long vendorId,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(vendorService.deleteVendor(vendorId, authorizationHeader));

    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/get/{vendorId}")
    @ApiOperation("Get vendor by Id")
    public ResponseEntity<ResponseWrapper<VendorDTO>> getVendor(
            @PathVariable("vendorId") long vendorId) {
        return ResponseEntity.ok(vendorService.getVendor(vendorId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping(value = "/update/photo/{vendorId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("ADD Photo for vendor")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addVendorPhoto(
            @PathVariable("vendorId") long vendorId,
            @RequestParam(name = "file") MultipartFile logoFile,
            HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.addVendorPhoto(vendorId,logoFile));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping(value = "/update/working/days/{vendorId}")
    @ApiOperation("Register Vendor With Email & Password")
    public ResponseEntity<ResponseWrapper<Boolean>> registerVendorWorkingDays(
            @PathVariable("vendorId") long vendorId,
            @Valid @RequestBody String wokringTimesRegistrationRequest,
            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.registerVendorWorkingDays(vendorId,wokringTimesRegistrationRequest));
    }


}
