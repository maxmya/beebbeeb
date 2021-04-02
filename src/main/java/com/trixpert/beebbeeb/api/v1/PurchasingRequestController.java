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
    public ResponseEntity<ResponseWrapper<Boolean>> addPurchasingRequest(@RequestBody PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
                                                                         HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.registerPurchasingRequest(purchasingRequestRegistrationRequest, authorizationHeader));
    }

    @PutMapping("/delete/{purchasingRequestId}")
    @ApiOperation("Remove purchasing Request By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deletePurchasingRequest(@PathVariable("purchasingRequestId") Long purchasingRequestId,
                                                                            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(purchasingRequestService.deletePurchasingRequest(purchasingRequestId, authorizationHeader));
    }

    @GetMapping("/get/{purchasingRequestId}")
    @ApiOperation("Get purchasing By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequest(@PathVariable("purchasingRequestId") Long purchasingRequestId) {
        return ResponseEntity.ok(purchasingRequestService.getPurchasingRequest(purchasingRequestId));
    }


    @PutMapping("/update/{purchasingRequestId}")
    @ApiOperation("Update an existing purchasing Request ")
    public ResponseEntity<ResponseWrapper<Boolean>> updatePurchasingRequest(
            @RequestBody PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
            @PathVariable("purchasingRequestId") long purchasingRequestId,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.updatePurchasingRequest(purchasingRequestRegistrationRequest,
                purchasingRequestId, authorizationHeader));
    }

    @PutMapping("/update/{purchasingRequestId}/{status}")
    @ApiOperation("Update an existing purchasing Request ")
    public ResponseEntity<ResponseWrapper<Boolean>> updateStatusForPurchasingRequest(@PathVariable("purchasingRequestId") long purchasingRequestId,
                                                                                     @PathVariable("status") String status,
                                                                                     HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(purchasingRequestService.updateStatusForPurchasingRequest(purchasingRequestId, status, authorizationHeader));
    }

    @GetMapping("/get/{vendorId}/{purchasingRequestId}")
    @ApiOperation("Get purchasing for Vendor By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequestForVendor(@PathVariable("purchasingRequestId") long purchasingRequestId,
                                                                                               @PathVariable("vendorId") long vendorId) {

        return ResponseEntity.ok(purchasingRequestService.getPurchasingRequestForVendor(purchasingRequestId, vendorId));
    }

    @GetMapping("/get/{customerId}/{purchasingRequestId}")
    @ApiOperation("Get purchasing for customer By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequestForCustomer(@PathVariable("purchasingRequestId") long purchasingRequestId,
                                                                                                 @PathVariable("customerId") long customerId) {

        return ResponseEntity.ok(purchasingRequestService.getPurchasingRequestForCustomer(purchasingRequestId, customerId));
    }

    @GetMapping("/get/{carInstanceId}/{purchasingRequestId}")
    @ApiOperation("Get purchasing for Vendor By Id")
    public ResponseEntity<ResponseWrapper<PurchasingRequestDTO>> getPurchasingRequestForCar(@PathVariable("purchasingRequestId") long purchasingRequestId,
                                                                                            @PathVariable("carInstanceId") long carInstanceId) {

        return ResponseEntity.ok(purchasingRequestService.getPurchasingRequestForCar(purchasingRequestId, carInstanceId));
    }

    @GetMapping("/list/active/{vendorId}")
    @ApiOperation("Get all active Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequestsForVendor(@PathVariable("vendorId") long vendorId) {
        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForVendor(true, vendorId));
    }

    @GetMapping("/list/inactive/{vendorId}")
    @ApiOperation("Get all inactive Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllInActivePurchasingRequestsForVendor(@PathVariable("vendorId") long vendorId) {
        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForVendor(false, vendorId));
    }

    @GetMapping("/list/active/{customerId}")
    @ApiOperation("Get all active Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequestsForCustomer(@PathVariable("customerId") long customerId) {
        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCustomer(true, customerId));
    }

    @GetMapping("/list/inactive/{customerId}")
    @ApiOperation("Get all inactive Purchasing Requests List for Customer ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllInActivePurchasingRequestsForCustomer(@PathVariable("customerId") long customerId) {
        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCustomer(false, customerId));
    }

    @GetMapping("/list/active/{carInstanceId}")
    @ApiOperation("Get all active Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllActivePurchasingRequestsForCar(@PathVariable("carInstanceId") long carInstanceId) {
        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCar(true, carInstanceId));
    }

    @GetMapping("/list/inactive/{carInstanceId}")
    @ApiOperation("Get all inactive Purchasing Requests List for Vendor ")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestDTO>>>
    getAllInActivePurchasingRequestsForCar(@PathVariable("carInstanceId") long carInstanceId) {
        return ResponseEntity.ok(purchasingRequestService.listPurchasingRequestsForCar(false, carInstanceId));
    }
}
