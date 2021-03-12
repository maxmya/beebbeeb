package com.trixpert.beebbeeb.api.v1;


import com.trixpert.beebbeeb.data.request.AdminRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.services.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Admins API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/register")
    @ApiOperation("register a new admin")
    public ResponseEntity<ResponseWrapper<Boolean>> registerAdmin(@Valid @RequestBody AdminRegistrationRequest adminRegistrationRequest) {
        return ResponseEntity.ok(adminService.registerAdmin(adminRegistrationRequest));
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("/list")
    @ApiOperation("list all admins")
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> listAllAdmins() {
        return ResponseEntity.ok(adminService.listAdmins());
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PutMapping("/delete/{adminId}")
    @ApiOperation("delete admin")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteAdmin(@PathVariable("adminId") Long adminId) {
        return ResponseEntity.ok(adminService.deleteAdmin(adminId));
    }

}
