package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.*;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"All Mobile APIs"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/mobile")
public class MobileController {

    private final MobileService mobileService;
    private final PurchasingRequestService purchasingRequestService;
    private final LoanService loanService;
    private final AddressService addressService;
    private final CustomerService customerService;
    private final CountingService countingService;
    private final VendorService vendorService;
    private final CarInstanceService carInstanceService;

    public MobileController(MobileService mobileService,
                            PurchasingRequestService purchasingRequestService,
                            CustomerService customerService,
                            LoanService loanService,
                            AddressService addressService,
                            CountingService countingService, VendorService vendorService, CarInstanceService carInstanceService) {

        this.mobileService = mobileService;
        this.purchasingRequestService = purchasingRequestService;
        this.loanService = loanService;
        this.addressService = addressService;
        this.customerService = customerService;
        this.countingService = countingService;
        this.vendorService = vendorService;
        this.carInstanceService = carInstanceService;
    }

    @PostMapping(value = "/loan/request", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Add Loan Form , side1 = file , side2 = file , body = json body of LoanRegistrationRequest Details Below")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addLoan(@RequestParam(name = "side1") MultipartFile photoSide1,
                                                            @RequestParam(name = "side2") MultipartFile photoSide2,
                                                            @Valid @RequestParam(name = "body") String regRequest,
                                                            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        LoanRegistrationRequest loanRegistrationRequest = objectMapper.readValue(regRequest, LoanRegistrationRequest.class);
        return ResponseEntity.ok(loanService.registerLoan(loanRegistrationRequest, photoSide1, photoSide2, authorizationHeader));
    }

    @PostMapping("/purchasing/request")
    @ApiOperation("Add New Purchasing Request")
    public ResponseEntity<ResponseWrapper<Boolean>> addPurchasingRequest(
            @RequestBody PurchasingRequestRegistrationRequest purchasingRequestRegistrationRequest,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(purchasingRequestService.registerPurchasingRequest(purchasingRequestRegistrationRequest, authorizationHeader));
    }

    @GetMapping("/me")
    @ApiOperation("Get Customer Profile")
    public ResponseEntity<ResponseWrapper<CustomerProfileResponse>> profileResponse(HttpServletRequest request) {
        return ResponseEntity.ok(mobileService.profileResponse(request));
    }

    @GetMapping("/address/list")
    @ApiOperation("Get Customer List Of Addresses")
    public ResponseEntity<ResponseWrapper<List<AddressDTO>>> getListOfAddresses(HttpServletRequest request) {
        return ResponseEntity.ok(mobileService.getListOfAddresses(request));
    }

    @GetMapping("/purchasingRequest/status/{purchasingRequestId}")
    public ResponseEntity<ResponseWrapper<PurchasingRequestMobileResponse>> getStatusForPurchasingRequest(
            @PathVariable("purchasingRequestId") long purchasingRequestId) {
        return ResponseEntity.ok(purchasingRequestService.getPurchasingRequestStatus(purchasingRequestId));
    }

    @PostMapping("/address/add")
    @ApiOperation("Add Customer Address")
    public ResponseEntity<ResponseWrapper<Boolean>> saveAddress(@RequestBody AddressDTO address) {
        return ResponseEntity.ok(mobileService.saveAddress(address));
    }

    @PutMapping("/update/{addressId}")
    @ApiOperation("Update an existing address with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateAddress(
            @Valid @RequestBody AddressDTO address,
            @PathVariable("addressId") long addressId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(addressService.updateAddress(address,
                addressId, authorizationHeader));
    }

    @PutMapping("/delete/{addressId}")
    @ApiOperation("Remove address By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteAddress(@PathVariable("addressId") Long addressId,
                                                                  HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(addressService.deleteAddress(addressId, authorizationHeader));
    }

