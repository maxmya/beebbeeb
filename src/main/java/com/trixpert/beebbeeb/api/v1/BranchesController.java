package com.trixpert.beebbeeb.api.v1;


import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.services.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{vendorID}/register")
    @ApiOperation("Add Branches List For Specific Vendor")
    public ResponseEntity<ResponseWrapper<Boolean>> registerBranchForVendor(
            @Valid @RequestBody BranchDTO branchDTO,
            @PathVariable("vendorID") Long vendorID ,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(branchService.registerBranchForVendor(branchDTO, vendorID , authorizationHeader));
    }


    @GetMapping("/{vendorID}/list")
    @ApiOperation("Get List Of All Branches For Specific Vendor")
    public ResponseEntity<ResponseWrapper<List<BranchDTO>>> getBranchesForVendor(
            @PathVariable("vendorID") Long vendorID ) {

        return ResponseEntity.ok(branchService.getAllBranchesForVendor(vendorID));
    }

    @PutMapping("/update")
    @ApiOperation("Updating an existing branch for specific vendor")
    public ResponseEntity<ResponseWrapper<Boolean>> updateBranchForVendor(
            @Valid @RequestBody BranchDTO branchDTO , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(branchService.updateBranchForVendor(branchDTO , authorizationHeader));
    }

    @PutMapping("/delete/{branchID}")
    @ApiOperation("Deleting an existing branch for a specific vendor")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteBranchForVendor(
            @PathVariable Long branchID, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(branchService.deleteBranchForVendor(branchID , authorizationHeader));
    }


    @GetMapping("/cars/list/{branchId}")
    @ApiOperation("Getting list of cars for specific branch")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> listCarsForBranch(@PathVariable("branchId") long branchId){
        return ResponseEntity.ok(branchService.listCarsForBranch(branchId));
    }

}
