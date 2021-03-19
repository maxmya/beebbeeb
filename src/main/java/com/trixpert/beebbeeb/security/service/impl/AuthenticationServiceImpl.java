package com.trixpert.beebbeeb.security.service.impl;

import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.request.EmailLoginRequest;
import com.trixpert.beebbeeb.data.request.PhoneLoginRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.AuthResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.security.jwt.JwtProvider;
import com.trixpert.beebbeeb.security.service.AuthenticationService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;

    private final UserService userService;
    private final ReporterService reporterService;

    private final RolesRepository rolesRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthenticationServiceImpl(ReporterService reporterService,
                                     AuthenticationManager authenticationManager,
                                     JwtProvider jwtProvider,
                                     RolesRepository rolesRepository,
                                     UserRepository userRepository,
                                     UserService userService) {

        this.userRepository = userRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.rolesRepository = rolesRepository;
    }


    private AuthResponse loginUser(String username, String password, boolean isPhone) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        String jwt = jwtProvider.generateToken(authentication);

        if (jwt != null) {
            Optional<UserEntity> loggedInUser;

            if (isPhone)
                loggedInUser = userRepository.findByPhone(username);
            else
                loggedInUser = userRepository.findByEmail(username);

            if (loggedInUser.isPresent() && loggedInUser.get().isActive()) {

                AuthResponse response = new AuthResponse();
                response.setAccessToken(jwt);
                response.setDisplayName(loggedInUser.get().getName());
                return response;

            } else throw new UsernameNotFoundException("user not found");

        } else throw new UsernameNotFoundException("user not found");
    }


    @Override
    public ResponseWrapper<AuthResponse> loginUserWithPhone(PhoneLoginRequest userLoginRequest) {
        try {
            return reporterService.reportSuccess(
                    loginUser(userLoginRequest.getPhone(), userLoginRequest.getPassword(), true)
            );
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }


    @Override
    public ResponseWrapper<AuthResponse> loginUserWithEmail(EmailLoginRequest emailLoginRequest) {
        try {
            return reporterService.reportSuccess(
                    loginUser(emailLoginRequest.getEmail(), emailLoginRequest.getPassword(), false)
            );
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public boolean validateToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            return jwtProvider.validateJwtToken(token);
        } else {
            return false;
        }
    }


    @Override
    public ResponseWrapper<Boolean> registerAdmin(RegistrationRequest registrationRequest) {
        try {

            Optional<RolesEntity> adminRole = rolesRepository.findByName(Roles.ROLE_SUPER_ADMIN);

            if (!adminRole.isPresent()) {
                throw new NotFoundException("Role Super Admin Not Found");
            }

            userService.registerUser(
                    registrationRequest.getEmail(),
                    adminRole.get(),
                    registrationRequest,
                    "",
                    false);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }


}