    @GetMapping("/list/cars")
    @ApiOperation("Get List Of Cars")
    public ResponseEntity<ResponseWrapper<List<CarItemResponse>>> listCars(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "query", required = false) String query) {
        return ResponseEntity.ok(mobileService.listCars(page, size, query));
    }


    @PostMapping("/register/customer")
    @ApiOperation("Register New Customer")
    public ResponseEntity<ResponseWrapper<OtpResponse>> registerUser(@RequestBody CustomerMobileRegistrationRequest customerRegisterRequest) {
        return ResponseEntity.ok(mobileService.registerUser(customerRegisterRequest));
    }

    @PostMapping("/register/verify/{phone}")
    @ApiOperation("Verify Customer Registration")
    public ResponseEntity<ResponseWrapper<Boolean>> verifyUser(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(mobileService.verifyUser(phone));
    }


    @GetMapping("/home")
    @ApiOperation("Get Home Screen Data")
    public ResponseEntity<ResponseWrapper<MobileHomeResponse>> getMobileHome() {
        return ResponseEntity.ok(mobileService.getMobileHome());
    }

    @GetMapping("/car/details/{carId}")
    @ApiOperation("Get Car Details")
    public ResponseEntity<ResponseWrapper<CarDetailsResponse>> getCarDetails(@PathVariable("carId") long carId) {
        return ResponseEntity.ok(mobileService.getCarDetails(carId));
    }

    @PutMapping("/me/update/{customerId}")
    @ApiOperation("Update an existing customer")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCustomer(
            @Valid @RequestBody CustomerRegistrationRequest customerRegistrationRequest,
            @PathVariable("customerId") long customerId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(customerService.updateCustomer(customerRegistrationRequest,
                customerId, authorizationHeader));
    }

    @GetMapping("/counts")
    public ResponseEntity<ResponseWrapper<FilterCountingResponse>> countList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "color", required = false) String color
    ) {
        return ResponseEntity.ok(countingService.countPure(type, model, brand, color));
    }

    @GetMapping("/cars/filter")
    public ResponseEntity<ResponseWrapper<FilterCountingResponse>> getFilterResult(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "vendor", required = false) String vendor,
            @RequestParam(value = "priceFrom", required = false) String from,
            @RequestParam(value = "priceTo", required = false) String to
    ) {
        return ResponseEntity.ok(countingService.countPure(type, model, brand, color));
    }

    @GetMapping("/customer/profile/score/{customerId}")
    @ApiOperation("Get Customer Profile Score")
    public ResponseEntity<ResponseWrapper<ProfileScoreResponse>> getProfileScore(@PathVariable("customerId") long customerId) {
        return ResponseEntity.ok(customerService.getProfileScore(customerId));
    }

    @GetMapping("/vendor/details/{vendorId}")
    @ApiOperation("Get Vendor Details")
    public ResponseEntity<ResponseWrapper<VendorDetailsResponse>> getVendorDetails(@PathVariable("vendorId") long vendorId){
        return ResponseEntity.ok(vendorService.getVendorDetails(vendorId));
    }

    @GetMapping("/customer/purchasing/list/active")
    @ApiOperation("Get all active customer's purchasing requests")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestMobileResponse>>> listActivePurchasingRequestForCustomer(
            HttpServletRequest request
    ){
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(mobileService.listPurchasingRequestForCustomer(true, authorizationHeader));
    }

    @GetMapping("/customer/purchasing/list/inactive")
    @ApiOperation("Get all inactive customer's purchasing requests")
    public ResponseEntity<ResponseWrapper<List<PurchasingRequestMobileResponse>>> listInactivePurchasingRequestForCustomer(
            HttpServletRequest request
    ){
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(mobileService.listPurchasingRequestForCustomer(false, authorizationHeader));
    }

    @GetMapping("/branches/{branchId}")
    @ApiOperation("Get Branch Details")
    public ResponseEntity<ResponseWrapper<BranchResponse>> getBranchDetails(@PathVariable("branchId")long branchId){
        return ResponseEntity.ok(mobileService.getBranchDetails(branchId));
    }

    @PostMapping("/review/vendor/{vendorId}")
    @ApiOperation("Customer Add Review For Vendor ")
    public ResponseEntity<ResponseWrapper<Boolean>> addReviewForVendor(@PathVariable("vendorId") long vendorId, ReviewRegistrationRequest reviewRegistrationRequest) {
        return ResponseEntity.ok(vendorService.addReviewForVendor(vendorId,reviewRegistrationRequest));
    }


    @PostMapping("/review/carInstance/{carInatnceId}")
    @ApiOperation("Customer Add Review For Car")
    public ResponseEntity<ResponseWrapper<Boolean>> addReviewForCarInstance(@PathVariable("carInatnceId") long carInstanceId, ReviewRegistrationRequest reviewRegistrationRequest) {
        return ResponseEntity.ok(carInstanceService.addReviewForCarInstance(carInstanceId,reviewRegistrationRequest));
    }

    @GetMapping("/review/carInstance/{carInatnceId}")
    @ApiOperation("Get User Review For Car By User Token")
    public ResponseEntity<ResponseWrapper<ReviewResponse>> getUserReviewForCarInstance(@PathVariable("carInatnceId") long carInstanceId, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(carInstanceService.getUserReviewForCarInstance(carInstanceId,authorizationHeader));
    }

    @GetMapping("/review/vendor/{vendorId}")
    @ApiOperation("Get User Review For Vendor By User Token")
    public ResponseEntity<ResponseWrapper<ReviewResponse>> getUserReviewForVendor(@PathVariable("vendorId") long vendorId, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(vendorService.getUserReviewForVendor(vendorId,authorizationHeader));
    }


}
