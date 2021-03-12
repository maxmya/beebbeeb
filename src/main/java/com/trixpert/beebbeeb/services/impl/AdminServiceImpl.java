package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.UserRoles;
import com.trixpert.beebbeeb.data.mappers.UserMapper;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.repositories.UserRolesRepository;
import com.trixpert.beebbeeb.data.request.AdminRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AdminService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final UserRolesRepository userRolesRepository;

    private final ReporterService reporterService;

    private final UserMapper userMapper;

    public AdminServiceImpl(UserService userService,
                            UserRepository userRepository,
                            RolesRepository rolesRepository,
                            UserRolesRepository userRolesRepository,
                            ReporterService reporterService,
                            UserMapper userMapper) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.userRolesRepository = userRolesRepository;
        this.reporterService = reporterService;
        this.userMapper = userMapper;
    }


    @Override
    public ResponseWrapper<Boolean> registerAdmin(AdminRegistrationRequest registrationRequest) {
        try {

            Optional<RolesEntity> adminRole = rolesRepository.findByName(Roles.ROLE_ADMIN);

            if (!adminRole.isPresent()) {
                throw new NotFoundException("Role Super Admin Not Found");
            }

            userService.registerUser(
                    registrationRequest.getEmail(),
                    adminRole.get(),
                    registrationRequest,
                    false);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<UserDTO>> listAdmins() {
        try {
            Optional<RolesEntity> adminRole = rolesRepository.findByName(Roles.ROLE_ADMIN);

            if (!adminRole.isPresent()) {
                throw new NotFoundException("Admin Role Not Found !");
            }

            List<UserRoles> adminRoles = userRolesRepository.findAllByRoleId(adminRole.get().getId());
            List<Long> adminUserIds = new ArrayList<>();
            adminRoles.forEach(admin -> adminUserIds.add(admin.getUserId()));
            List<UserEntity> admins = userRepository.findAllByIdIn(adminUserIds);
            List<UserDTO> adminsDTOs = new ArrayList<>();
            admins.forEach(admin -> adminsDTOs.add(userMapper.convertToDTO(admin)));

            return reporterService.reportSuccess(adminsDTOs);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteAdmin(long adminId) {
        try {
            Optional<UserEntity> admin = userRepository.findById(adminId);
            if (!admin.isPresent()) {
                throw new NotFoundException("Admin not found !");
            }
            UserEntity adminEntity = admin.get();
            adminEntity.setActive(false);
            userRepository.save(adminEntity);
            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
