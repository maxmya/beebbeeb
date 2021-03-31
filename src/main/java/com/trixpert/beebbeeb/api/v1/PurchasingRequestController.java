package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.PurchasingRequestRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;
import com.trixpert.beebbeeb.services.PurchasingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"purchasingRequest API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/purchasingRequest")

public class PurchasingRequestController {
    private final PurchasingRequestService purchasingRequestService;

    public PurchasingRequestController(PurchasingRequestService purchasingRequestService) {
        this.purchasingRequestService = purchasingRequestService;
    }

    @GetMapping("/list/active")
    @ApiOperation("Get all active Purchasing Requests List")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequests() {
        return ResponseEntity.ok(purchasingRequestService.listAllPurchasingRequests(true));
    }

    @GetMapping("/list/Inactive")
    @ApiOperation("Get all Inactive Purchasing Requests List")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllIncActivePurchasingRequests() {
        return ResponseEntity.ok(purchasingRequestService.listAllPurchasingRequests(false));
    }

    @PostMapping("/add")
    @ApiOperation("Add New Purchasing Request")
    public ResponseEntity<ResponseWrapper<Boolean>> addPurchasingRequest(
            @RequestBody PurchasingRequestRegistrationRequest
                    purchasingRequestRegistrationRequest,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.registerPurchasingRequest(
                purchasingRequestRegistrationRequest, authorizationHeader));
    }

    @PutMapping("/delete/{purchasing_requestId}")
    @ApiOperation("Remove purchasing Request By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deletePurchasingRequest(
            @PathVariable("purchasing_requestId") Long purchasing_requestId,
            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.deletePurchasingRequest(
                purchasing_requestId, authorizationHeader));
    }

    @GetMapping("/get/{purchasing_requestId}")
    @ApiOperation("Get purchasing By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequest(
            @PathVariable("purchasing_requestId") Long purchasing_requestId) {

        return ResponseEntity.ok(purchasingRequestService.
                getPurchasingRequest(purchasing_requestId));
    }


    @PutMapping("/update/{purchasing_requestId}")
    @ApiOperation("Update an existing purchasing Request ")
    public ResponseEntity<ResponseWrapper<Boolean>> updatePurchasingRequest(
            @RequestBody PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
            @PathVariable("purchasing_requestId") long purchasing_requestId,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.updatePurchasingRequest(
                purchasingRequestRegistrationRequest,
                purchasing_requestId, authorizationHeader));
    }

    @PutMapping("/update/{purchasing_requestId}/{status}")
    @ApiOperation("Update an existing purchasing Request ")
    public ResponseEntity<ResponseWrapper<Boolean>> updateStatusForPurchasingRequest(
            @PathVariable("purchasing_requestId") long purchasing_requestId,
            @PathVariable("status") String status,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.updateStatusForPurchasingRequest(
                purchasing_requestId, status, authorizationHeader));
    }

    @GetMapping("/get/{vendorId}/{purchasing_requestId}")
    @ApiOperation("Get purchasing for Vendor By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequestForVendor(
            @PathVariable("purchasing_requestId") long purchasing_requestId,
            @PathVariable("vendorId") long vendorId) {

        return ResponseEntity.ok(purchasingRequestService.
                getPurchasingRequestForVendor(purchasing_requestId, vendorId));
    }

    @GetMapping("/get/{customerId}/{purchasing_requestId}")
    @ApiOperation("Get purchasing for customer By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequestForCustomer(
            @PathVariable("purchasing_requestId") long purchasing_requestId,
            @PathVariable("customerId") long customerId) {

        return ResponseEntity.ok(purchasingRequestService.
                getPurchasingRequestForCustomer(purchasing_requestId, customerId));
    }

    @GetMapping("/get/{car_instance_id}/{purchasing_requestId}")
    @ApiOperation("Get purchasing for Vendor By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequestForCar(
            @PathVariable("purchasing_requestId") long purchasing_requestId,
            @PathVariable("car_instance_id") long car_instance_id) {

        return ResponseEntity.ok(purchasingRequestService.
                getPurchasingRequestForCar(purchasing_requestId, car_instance_id));
    }

    @GetMapping("/list/active/{vendorId}")
    @ApiOperation("Get all active Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequestsForVendor(
            @PathVariable("vendorId") long vendorId) {

        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForVendor(
                true, vendorId));
    }

    @GetMapping("/list/inactive/{vendorId}")
    @ApiOperation("Get all inactive Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllInActivePurchasingRequestsForVendor(
            @PathVariable("vendorId") long vendorId) {

        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForVendor(
                false, vendorId));
    }

    @GetMapping("/list/active/{customerId}")
    @ApiOperation("Get all active Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequestsForCustomer(
            @PathVariable("customerId") long customerId) {

        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCustomer(
                true, customerId));
    }

    @GetMapping("/list/inactive/{customerId}")
    @ApiOperation("Get all inactive Purchasing Requests List for Customer ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllInActivePurchasingRequestsForCustomer(
            @PathVariable("customerId") long customerId) {

        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCustomer(
                false, customerId));
    }

    @GetMapping("/list/active/{car_instance_id}")
    @ApiOperation("Get all active Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequestsForCar(
            @PathVariable("car_instance_id") long car_instance_id) {

        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCar(
                true, car_instance_id));
    }

    @GetMapping("/list/inactive/{car_instance_id}")
    @ApiOperation("Get all inactive Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllInActivePurchasingRequestsForCar(
            @PathVariable("car_instance_id") long car_instance_id) {

        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCar(
                false, car_instance_id));
    }
}
