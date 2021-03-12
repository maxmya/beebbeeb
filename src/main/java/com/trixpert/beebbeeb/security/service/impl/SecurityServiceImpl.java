package com.trixpert.beebbeeb.security.service.impl;

import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.exception.UnauthorizedException;
import com.trixpert.beebbeeb.security.jwt.JwtProvider;
import com.trixpert.beebbeeb.security.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public SecurityServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserEntity getUserByHttpServletRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            throw new UnauthorizedException();
        }
        return this.getUserByAuthHeader(authorizationHeader);
    }

    @Override
    public UserEntity getUserByAuthHeader(String authHeader) {
        return this.getUserByToken(authHeader.replace("Bearer ", ""));
    }

    @Override
    public UserEntity getUserByToken(String token) {
        String userPhone = jwtProvider.getUsernameFromToken(token);
        Optional<UserEntity> userEntityOptional = userRepository.findByPhone(userPhone);
        if (userEntityOptional.isPresent())
            return userEntityOptional.get();
        else
            throw new UnauthorizedException();
    }
}
