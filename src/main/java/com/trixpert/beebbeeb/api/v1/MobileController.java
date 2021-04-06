package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.services.MobileService;
import com.trixpert.beebbeeb.services.PurchasingRequestService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"All Mobile APIs"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/mobile")
public class MobileController {

    private final MobileService mobileService;
    private final PurchasingRequestService purchasingRequestService;

    public MobileController(MobileService mobileService, PurchasingRequestService purchasingRequestService) {
        this.mobileService = mobileService;
        this.purchasingRequestService = purchasingRequestService;
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<CustomerProfileResponse>> profileResponse(HttpServletRequest request) {
        return ResponseEntity.ok(mobileService.profileResponse(request));
    }

    @GetMapping("/address/list")
    public ResponseEntity<ResponseWrapper<List<AddressDTO>>> getListOfAddresses(HttpServletRequest request) {
        return ResponseEntity.ok(mobileService.getListOfAddresses(request));
    }

    @GetMapping("/purchasingRequest/status/{purchasingRequestId}")
    public ResponseEntity<ResponseWrapper<PurchasingRequestMobileResponse>> getStatusForPurchasingRequest(
            @PathVariable("purchasingRequestId") long purchasingRequestId){
        return ResponseEntity.ok(purchasingRequestService.getPurchasingRequestStatus(purchasingRequestId));
    }

    @PostMapping("/address/add")
    public ResponseEntity<ResponseWrapper<Boolean>> saveAddress(@RequestBody AddressDTO address) {
        return ResponseEntity.ok(mobileService.saveAddress(address));
    }

    @GetMapping("/list/cars")
    public ResponseEntity<ResponseWrapper<List<CarItemResponse>>> listCars(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "query", required = false) String query) {
        return ResponseEntity.ok(mobileService.listCars(page, size, query));
    }


    @PostMapping("/register/customer")
    public ResponseEntity<ResponseWrapper<OtpResponse>> registerUser(@RequestBody CustomerMobileRegistrationRequest customerRegisterRequest) {
        return ResponseEntity.ok(mobileService.registerUser(customerRegisterRequest));
    }

    @PostMapping("/register/verify/{phone}")
    public ResponseEntity<ResponseWrapper<Boolean>> verifyUser(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(mobileService.verifyUser(phone));
    }


    @GetMapping("/home")
    public ResponseEntity<ResponseWrapper<MobileHomeResponse>> getMobileHome() {
        return ResponseEntity.ok(mobileService.getMobileHome());
    }

    @GetMapping("/car/details/{carId}")
    public ResponseEntity<ResponseWrapper<CarDetailsResponse>> getCarDetails(@PathVariable("carId") long carId) {
        return ResponseEntity.ok(mobileService.getCarDetails(carId));
    }


}
