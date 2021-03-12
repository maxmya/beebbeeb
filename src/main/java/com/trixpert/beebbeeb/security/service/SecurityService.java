package com.trixpert.beebbeeb.security.service;


import com.trixpert.beebbeeb.data.entites.UserEntity;

import javax.servlet.http.HttpServletRequest;


public interface SecurityService {

    UserEntity getUserByHttpServletRequest(HttpServletRequest request);

    UserEntity getUserByAuthHeader(String authHeader);

    UserEntity getUserByToken(String token);

}
