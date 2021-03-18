package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import com.trixpert.beebbeeb.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    @ApiOperation("Register Vendor With Email & Password")
    public ResponseEntity<ResponseWrapper<Boolean>> registerVendor(
            @Valid @RequestBody VendorRegistrationRequest vendorRegistrationRequest
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(vendorService.registerVendor(
                vendorRegistrationRequest, authorizationHeader));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    @ApiOperation("Get All Vendors")
    public ResponseEntity<ResponseWrapper<List<VendorDTO>>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    @ApiOperation("Updating an existing vendor with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateVendor(
            @Valid @RequestBody VendorDTO vendorDTO,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(vendorService.updateVendor(vendorDTO, authorizationHeader));
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
