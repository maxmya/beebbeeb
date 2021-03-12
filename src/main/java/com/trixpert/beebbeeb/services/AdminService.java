package com.trixpert.beebbeeb.services;


import com.trixpert.beebbeeb.data.request.AdminRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.UserDTO;

import java.util.List;

public interface AdminService {

    ResponseWrapper<Boolean> registerAdmin(AdminRegistrationRequest registrationRequest);

    ResponseWrapper<List<UserDTO>> listAdmins();

    ResponseWrapper<Boolean> deleteAdmin(long adminId);

}
