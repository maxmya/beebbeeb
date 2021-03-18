package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.mappers.UserMapper;
import com.trixpert.beebbeeb.data.mappers.VendorMapper;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    private final Logger logger = LoggerFactory.getLogger("VendorService");

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final RolesRepository rolesRepository;

    private final ReporterService reporterService;
    private final UserService userService;

    private final UserMapper userMapper;
    private final VendorMapper vendorMapper;

    private final AuditService auditService;
    private final CloudStorageService cloudStorageService;


    public VendorServiceImpl(UserRepository userRepository,
                             VendorRepository vendorRepository,
                             RolesRepository rolesRepository,
                             ReporterService reporterService,
                             UserService userService,
                             UserMapper userMapper,
                             VendorMapper vendorMapper, AuditService auditService, CloudStorageService cloudStorageService) {
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.rolesRepository = rolesRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.vendorMapper = vendorMapper;
        this.auditService = auditService;
        this.cloudStorageService = cloudStorageService;
    }

    @Override
    public ResponseWrapper<Boolean> registerVendor(
            VendorRegistrationRequest vendorRegistrationRequest
            ,MultipartFile logoFile, String authHeader) throws IOException {

        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord ="";
        logoUrlRecord = cloudStorageService.uploadFile(logoFile);


        if (userRepository.existsByEmail(vendorRegistrationRequest.getEmail())) {
            logger.error("attempt to register existed email ".concat(vendorRegistrationRequest.getEmail()));
            return new ResponseWrapper<>(false,
                    "email already registered", HttpStatus.BAD_REQUEST, false);
        }

        try {

            Optional<RolesEntity> vendorRole = rolesRepository.findByName(Roles.ROLE_VENDOR);

            if (!vendorRole.isPresent()) {
                throw new NotFoundException("Vendor Roles Not Found");
            }

            UserEntity userEntityRecord = userService.registerUser(
                    vendorRegistrationRequest.getEmail(),
                    vendorRole.get(),
                    vendorRegistrationRequest,
                    logoUrlRecord,
                    false
            ).getData();


            VendorEntity vendorEntityRecord = VendorEntity.builder()
                    .name(vendorRegistrationRequest.getVendorName())
                    .manager(userEntityRecord)
                    .mainAddress(vendorRegistrationRequest.getMainAddress())
                    .generalManagerName(vendorRegistrationRequest.getGmName())
                    .generalManagerPhone(vendorRegistrationRequest.getGmPhone())
                    .accountManagerName(vendorRegistrationRequest.getAccManagerName())
                    .accountManagerPhone(vendorRegistrationRequest.getAccManagerPhone())
                    .build();

            vendorRepository.save(vendorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new vendor entity " + vendorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            logger.info("new vendor registered ".concat(vendorRegistrationRequest.getEmail()));
            return reporterService.reportSuccess("vendor registered successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<VendorDTO>> getAllVendors() {
        try {
            //Creating ArrayList for store all vendors on it
            List<VendorDTO> allVendors = new ArrayList<>();
            //Looping On All Vendors on database one by one
            vendorRepository.findAll().forEach(vendor -> {
                UserDTO userDTO = userMapper.convertToDTO(vendor.getManager());
                // For Every Vendor Create new Object from vendorDTO and Send needed data
                // From vendorEntity to vendorDTO by passing this data on constructor
                VendorDTO vendorDTO = vendorMapper.convertToDTO(vendor);
                vendorDTO.setManager(userDTO);
                //Append new vendorDTO Object in the list of all vendors
                allVendors.add(vendorDTO);
            });

            return reporterService.reportSuccess(allVendors);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteVendor(Long vendorId , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorId);
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            } else {
                VendorEntity vendorRecord = optionalVendorRecord.get();
                vendorRecord.setActive(false);
                vendorRepository.save(vendorRecord);

                AuditDTO auditDTO =
                        AuditDTO.builder()
                                .user(userService.getUserByUsername(username))
                                .action(AuditActions.DELETE)
                                .description("Deleting new vendor entity " + vendorRecord.toString())
                                .timestamp(LocalDateTime.now())
                                .build();

                auditService.logAudit(auditDTO);

                return reporterService.reportSuccess("Vendor soft deleted(deActivated) successfully");
            }
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateVendor(VendorDTO vendorDTO , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorDTO.getId());
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            }

            VendorEntity vendorRecord = optionalVendorRecord.get();

            if (vendorDTO.getVendorName() != null) {
                vendorRecord.setName(vendorDTO.getVendorName());
            }
            if (vendorDTO.getManager() != null) {
                userService.updateUser(userMapper.convertToDTO(vendorRecord.getManager()));
            }
            if (vendorDTO.getMainAddress() != null) {
                vendorRecord.setMainAddress(vendorDTO.getMainAddress());
            }
            if (vendorDTO.getGeneralManagerName() != null) {
                vendorRecord.setGeneralManagerName(vendorDTO.getGeneralManagerName());
            }
            if (vendorDTO.getGeneralManagerPhone() != null) {
                vendorRecord.setGeneralManagerPhone(vendorDTO.getGeneralManagerPhone());
            }
            if (vendorDTO.getGeneralManagerName() != null) {
                vendorRecord.setAccountManagerName(vendorDTO.getAccountManagerName());
            }
            if (vendorDTO.getAccountManagerPhone() != null) {
                vendorRecord.setAccountManagerPhone(vendorDTO.getAccountManagerPhone());
            }
            vendorRepository.save(vendorRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new vendor entity " + vendorRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Vendor updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
