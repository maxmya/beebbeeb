package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.AddressRegistrationRequest;
import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.LoanRegistrationRequest;
import com.trixpert.beebbeeb.data.request.PurchasingRequestRegistrationRequest;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.services.AddressService;
import com.trixpert.beebbeeb.services.LoanService;
import com.trixpert.beebbeeb.services.MobileService;
import com.trixpert.beebbeeb.services.PurchasingRequestService;
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

    public MobileController(MobileService mobileService,
                            PurchasingRequestService purchasingRequestService,
                            LoanService loanService, AddressService addressService) {

        this.mobileService = mobileService;
        this.purchasingRequestService = purchasingRequestService;
        this.loanService = loanService;
        this.addressService = addressService;
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
            @PathVariable("purchasingRequestId") long purchasingRequestId){
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
            @Valid @RequestBody AddressRegistrationRequest addressRegistrationRequest,
            @PathVariable("addressId") long addressId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(addressService.updateAddress(addressRegistrationRequest,
                addressId, authorizationHeader));
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


}
