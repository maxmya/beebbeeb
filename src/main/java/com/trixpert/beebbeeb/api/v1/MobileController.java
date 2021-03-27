package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.response.*;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.services.*;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(tags = {"All Mobile APIs"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/mobile")
public class MobileController {

    private final MobileService mobileService;

    public MobileController(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<CustomerProfileResponse>> profileResponse(HttpServletRequest request) {
        return ResponseEntity.ok(mobileService.profileResponse(request));
    }

    @GetMapping("/address/list")
    public ResponseEntity<ResponseWrapper<List<AddressDTO>>> getListOfAddresses(HttpServletRequest request) {
        return ResponseEntity.ok(mobileService.getListOfAddresses(request));
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


}
