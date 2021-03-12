package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.UserDTO;

public interface UserService {

    UserDTO getUserByUsername(String username);

    ResponseWrapper<UserEntity> updateUser(UserDTO userDTO);

    ResponseWrapper<UserEntity> registerUser(String username,
                                             RolesEntity role,
                                             RegistrationRequest registrationRequest,
                                             boolean isPhone);


}
