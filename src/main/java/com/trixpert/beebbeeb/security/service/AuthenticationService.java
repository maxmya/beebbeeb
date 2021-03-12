package com.trixpert.beebbeeb.security.service;


import com.trixpert.beebbeeb.data.request.EmailLoginRequest;
import com.trixpert.beebbeeb.data.request.PhoneLoginRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.response.AuthResponse;

public interface AuthenticationService {


    ResponseWrapper<AuthResponse> loginUserWithPhone(PhoneLoginRequest userLoginRequest);

    ResponseWrapper<AuthResponse> loginUserWithEmail(EmailLoginRequest emailLoginRequest);

    ResponseWrapper<Boolean> registerAdmin(RegistrationRequest registrationRequest);

    boolean validateToken(String authHeader);


}
