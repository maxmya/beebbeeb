package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.EmailLoginRequest;
import com.trixpert.beebbeeb.data.request.PhoneLoginRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.AuthResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.security.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = {"Authenticate Users"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login/email", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Login User With Email & Password")
    public ResponseEntity<ResponseWrapper<AuthResponse>> authenticateUserByEmail(
            @Valid @RequestBody EmailLoginRequest emailLoginRequest) {
        return ResponseEntity.ok(authService.loginUserWithEmail(emailLoginRequest));
    }

    @PostMapping("/login/phone")
    @ApiOperation("Login User With Phone & Password")
    public ResponseEntity<ResponseWrapper<AuthResponse>> authenticateUserByPhone(@RequestBody PhoneLoginRequest phoneLoginRequest) {
        return ResponseEntity.ok(authService.loginUserWithPhone(phoneLoginRequest));
    }

    @GetMapping("/validate")
    @ApiOperation("Validate User Token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        boolean response = authService.validateToken(authorizationHeader);
        if (response) {
            return ResponseEntity.ok(true);
        } else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/phone/otp/{phoneNumber}")
    @ApiOperation("Verify user phone with OTP")
    public ResponseEntity<ResponseWrapper<String>> verifyPhoneByOTP(
            @PathVariable("phoneNumber") String phoneNumber) {
        return null;
    }

    @PostMapping("/register/super-admin")
    @ApiOperation("Register Admin With Email & Password")
    public ResponseEntity<ResponseWrapper<Boolean>> registerAdmin(
            @Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.registerAdmin(registrationRequest));
    }


}