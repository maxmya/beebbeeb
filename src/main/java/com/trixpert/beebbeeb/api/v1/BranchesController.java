package com.trixpert.beebbeeb.api.v1;


import com.trixpert.beebbeeb.data.request.BranchRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.services.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Branches API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/branches")
public class BranchesController {

    private final BranchService branchService;

    public BranchesController(BranchService branchService) {
        this.branchService = branchService;
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PostMapping("/{vendorID}/register")
    @ApiOperation("Add Branches List For Specific Vendor")
    public ResponseEntity<ResponseWrapper<Boolean>> registerBranchForVendor(
            @Valid @RequestBody BranchRegistrationRequest branchRegistrationRequest,
            @PathVariable("vendorID") Long vendorID,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(branchService.registerBranchForVendor(
                branchRegistrationRequest, vendorID, authorizationHeader));
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/{vendorId}/list/active")
    @ApiOperation("Get List Of All Active Branches For Specific Vendor")
    public ResponseEntity<ResponseWrapper<List<BranchDTO>>> getActiveBranchesForVendor(
            @PathVariable("vendorId") Long vendorId) {

        return ResponseEntity.ok(branchService.getAllBranchesForVendor(vendorId, true));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/{vendorId}/list/inactive")
    @ApiOperation("Get List Of All Inactive Branches For Specific Vendor")
    public ResponseEntity<ResponseWrapper<List<BranchDTO>>> getInactiveBranchesForVendor(
            @PathVariable("vendorId") Long vendorId) {

        return ResponseEntity.ok(branchService.getAllBranchesForVendor(vendorId, false));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("/update/{branchId}")
    @ApiOperation("Updating an existing branch for specific vendor")
    public ResponseEntity<ResponseWrapper<Boolean>> updateBranchForVendor(
            @Valid @RequestBody BranchRegistrationRequest branchRegistrationRequest,
            @PathVariable("branchId") long branchId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(branchService.updateBranchForVendor(branchRegistrationRequest,
                branchId, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("/delete/{branchID}")
    @ApiOperation("Deleting an existing branch for a specific vendor")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteBranchForVendor(
            @PathVariable Long branchID, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(branchService.deleteBranchForVendor(branchID, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/cars/list/{branchId}")
    @ApiOperation("Getting list of cars for specific branch")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> listCarsForBranch(
            @PathVariable("branchId") long branchId) {
        return ResponseEntity.ok(branchService.listCarsForBranch(branchId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/{vendorId}/get/{branchId}")
    @ApiOperation("Get Branch For Specific Vendor")
    public ResponseEntity<ResponseWrapper<BranchDTO>> getBranchForVendor(
            @PathVariable("vendorId") long vendorId,
            @PathVariable("branchId") long branchId) {

        return ResponseEntity.ok(branchService.getBranchForVendor(vendorId, branchId));
    }


}
